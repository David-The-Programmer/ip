public class StorageAccessDeniedException extends Exception {
    private String remedy;

    public StorageAccessDeniedException(String message, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    public String getRemedy() {
        return this.remedy;
    }
}
