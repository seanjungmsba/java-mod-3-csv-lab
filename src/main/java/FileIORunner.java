import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class FileIORunner {

    public static final String FILE_NAME = "person.csv";

    public static void main(String[] args) throws Exception {
        UserOutputService userOutputService = new SysoutUserOutputService();
        try (UserInputService userInputService = new ScannerUserInputService(userOutputService);) {
            ChooseOptionService chooseOptionService = new ChooseOptionService(userInputService);
            chooseOptionService.chooseMode();
            chooseOptionService.chooseOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method reads person from csv file and create new Student object,
    // which is then put into the Singleton List
    public static void readFromFile(String fileName) {
        String returnString = new String();
        Scanner fileReader = null;
        try {
            File myFile = new File(fileName);
            fileReader = new Scanner(myFile);
            while (fileReader.hasNextLine()) {
                returnString = fileReader.nextLine();
                // process returns string into a set of variables
                // split by comma
                String[] personInfo = returnString.split(",");
                // create a new student
                // firstName, lastName, birthYear, birthMonth, birthDay
                String firstName = personInfo[0];
                String lastName= personInfo[1];
                int birthYear= Integer.parseInt(personInfo[2]);
                int birthMonth= Integer.parseInt(personInfo[3]);
                int birthDay= Integer.parseInt(personInfo[4]);
                Person person = new Person(firstName, lastName, birthYear, birthMonth, birthDay);
                PeopleService.getInstance().addPerson(person);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileReader != null)
                fileReader.close();
        }
    }

     static void writeToFile(String fileName, String text) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.write(text);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileWriter != null)
                fileWriter.close();
        }
    }
}

interface UserInputService extends AutoCloseable {
    String getUserInput(String prompt);
}
interface UserOutputService {
    void printMessage(String message);
}

class SysoutUserOutputService implements UserOutputService {
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}

class ScannerUserInputService implements UserInputService {
    private Scanner scanner;
    private UserOutputService userOutputService;
    public ScannerUserInputService(UserOutputService userOutputService) {
        this.scanner = new Scanner(System.in);
        this.userOutputService = userOutputService;
    }
    public String getUserInput(String prompt) {
        userOutputService.printMessage(prompt);
        String input = scanner.nextLine();
        if (input.isBlank()) {
            return getUserInput(prompt);
        }
        return input;
    }
    @Override
    public void close() throws Exception {
        scanner.close();
    }
}

class PersonBuilderService {
    private UserInputService userInputService;
    public PersonBuilderService(UserInputService userInputService) {
        this.userInputService = userInputService;
    }
    public Person createPerson() {
        String firstName = userInputService.getUserInput("What's the person's first name?");
        String lastName = userInputService.getUserInput("What's the person's last name?");
        int birthYear = Integer.parseInt(userInputService.getUserInput("What's the person's year of birth?"));
        int birthMonth = Integer.parseInt(userInputService.getUserInput("What's the person's month of birth?"));
        int birthDay = Integer.parseInt(userInputService.getUserInput("What's the person's day of birth?"));
        Person person = new Person(firstName, lastName, birthYear, birthMonth, birthDay);
        PeopleService.getInstance().addPerson(person);
        return person;
    }
}

class PeopleService {
    private List<Person> people;
    private PeopleService() {
        this.people = new ArrayList<>();
    }
    private static PeopleService singleton;
    public static PeopleService getInstance() {
        if (singleton == null) {
            singleton = new PeopleService();
        }
        return singleton;
    }
    public void addPerson(Person p) {
        people.add(p);
    }
    public List<Person> getPeople() {
        return people;
    }
    @Override
    public String toString() {
        return people.toString();
    }
}

class ChooseOptionService {
    private UserInputService userInputService;
    public ChooseOptionService(UserInputService userInputService) {
        this.userInputService = userInputService;
    }

    public void chooseMode() {
        String option = userInputService.getUserInput("Enter '1' to restore a file or '2' to start new.");
        if (option.equals("1")) {
            // checking if the file exists; if so, read it
            if (new File(FileIORunner.FILE_NAME).exists())
                FileIORunner.readFromFile(FileIORunner.FILE_NAME);
        } else if (option.equals("2")) {
            chooseOptions();
        } else {
            System.exit(0);
        }
    }
    public void chooseOptions() {
        boolean processing = true;
        while (processing) {
            String option = userInputService.getUserInput("Type 'a' to add a person to the list, 'p' to print a list of current people, or simply press anything else to exit the program");
            if (option.equals("a")) {
                PersonBuilderService personBuilderService = new PersonBuilderService(userInputService);
                personBuilderService.createPerson();
            } else if (option.equals("p")) {
                System.out.println(PeopleService.getInstance().getPeople());
            } else {
                StringBuilder peopleCSV = new StringBuilder();
                // for each person in PeopleService,
                for (Person person: PeopleService.getInstance().getPeople()) {
                    // format each person as csv
                    String personString = person.formatAsCSV();
                    // append it to peopleCSV and \n
                    peopleCSV.append(personString + "\n");
                }
                try {
                    FileIORunner.writeToFile("person.csv", peopleCSV.toString());
                } catch (IOException e) {
                    System.err.println("Something is wrong");
                } finally {
                    System.out.println("Program ended");
                    processing = false;
                    return;
                }

            }
        }

    }
}
