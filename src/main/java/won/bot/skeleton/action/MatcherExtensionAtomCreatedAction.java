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

        //Dataset
        //me
        System.out.println("start");
        DefaultAtomModelWrapper defaultAtomModelWrapper = new DefaultAtomModelWrapper(atomCreatedEvent.getAtomData());

        //System.out.println("getAllTags");

        //System.out.println("data");
        //System.out.println(defaultAtomModelWrapper.getAllTags());

        //System.out.println("seek");

        Collection<Resource> seeksnodes=defaultAtomModelWrapper.getSeeksNodes();
        for(Resource res :seeksnodes){
            //Collection<String> tagsList = defaultAtomModelWrapper.getContentPropertyStringValues(res, WONCON.tag, null);
            Collection<String> tagsList = defaultAtomModelWrapper.getTags(res);
            System.out.println("tags: ");

            for (String elem : tagsList) {
                if(false){
                    //isbn checken und evlt dafür aufrufen
                    //break;
                }
                if(elem.toLowerCase().contains("book")){
                    System.out.println("match: " + elem);
                    // hier crawler aufrufen mit titel
                    //break;
                }
                if(elem.toLowerCase().contains("buch")){
                    System.out.println("match: " + elem);
                    // hier crawler aufrufen
                    //break;
                }
                if(elem.toLowerCase().equals("bücher")){
                    System.out.println("match: " + elem);
                    // hier crawler aufrufen
                    //break;
                }

            }
        }
        System.out.println("done");

        //nur die looking for, wie?


        /*System.out.println("forEach");
        defaultAtomModelWrapper.getSeeksNodes().forEach(node -> {
            System.out.println(defaultAtomModelWrapper.getContentPropertyStringValue(node, DC.description));
        });*/

        //me

        Map<URI, Set<URI>> connectedSocketsMapSet = botContextWrapper.getConnectedSockets();

        //hier iwo also mittels tag schauen (book, buch) ob es passt, wenn ja ->
        // crawlen & atom erstellen

        for(Map.Entry<URI, Set<URI>> entry : connectedSocketsMapSet.entrySet()) {
            URI senderSocket = entry.getKey();
            Set<URI> targetSocketsSet = entry.getValue();
            for(URI targetSocket : targetSocketsSet) {
                logger.info("TODO: Send MSG("+senderSocket+"->"+targetSocket+") that we registered that an Atom was created, atomUri is: " +atomCreatedEvent.getAtomURI());
                WonMessage wonMessage = WonMessageBuilder
                                            .connectionMessage()
                                            .sockets()
                                            .sender(senderSocket)
                                            .recipient(targetSocket)
                                            .content()
                                            .text("We registered that an Atom was created, atomUri is: " + atomCreatedEvent.getAtomURI())
                                            .build();
                ctx.getWonMessageSender().prepareAndSendMessage(wonMessage);
            }
        }
    }
}
