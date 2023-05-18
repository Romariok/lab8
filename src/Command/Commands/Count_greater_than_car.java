package Command.Commands;

import Command.CommandResponse;
import Command.Command_abstract;
import Data.HumanBeing;
import DataStructure.Response;
import server.Log;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for the count_greater_than_car command. Remove elements in collection whose status of car greater than specified
 */
public class Count_greater_than_car extends Command_abstract implements CommandResponse {
    private String output;

    public Count_greater_than_car(){
    }
    @Override
    public void execute(){
        CopyOnWriteArrayList<HumanBeing> humans = getCollectionManager().getConcurrentCollection();
        int counting = 0;
        try {
            boolean cool=  Boolean.parseBoolean(getArgs()[0]);
            if (!cool) {
                for (int i = 0; i < humans.size(); i++) {
                    if (humans.get(i).getCar().getCool()) {
                        counting++;
                    }
                }
            }
            output = "Количество элементов, значение поля car которых больше " + cool + " : " + counting+"\n";
        }catch (Exception ex){
            output = "Неправильный аргумент!";
            Log.getLogger().warning(ex.getMessage());
        }

    }

    @Override
    public Response getResponse(){
        return new Response("count greater than car", output);
    }
}
