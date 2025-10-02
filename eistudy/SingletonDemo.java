class Logger {
    private static Logger instance;
    private Logger() {}
    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
    void log(String msg) { System.out.println("LOG: " + msg); }
}

public class SingletonDemo {
    public static void main(String[] args) {
        Logger l1 = Logger.getInstance();
        Logger l2 = Logger.getInstance();
        l1.log("First message");
        l2.log("Second message");
        System.out.println("Same instance? " + (l1 == l2));
    }
}
