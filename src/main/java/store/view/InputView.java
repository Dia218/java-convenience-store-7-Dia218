package store.view;

public class InputView {
    public String requireInput(ServiceMessages message) {
        printRequireMessage(message);
        return camp.nextstep.edu.missionutils.Console.readLine();
    }
    private void printRequireMessage(ServiceMessages message) {
        System.out.println(message.getInputMessage());
    }
}
