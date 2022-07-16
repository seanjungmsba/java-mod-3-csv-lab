package InputOutputService;

import Builder.Person;

import java.util.List;

// inspired by other classmates to create the interface and enum,
// both of which makes the code easier to write and read

// since there are two file types: CSV and JSON, it makes sense to create interface
// that CSVIOService and JsonIOService can implement for the consistency sake.
public interface IOServiceInterface {

    public List<Person> readFromFile();
    public void writeToFile(List<Person> personList);
    public void resetFile();

}