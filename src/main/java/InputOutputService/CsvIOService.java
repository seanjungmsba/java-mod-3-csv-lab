package InputOutputService;

import Builder.Logger;
import Builder.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvIOService implements IOServiceInterface {

    private String csvFileName;
    public CsvIOService(String csvFileName) {
        this.csvFileName = csvFileName;
    }
    public List<Person> readFromFile() {
        List<Person> personList = new ArrayList<>();
        String peopleFromCSV = new String();
        Scanner fileReader = null;
        try {
            File myFile = new File(csvFileName);
            fileReader = new Scanner(myFile);
            while (fileReader.hasNextLine()) {
                peopleFromCSV = fileReader.nextLine();
                String[] storePersonArr = peopleFromCSV.split(",");
                String firstName = storePersonArr[0];
                String lastName = storePersonArr[1];
                int birthYear = Integer.parseInt(storePersonArr[2]);
                int birthMonth = Integer.parseInt(storePersonArr[3]);
                int birthDay = Integer.parseInt(storePersonArr[4]);
                Person person = new Person(firstName, lastName, birthYear, birthMonth, birthDay);
                personList.add(person);
            }
        } catch (IOException ioException) {
            Logger.getInstance().log("ERROR: There is either no file in the directory or file has no contents. Please re-start the program if you wish to continue");
            System.exit(0);
        } finally {
            if (fileReader != null)
                fileReader.close();
        }

        return personList;
    }


    public void writeToFile(List<Person> personList){
        try (FileWriter fileWriter = new FileWriter(csvFileName);) {
            for (Person person : personList) {
                fileWriter.write(person.toString() + "\n");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void resetFile() {
        try {
            new FileWriter(csvFileName, false).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCSVFileName() {
        return csvFileName;
    }

    public boolean doesCSVFileExist() {
        return this.csvFileName != null;
    }

}