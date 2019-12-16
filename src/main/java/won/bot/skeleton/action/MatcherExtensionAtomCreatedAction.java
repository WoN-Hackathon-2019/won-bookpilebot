package won.bot.skeleton.action;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import won.bot.framework.eventbot.EventListenerContext;
import won.bot.framework.eventbot.action.BaseEventBotAction;
import won.bot.framework.eventbot.event.Event;
import won.bot.framework.eventbot.listener.EventListener;
import won.bot.framework.extensions.matcher.MatcherExtensionAtomCreatedEvent;
import won.bot.skeleton.context.SkeletonBotContextWrapper;
import won.bot.skeleton.utils.BookAtomModelWrapper;
import won.protocol.message.WonMessage;
import won.protocol.message.builder.WonMessageBuilder;
import won.protocol.model.Coordinate;
import won.protocol.util.DefaultAtomModelWrapper;
import won.protocol.vocabulary.SCHEMA;
import won.protocol.vocabulary.WONCON;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MatcherExtensionAtomCreatedAction extends BaseEventBotAction {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public MatcherExtensionAtomCreatedAction(EventListenerContext eventListenerContext) {
        super(eventListenerContext);
    }

    @Override protected void doRun(Event event, EventListener executingListener) throws Exception {
        EventListenerContext ctx = getEventListenerContext();
        if(!(event instanceof MatcherExtensionAtomCreatedEvent) || !(getEventListenerContext().getBotContextWrapper() instanceof SkeletonBotContextWrapper)) {
            logger.error("MatcherExtensionAtomCreatedAction can only handle MatcherExtensionAtomCreatedEvent and only works with SkeletonBotContextWrapper");
            return;
        }
        SkeletonBotContextWrapper botContextWrapper = (SkeletonBotContextWrapper) ctx.getBotContextWrapper();
        MatcherExtensionAtomCreatedEvent atomCreatedEvent = (MatcherExtensionAtomCreatedEvent) event;

        System.out.println("start");
        BookAtomModelWrapper bookAtomModelWrapper = new BookAtomModelWrapper(atomCreatedEvent.getAtomData());

        Collection<Resource> seeksnodes=bookAtomModelWrapper.getSeeksNodes();
        for(Resource res :seeksnodes){
            //Collection<String> tagsList = defaultAtomModelWrapper.getContentPropertyStringValues(res, WONCON.tag, null);
            Collection<String> tagsList = bookAtomModelWrapper.getTags(res);
            System.out.println("in");
            String isbn = bookAtomModelWrapper.getSeeksIsbn();

            if(isbn!=null){
                // crawler mit isbn
                System.out.println("isbn: " + isbn);
                System.out.println("crawler mit isbn");
                break;
            }

            for (String elem : tagsList) {
                if(isbn!=null){
                    // crawler mit isbn
                    System.out.println("isbn: " + isbn);
                    System.out.println("crawler mit isbn");
                    break;
                }
                if(elem.toLowerCase().contains("book")){
                    System.out.println("match: " + elem);
                    System.out.println("crawler mit titel");
                    // hier crawler aufrufen mit titel
                    break;
                }
                if(elem.toLowerCase().contains("buch")){
                    System.out.println("match: " + elem);
                    System.out.println("crawler mit titel");
                    // hier crawler aufrufen
                    break;
                }
                if(elem.toLowerCase().equals("bücher")){
                    System.out.println("match: " + elem);
                    System.out.println("crawler mit titel");
                    // hier crawler aufrufen
                    break;
                }

            }
        }
        System.out.println("done");
    }
}
