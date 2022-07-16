package Builder;

/* following lecture's advice on creating singleton Logger class */
public class Logger {

    private static Logger logger = null;

    private Logger() {
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }

        return logger;
    }

    public void log(String message) {
        System.out.println(message);
    }
}