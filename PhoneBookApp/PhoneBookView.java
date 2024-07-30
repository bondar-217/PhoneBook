package PhoneBookApp;

import java.util.*;

interface PhoneBookView {
    void showMessage(String message);

    String getInput(String prompt);

    Map<String, String> getUserInfo() throws PhoneBookApp.NamesError;
}

class ConsolePhoneBookView implements PhoneBookView {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public Map<String, String> getUserInfo() throws PhoneBookApp.NamesError {
        while (true) {
            try {
                String date = getInput("Date (YYYY-MM-DD): ");
                String time = getInput("Time (HH:MM): ");
                String firstName = getInput("First Name: ");
                if (firstName.length() < 2) {
                    throw new PhoneBookApp.NamesError("Too short name!");
                }

                String secondName = getInput("Second Name: ");
                if (secondName.length() < 4) {
                    throw new PhoneBookApp.NamesError("Too short second name!");
                }

                String phoneNumbers = getInput("Phone number: ");
                if (phoneNumbers.length() < 11) {
                    throw new PhoneBookApp.NamesError("Too short phone number!");
                }

                Map<String, String> userInfo = new HashMap<>();
                userInfo.put("date", date);
                userInfo.put("time", time);
                userInfo.put("first_name", firstName);
                userInfo.put("second_name", secondName);
                userInfo.put("phone_numbers", phoneNumbers);
                return userInfo;
            } catch (PhoneBookApp.NamesError e) {
                showMessage(e.getMessage());
            }
        }
    }

}
