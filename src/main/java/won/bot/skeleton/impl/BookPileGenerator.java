package won.bot.skeleton.impl;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Resource;
import won.bot.framework.eventbot.EventListenerContext;
import won.bot.framework.eventbot.action.EventBotActionUtils;
import won.protocol.message.WonMessage;
import won.protocol.model.SocketType;
import won.protocol.service.WonNodeInformationService;
import won.protocol.util.AtomModelWrapper;
import won.protocol.util.DefaultAtomModelWrapper;
import won.protocol.vocabulary.SCHEMA;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Samantha on 10.12.2019.
 */
public class BookPileGenerator {
    private static final String GET_ENDPOINT = "https://buechereien.wien.gv.at/Mediensuche/Einfache-Suche?search=%1";
    private static final String uriListName = "atom-pile";

    public void generatePile(EventListenerContext ctx, String keyword) {
        WonNodeInformationService wonNodeInformationService =
                ctx.getWonNodeInformationService();
        URI wonNodeUri = ctx.getNodeURISource().getNodeURI();
        URI atomURI = wonNodeInformationService.generateAtomURI(wonNodeUri);

        ArrayList<AtomModelWrapper> bookAtoms = new ArrayList<>();
        // add information to the atom model
        for(PileBook book : getBooks(keyword)) {
            DefaultAtomModelWrapper atomModelWrapper;
            atomModelWrapper = new DefaultAtomModelWrapper(atomURI);
            atomModelWrapper.setTitle(book.getTitle());
            atomModelWrapper.setDescription("your description");

            Resource seeker = atomModelWrapper.createSeeksNode(atomURI.toString());

            // add sockets for connections between atoms
            atomModelWrapper.addSocket(atomURI.toString() + "#socket0", SocketType.ChatSocket.getURI().toString());
            atomModelWrapper.addSocket(atomURI.toString() + "#socket1", SocketType.HoldableSocket.getURI().toString());

            // prepare the creation message that will be sent to the node
            Dataset atomDataset = atomModelWrapper.copyDataset();
            WonMessage createAtomMessage = createWonMessage(wonNodeInformationService, atomURI,
                    wonNodeUri, atomDataset);
            // remember the atom URI so we can react to success/failure responses
            EventBotActionUtils.rememberInList(ctx, atomURI, uriListName);


            bookAtoms.add(atomModelWrapper);

        }
    }

    public ArrayList<PileBook> getBooks(String keyword) {
        return new ArrayList<>();
    }
}
