package Builder;

import java.util.Scanner;

/* following Jay's advice on creating separate service class */
public class ScannerUserInputService {
    private Scanner scanner;
    private Logger logger;

    public ScannerUserInputService() {
        this.scanner = new Scanner(System.in);
        logger = Logger.getInstance();
    }

    /* exception handling from user input for integer values */
    public int getIntegerInput(String prompt, int min, int max) {
        logger.log(prompt);
        try {
            String s = scanner.nextLine();
            int i = Integer.parseInt(s);
            if (!(i >= min && i <= max)) {
                logger.log("ERROR: Re-enter the valid input: ");
                return getIntegerInput(prompt, min, max);
            }
            return i;
        } catch (Exception e) {
            logger.log("ERROR: Re-enter the valid input: ");
            return getIntegerInput(prompt, min, max);
        }
    }

    /* exception handling from user input for string values */
    public String getStringInput(String prompt) {
        logger.log(prompt);
        try {
            String s = scanner.nextLine();
            if (s.equals("")) {
                System.out.print("ERROR: ENTER THE VALID INPUT: ");
                return getStringInput(prompt);
            }
            return s;
        } catch (Exception e) {
            System.out.print("ERROR: ENTER THE VALID INPUT: ");
            return getStringInput(prompt);
        }
    }

    public void close() {
        scanner.close();
    }
}