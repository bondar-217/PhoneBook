package PhoneBookApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class PhoneBookApp {

    public static class NamesError extends Exception {
        public NamesError(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws IOException, NamesError {
        PhoneBookView view = new ConsolePhoneBookView();
        PhoneBookOperations model = new PhoneBookModel();
        PhoneBookPresenter presenter = new PhoneBookPresenter(view, model);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = view.getInput("Input command: ");
            presenter.handleCommand(command);
        }
    }
}
