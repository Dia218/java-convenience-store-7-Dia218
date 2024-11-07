package store.view;

public class InputView {
    public String requireInput(InputMessages massage) {
        printRequireMessage(massage);
        return camp.nextstep.edu.missionutils.Console.readLine();
    }
    private void printRequireMessage(InputMessages massage) {
        System.out.println(massage);
    }
}
