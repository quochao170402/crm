package cybersoft.javabackend.java18.crm.exception;

public class DatabaseNotFoundException extends RuntimeException {
    public DatabaseNotFoundException(String e) {
        super(e);
    }

    public DatabaseNotFoundException() {
        super();
    }
}
