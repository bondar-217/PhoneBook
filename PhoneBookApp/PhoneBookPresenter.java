package PhoneBookApp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class PhoneBookPresenter {
    private PhoneBookView view;
    private PhoneBookOperations model;
    private String fileName = "phone.csv";
    private String newFile = "phone_1.csv";

    public PhoneBookPresenter(PhoneBookView view, PhoneBookOperations model) {
        this.view = view;
        this.model = model;
    }

    public void handleCommand(String command) throws IOException, PhoneBookApp.NamesError {
        switch (command) {
            case "q":
                view.showMessage("Exiting application...");
                System.exit(0);
                break;
            case "w":
                if (!Files.exists(Paths.get(fileName))) {
                    model.createFile(fileName);
                }
                Map<String, String> entry = view.getUserInfo();
                model.writeFile(fileName, entry);
                break;
            case "r":
                if (!Files.exists(Paths.get(fileName))) {
                    view.showMessage("File doesn't exist, create file first!");
                    break;
                }
                view.showMessage(model.readFile(fileName).toString());
                break;
            case "d":
                if (!Files.exists(Paths.get(fileName))) {
                    view.showMessage("File doesn't exist, create file first!");
                    break;
                }
                int deleteRowNumber = Integer.parseInt(view.getInput("Input number of row to delete: "));
                model.deleteRow(fileName, deleteRowNumber);
                break;
            case "e":
                if (!Files.exists(Paths.get(fileName))) {
                    view.showMessage("File doesn't exist, create file first!");
                    break;
                }
                int editRowNumber = Integer.parseInt(view.getInput("Input number of row to edit: "));
                Map<String, String> newEntry = view.getUserInfo();
                model.editRow(fileName, editRowNumber, newEntry);
                break;
            case "c":
                if (!Files.exists(Paths.get(newFile))) {
                    model.createFile(newFile);
                    view.showMessage("File created!");
                }
                int copyRowNumber = Integer.parseInt(view.getInput("Input number of row to copy: "));
                model.copyRow(fileName, newFile, copyRowNumber);
                break;
            case "search":
                String searchDate = view.getInput("Enter date to search (YYYY-MM-DD): ");
                view.showMessage(model.searchByDate(fileName, searchDate).toString());
                break;
            case "sort":
                view.showMessage(model.sortByDateAndTime(fileName).toString());
                break;
            default:
                view.showMessage("Wrong command!");
                break;
        }
    }
}
