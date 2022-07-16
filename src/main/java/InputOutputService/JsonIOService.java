package InputOutputService;

import Builder.Logger;
import Builder.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class JsonIOService implements IOServiceInterface {

    private String jsonFileName;

    public JsonIOService(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public List<Person> readFromFile() {
        List<Person> peopleList = null;
        try {
            peopleList = Arrays.asList(new ObjectMapper().readValue(new File(jsonFileName), Person[].class));
        }
        catch (Exception e) {
            Logger.getInstance().log("ERROR: There is either no file in the directory or file has no contents. Please re-start the program if you wish to continue");
            System.exit(0);
        }
        return peopleList;

    }

    public void writeToFile(List<Person> personList) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(jsonFileName, true);
            String json = new ObjectMapper().writeValueAsString(personList);
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetFile() {
        try {
            new FileWriter(jsonFileName, false).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJSONFileName() {
        return jsonFileName;
    }

    public boolean doesJSONFileExist() {
        return this.jsonFileName != null;
    }

}