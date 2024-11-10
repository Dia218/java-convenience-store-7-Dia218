package store.view;

public class InputView {
    public String requireInput(ServiceMessages message) {
        return requireInput(message.getInputMessage());
    }

    public String requireInput(ServiceMessages message, String addString, int addInt) {
        return requireInput(String.format(message.getInputMessage(), addString, addInt));
    }

    private String requireInput(String message) {
        printRequireMessage(message);
        return camp.nextstep.edu.missionutils.Console.readLine();
    }

    private void printRequireMessage(String message) {
        System.out.println(message);
    }
}
