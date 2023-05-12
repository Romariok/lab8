package Command.Commands;

import Command.CommandResponse;
import Command.Command_abstract;
import Data.HumanBeing;
import DataStructure.Response;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for the filter_starts_with_soundtrack_name. Print items whose soundtrackName field value starts with the specified substring
 */
public class Filter_starts_with_soundtrack_name extends Command_abstract implements CommandResponse {
    private String output;

    public Filter_starts_with_soundtrack_name(){
    }
    @Override
    public void execute() {
        CopyOnWriteArrayList<HumanBeing> humans = getCollectionManager().getConcurrentCollection();
        String soundtrackName = getArgs()[0];
        humans.forEach((humanBeing -> {
            if (humanBeing.getSoundtrackName().contains(soundtrackName)) {
                output += humanBeing.getSoundtrackName()+"\n";
            }
        }));

    }

    @Override
    public Response getResponse() {
        return new Response("filter_starts_with_soundtrack_name", output);
    }
}
