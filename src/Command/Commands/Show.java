package Command.Commands;

import Command.CommandResponse;
import Command.Command_abstract;
import Data.HumanBeing;
import DataStructure.Response;
import server.FileManagment.ParserfromBD;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for the show command. Printing information about elements in collection
 */
public class Show extends Command_abstract implements CommandResponse {
    private String output = "";

    public Show(){
    }
    @Override
    public void execute() {
        output = "";
        ParserfromBD parserfromBD = new ParserfromBD(this.getCollectionManager());
        parserfromBD.parseData();
        CopyOnWriteArrayList<HumanBeing> humans = getCollectionManager().getConcurrentCollection();
        if (humans.size() != 0) humans.forEach(humanBeing -> {
            output += humanBeing.toString()+"\n";
        });
        else output = "Collection is empty\n";
    }

    @Override
    public Response getResponse(){
        return new Response("show", output);
    }
}
