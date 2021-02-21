package Repository;

public class RepositoryException extends RuntimeException {
    public RepositoryException(){};

    public RepositoryException(String msg) {
        super(msg);
    }

    public RepositoryException(Exception ex) {
        super(ex);
    }
}
