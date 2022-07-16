package InputOutputService;

public class IOServiceSetter {

    public static IOServiceInterface setupIOService(FileType fileType) {
        if (fileType == FileType.CSV) {
            return new CsvIOService(FileType.CSV.fileName);
        } else if (fileType == FileType.JSON) {
            return new JsonIOService(FileType.JSON.fileName);
        }
        return null; // will never happen because we're using an enum and thus required to satisfy the compiler
    }
}