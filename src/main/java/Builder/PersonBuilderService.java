package Builder;

/* following Jay's advice on creating separate service class */
public class PersonBuilderService {
    private ScannerUserInputService scannerUserInputService;
    public PersonBuilderService(ScannerUserInputService scannerUserInputService) {
        this.scannerUserInputService = scannerUserInputService;
    }
    public Person createPerson() {
        String firstName = scannerUserInputService.getStringInput("Enter first name: ");
        String lastName = scannerUserInputService.getStringInput("Enter last name: ");
        int birthYear = scannerUserInputService.getIntegerInput("Enter the year of birth: ",1900,2022);
        int birthMonth = scannerUserInputService.getIntegerInput("Enter the month of birth: ", 1,12);
        int birthDay = scannerUserInputService.getIntegerInput("Enter the day of birth: ", 1, 31);
        Person person = new Person(firstName, lastName, birthYear, birthMonth, birthDay);
        return person;
    }
}