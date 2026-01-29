public class DeserialiserException extends Exception {
    private String remedy;

    public DeserialiserException(String message, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    public String getRemedy() {
        return this.remedy;
    }
}
