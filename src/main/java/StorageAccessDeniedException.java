public class StorageAccessDeniedException extends Exception {
    private String saveFilePath;

    public StorageAccessDeniedException(String message, Throwable cause, String saveFilePath) {
        super(message, cause);
        this.saveFilePath = saveFilePath;
    }

    public String getSaveFilePath() {
        return this.saveFilePath;
    }
}
