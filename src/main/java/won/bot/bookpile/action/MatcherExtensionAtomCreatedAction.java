package won.bot.bookpile.action;

import org.apache.jena.rdf.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import won.bot.framework.eventbot.EventListenerContext;
import won.bot.framework.eventbot.action.BaseEventBotAction;
import won.bot.framework.eventbot.event.Event;
import won.bot.framework.eventbot.listener.EventListener;
import won.bot.framework.extensions.matcher.MatcherExtensionAtomCreatedEvent;
import won.bot.bookpile.context.BookPileBotContextWrapper;
import won.bot.bookpile.impl.BookPileGenerator;
import won.bot.bookpile.utils.BookAtomModelWrapper;

import java.lang.invoke.MethodHandles;
import java.util.Collection;

public class MatcherExtensionAtomCreatedAction extends BaseEventBotAction {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public MatcherExtensionAtomCreatedAction(EventListenerContext eventListenerContext) {
        super(eventListenerContext);
    }

    @Override protected void doRun(Event event, EventListener executingListener) throws Exception {
        EventListenerContext ctx = getEventListenerContext();
        if(!(event instanceof MatcherExtensionAtomCreatedEvent) || !(getEventListenerContext().getBotContextWrapper() instanceof BookPileBotContextWrapper)) {
            logger.error("MatcherExtensionAtomCreatedAction can only handle MatcherExtensionAtomCreatedEvent and only works with BookPileBotContextWrapper");
            return;
        }

        MatcherExtensionAtomCreatedEvent atomCreatedEvent = (MatcherExtensionAtomCreatedEvent) event;

        BookAtomModelWrapper bookAtomModelWrapper = new BookAtomModelWrapper(atomCreatedEvent.getAtomData());
        if (isBookAtom(bookAtomModelWrapper)) {
            String isbn = bookAtomModelWrapper.getSeeksIsbn();
            BookPileGenerator bookPileGenerator = new BookPileGenerator(ctx);

            if(isbn!=null){
                bookPileGenerator.generatePile(isbn);
            } else {
                String title = bookAtomModelWrapper.getSomeTitleFromIsOrAll();
                bookPileGenerator.generatePile(title);
            }
        }

    }

    private boolean isBookAtom(BookAtomModelWrapper bookAtomModelWrapper) {
        Collection<Resource> seeksnodes=bookAtomModelWrapper.getSeeksNodes();
        for(Resource res :seeksnodes){
            //Collection<String> tagsList = defaultAtomModelWrapper.getContentPropertyStringValues(res, WONCON.tag, null);
            Collection<String> tagsList = bookAtomModelWrapper.getTags(res);
            String isbn = bookAtomModelWrapper.getSeeksIsbn();

            if(isbn!=null){
                return true;
            }
            if(bookAtomModelWrapper.isBookSearch()){
                return true;
            }
            for (String elem : tagsList) {
                if (hasBookTag(elem)) {
                    return true;
                }
            }


        }

        return false;
    }

    private boolean hasBookTag(String elem) {
        if(elem.toLowerCase().contains("book")){
            return true;
        }
        if(elem.toLowerCase().contains("buch")){
            return true;
        }
        if(elem.toLowerCase().equals("bücher")){
            return true;
        }

        return false;
    }
}
