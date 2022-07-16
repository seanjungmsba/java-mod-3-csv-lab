import Builder.Logger;
import Builder.Person;
import Builder.PersonBuilderService;
import Builder.ScannerUserInputService;
import InputOutputService.FileType;
import InputOutputService.IOServiceInterface;
import InputOutputService.IOServiceSetter;

import java.util.ArrayList;
import java.util.List;
public class PersonBuilder {
    private static ScannerUserInputService scanner = new ScannerUserInputService();

    public static void main(String[] args) {
        PersonBuilder personBuilder = new PersonBuilder(scanner);
        personBuilder.runProgram();
    }
    private ScannerUserInputService scannerUserInputService;
    private IOServiceInterface csvIOService;
    private IOServiceInterface jsonIOService;
    private PersonBuilderService personBuilder;
    private Logger logger;
    private List<Person> peopleList;
    private final String CSV_FILE = "PEOPLE.csv";
    private final String JSON_FILE = "PEOPLE.json";
    public PersonBuilder(ScannerUserInputService scannerUserInputService) {
        this.scannerUserInputService = scannerUserInputService;
        csvIOService = IOServiceSetter.setupIOService(FileType.CSV);
        jsonIOService = IOServiceSetter.setupIOService(FileType.JSON);
        personBuilder = new PersonBuilderService(scannerUserInputService);
        logger = Logger.getInstance();
        peopleList = new ArrayList<>();
    }

    public boolean restoreFile() {
        logger.log("============== RESTORE OPTIONS ==============");
        logger.log("Enter [1] to restore list from existing file");
        logger.log("Enter [2] to start the list from scratch");
        int selection = scannerUserInputService.getIntegerInput("Enter one of the two options above: ",1,2);
        return selection == 1;
    }

    public int restoreFileOptions() {
        logger.log("============== FILE OPTIONS ==============");
        logger.log("Enter [1] to restore list from CSV file");
        logger.log("Enter [2] to restore list from JSON file");
        logger.log("Enter [3] to return to main menu");
        logger.log("Enter [4] to exit out of the program");
        int selection = scannerUserInputService.getIntegerInput("Enter one of the three options above: ",1,4);
        return selection;
    }

    public int selectOption() {
        logger.log("============== MAIN OPTIONS ==============");
        logger.log("Enter [1] to add a person to the list");
        logger.log("Enter [2] to print a current list");
        logger.log("Enter [3] to export the list and exit out of the program");
        int selection = scannerUserInputService.getIntegerInput("Enter one of the three options above: ",1,3);
        return selection;
    }

    public int exportOptions() {
        logger.log("============== EXPORT OPTIONS ==============");
        logger.log("Enter [1] to export list as CSV");
        logger.log("Enter [2] to export list as JSON");
        logger.log("Enter [3] to export list as both CSV and JSON");
        logger.log("Enter [4] to exit out of the program");
        int selection = scannerUserInputService.getIntegerInput("Enter one of the three options above: ",1,4);
        return selection;
    }

    public void runProgram() {

        boolean userWantsToRestore = restoreFile();
        if (userWantsToRestore) {
            try {
                if (csvIOService.readFromFile() != null) {
                    logger.log("CSV file exists!");
                }
                if (jsonIOService.readFromFile() != null) {
                    logger.log("JSON file exists!");
                }
            } catch (Exception e) {
                logger.log("You cannot restore a file that does not exist!");
            }

            int restoreFileOptions = restoreFileOptions();
            switch (restoreFileOptions) {
                case 1:
                    if (csvIOService.readFromFile() != null) {
                        logger.log("CSV file is read");
                        peopleList = csvIOService.readFromFile();
                    } else {
                        logger.log("CSV file does not exist for reading");
                        runProgram();
                    }
                    break;
                case 2:
                    if (jsonIOService.readFromFile() != null) {
                        logger.log("JSON file is read");
                        peopleList = jsonIOService.readFromFile();
                    } else {
                        logger.log("CSV file does not exist for reading");
                        runProgram();
                    }
                    break;
                case 3:
                    runProgram();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    logger.log("Invalid selection.");
            }
        }

        if (!userWantsToRestore) {
            csvIOService.resetFile();
            jsonIOService.resetFile();
        }
        boolean processing = true;
        while (processing) {
            int selection = selectOption();
            switch (selection) {
                case 1:
                    Person newPerson = personBuilder.createPerson();
                    peopleList.add(newPerson);
                    break;
                case 2:
                    System.out.println(peopleList);
                    break;
                case 3:
                    int exportChoice = exportOptions();
                    switch (exportChoice) {
                        case 1:
                            csvIOService.writeToFile(peopleList);
                            break;
                        case 2:
                            jsonIOService.writeToFile(peopleList);
                            break;
                        case 3:
                            csvIOService.writeToFile(peopleList);
                            jsonIOService.writeToFile(peopleList);
                            break;
                        case 4:
                            System.exit(0);
                            break;
                        default:
                            logger.log("Invalid selection.");
                    }
                    break;
                default:
                    logger.log("Invalid selection.");
            }
        }
    }
}