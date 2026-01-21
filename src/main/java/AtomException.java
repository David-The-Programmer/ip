public class AtomException extends RuntimeException {
    private String remedy;

    public AtomException(String message, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    public String getRemedy() {
        return this.remedy;
    }
}
