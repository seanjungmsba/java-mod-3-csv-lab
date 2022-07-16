package InputOutputService;

// Enum also makes the file more readable and code-writing more effective
public enum FileType {
    CSV("PEOPLE.csv"),
    JSON("PEOPLE.json");
    String fileName;
    private FileType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}