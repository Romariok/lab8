package Command.Commands;

import Command.*;
import Data.HumanBeing;
import DataStructure.Response;


/**
 * Class for the update command. Updating element by his index
 */
public class Update extends Command_abstract implements CommandResponse {
    private String output;

    public Update() {
    }

    @Override
    public void execute() {
        long id = Long.parseLong(getArgs()[0]);
        setBd(true);
        HumanBeing humanBeingNew = (HumanBeing) getValue();
        humanBeingNew.setLogin(getUser());
        setSuccess(getCollectionManager().getDBManager().updateCommand(humanBeingNew, id, getUser()));
        if (isSuccess()) {
            output = "Ваш элемент успешно обновлён!\n";
        } else {
            output = getCollectionManager().getDBManager().getLastE();
        }
    }

    @Override
    public Response getResponse() {
        return new Response("update", output);
    }
}
