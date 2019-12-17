package won.bot.skeleton.impl;

import won.bot.framework.eventbot.EventListenerContext;
import won.bot.framework.eventbot.action.BaseEventBotAction;
import won.bot.framework.eventbot.event.Event;
import won.bot.framework.eventbot.event.impl.command.create.CreateAtomCommandEvent;
import won.bot.framework.eventbot.event.impl.command.create.CreateAtomCommandSuccessEvent;
import won.bot.framework.eventbot.filter.impl.CommandResultFilter;
import won.bot.framework.eventbot.listener.EventListener;
import won.bot.framework.eventbot.listener.impl.ActionOnFirstEventListener;
import won.bot.skeleton.utils.BookAtomModelWrapper;
import won.bot.skeleton.utils.PileBook;
import won.bot.skeleton.utils.RestClient;
import won.protocol.service.WonNodeInformationService;
import won.protocol.util.AtomModelWrapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Samantha on 10.12.2019.
 */
public class BookPileGenerator {
    private static final String GET_ENDPOINT = "https://buechereien.wien.gv.at/Mediensuche/Einfache-Suche?search=%1";
    private static final String uriListName = "atom-pile";
    private static final RestClient restClient = new RestClient();

    private EventListenerContext ctx;

    public BookPileGenerator(EventListenerContext ctx) {
        this.ctx = ctx;
    }

    private static final String regex = "<div>\\s*?<a.*?href=\"(.*?)\".*?>(.*?(<mark>.*?</mark>)?.*?)</a>.*?</div>\\s*?" +
            "<div.*?>(.*?)</div>\\s*?<div.*?>.*?</div>\\s*?" +
            "<div>.*?<span.*?>\\s*?VerfasserIn:.*?</span>(.*?)</div>.*?" +
            "<div>\\s*?<span.*?>\\s*?Jahr:.*?</span>(.*?)</div>.*?" +
            "<div>\\s*?<span.*?>\\s*?Verlag:.*?</span>\\s*?<span.*?>(.*?)</span>\\s*?</div>.*?" +
            "<div>\\s*?(<span.*?>\\s*?Reihe:.*?</span>(.*?))?</div>\\s*?" +
            "<div.*?>\\s*?<span.*?>\\s*?<span.*?>\\s*?Mediengruppe:.*?</span>\\s*?<span.*?>(.*?)</span>\\s*?</span>\\s*?</div>";

    private static final String regex_isbn = "<div>\\s*?<span.*?>\\s*?ISBN:\\s*?</span>\\s*?<span.*?>(.*?)</span>\\s*?</div>";

    public ArrayList<AtomModelWrapper> generatePile(String keyword) {
        WonNodeInformationService wonNodeInformationService = ctx.getWonNodeInformationService();
        URI wonNodeUri = ctx.getNodeURISource().getNodeURI();

        ArrayList<AtomModelWrapper> bookAtoms = new ArrayList<>();
        // add information to the atom model
        for(PileBook book : getBooks(keyword)) {
            URI atomURI = wonNodeInformationService.generateAtomURI(wonNodeUri);
            BookAtomModelWrapper atomModelWrapper = new BookAtomModelWrapper(atomURI);
            atomModelWrapper.setTitle(book.getTitle());
            if (book.getIsbn() != null) {
                atomModelWrapper.setIsbn(book.getIsbn());
            }
            // TODO check empty fields
            // TODO check set URL
            atomModelWrapper.setUrl(book.getUrl());
            atomModelWrapper.setDescription(book.getDescription());
            atomModelWrapper.setAuthorName(book.getAuthor());

            // Resource seeker = atomModelWrapper.createSeeksNode(atomURI.toString());

            // add sockets for connections between atoms
            // atomModelWrapper.addSocket(atomURI.toString() + "#socket0", SocketType.ChatSocket.getURI().toString());
            // atomModelWrapper.addSocket(atomURI.toString() + "#socket1", SocketType.HoldableSocket.getURI().toString());

            //publish command
            CreateAtomCommandEvent createCommand = new CreateAtomCommandEvent(atomModelWrapper.getDataset(), uriListName);

            // this registers a listener that is activated when the message has been successful
            ctx.getEventBus().subscribe(
                    CreateAtomCommandSuccessEvent.class,
                    new ActionOnFirstEventListener( //note the 'onFIRSTevent' in the name: the listener is destroyed after being invoked once.
                            ctx,
                            new CommandResultFilter(createCommand),  // only listen for success to the command we just made
                            new BaseEventBotAction(ctx) {
                                @Override
                                protected void doRun(Event event, EventListener executingListener) {
                                    //your action here
                                    System.out.println("Book created");
                                }
                            }));

            ctx.getEventBus().publish(createCommand);

            // prepare the creation message that will be sent to the node
            /*
            Dataset atomDataset = atomModelWrapper.copyDataset();
            WonMessage createAtomMessage = createWonMessage(wonNodeInformationService, atomURI,
                    wonNodeUri, atomDataset);
             */
            // remember the atom URI so we can react to success/failure responses
            //EventBotActionUtils.rememberInList(ctx, atomURI, uriListName);

            bookAtoms.add(atomModelWrapper);

        }
        return bookAtoms;
    }

    public static String getUrl(String endpoint, String search) {
        return endpoint.replace("%1", search);
    }

    public static ArrayList<PileBook> getBooks(String keyword) {
        String url = getUrl(GET_ENDPOINT, keyword);
        String html = restClient.get(url);

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        ArrayList<PileBook> books = new ArrayList<>();
        while (matcher.find()) {
            PileBook book = new PileBook(keyword);
            book.setUrl(matcher.group(1));
            book.setTitle(matcher.group(2));
            book.setSubtitle(matcher.group(4));
            book.setAuthor(matcher.group(5));
            book.setYear(matcher.group(6));
            book.setPublisher(matcher.group(7));
            book.setSeries(matcher.group(9));
            book.setMediaType(matcher.group(10));

            if (!book.getMediaType().contains("Buch")) {
                continue;
            }

            String bookHtml = restClient.get(book.getUrl());
            Pattern pattern2 = Pattern.compile(regex_isbn, Pattern.DOTALL);
            Matcher matcher2 = pattern2.matcher(bookHtml);

            if (matcher2.find()) {
                book.setIsbn(matcher2.group(1));
            }

            books.add(book);
        }

        return books;
    }

    public static void main(String[] args) {
        ArrayList<PileBook> books = getBooks("rubinrot");
        for (PileBook book : books) {
            System.out.println(book);
        }
    }
}
