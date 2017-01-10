package ro.gov.ithub.personala;

/**
 * Created by andrei.
 */

public enum Command {

    NULL("null") {
        @Override public void execute(ICommand pCommand) {
            pCommand.onNullCommand();
        }
    },
    CALL("call") {
        @Override public void execute(ICommand pCommand) {
            pCommand.onCallCommand();
        }
    };

    private String command;

    Command(String command){
        this.command = command;
    }

    public static Command fromString(String pCommand){
        Command command = Command.valueOf(pCommand.toLowerCase());
        return command == null ? NULL : command;
    }

    public abstract void execute(ICommand pCommand);

    public interface ICommand{
        void onNullCommand();
        void onCallCommand();
    }
}
