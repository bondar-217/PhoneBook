package PhoneBookApp;

import java.io.*;
import java.util.*;

interface PhoneBookOperations {
    void createFile(String fileName) throws IOException;

    void writeFile(String fileName, Map<String, String> entry) throws IOException;

    List<Map<String, String>> readFile(String fileName) throws IOException;

    void deleteRow(String fileName, int rowNumber) throws IOException;

    void editRow(String fileName, int rowNumber, Map<String, String> entry) throws IOException;

    void copyRow(String fileName, String newFile, int rowNumber) throws IOException;

    List<Map<String, String>> searchByDate(String fileName, String date) throws IOException;

    List<Map<String, String>> sortByDateAndTime(String fileName) throws IOException;
}

class PhoneBookModel implements PhoneBookOperations {

    @Override
    public void createFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("date,time,first_name,second_name,phone_numbers\n");
        }
    }

    @Override
    public void writeFile(String fileName, Map<String, String> entry) throws IOException {
        List<Map<String, String>> res = readFile(fileName);
        res.add(entry);
        standardWrite(fileName, res);
    }

    @Override
    public List<Map<String, String>> readFile(String fileName) throws IOException {
        List<Map<String, String>> res = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] headers = reader.readLine().split(",");
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
                }
                res.add(row);
            }
        }
        return res;
    }

    @Override
    public void deleteRow(String fileName, int rowNumber) throws IOException {
        List<Map<String, String>> res = readFile(fileName);
        // Проверяем, что индекс строки находится в допустимом диапазоне
        if (rowNumber > 0 && rowNumber <= res.size()) {
            res.remove(rowNumber - 1);
            standardWrite(fileName, res);
        } else {
            throw new IllegalArgumentException(
                    "Row " + rowNumber + " doesn't exist! Please enter a number between 1 and " + res.size() + ".");
        }
    }

    @Override
    public void editRow(String fileName, int rowNumber, Map<String, String> entry) throws IOException {
        List<Map<String, String>> res = readFile(fileName);
        // Проверяем, что индекс строки находится в допустимом диапазоне
        if (rowNumber > 0 && rowNumber <= res.size()) {
            res.set(rowNumber - 1, entry);
            standardWrite(fileName, res);
        } else {
            throw new IllegalArgumentException(
                    "Row " + rowNumber + " doesn't exist! Please enter a number between 1 and " + res.size() + ".");
        }
    }

    @Override
    public void copyRow(String fileName, String newFile, int rowNumber) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String> rows = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }

            if (rowNumber < 1 || rowNumber >= rows.size()) {
                throw new IllegalArgumentException("Row " + rowNumber + " doesn't exist!");
            } else {
                String rowToCopy = rows.get(rowNumber);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true))) {
                    writer.write(rowToCopy);
                    writer.newLine();
                }
            }
        }
    }

    @Override
    public List<Map<String, String>> searchByDate(String fileName, String date) throws IOException {
        List<Map<String, String>> res = readFile(fileName);
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> row : res) {
            if (row.get("date").equals(date)) {
                result.add(row);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, String>> sortByDateAndTime(String fileName) throws IOException {
        List<Map<String, String>> res = readFile(fileName);
        res.sort(Comparator.comparing((Map<String, String> row) -> row.get("date"))
                .thenComparing(row -> row.get("time")));
        return res;
    }

    private void standardWrite(String fileName, List<Map<String, String>> res) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("date,time,first_name,second_name,phone_numbers\n");
            for (Map<String, String> row : res) {
                writer.write(
                        String.join(",", row.get("date"), row.get("time"), row.get("first_name"),
                                row.get("second_name"), row.get("phone_numbers")));
                writer.newLine();
            }
        }
    }
}
