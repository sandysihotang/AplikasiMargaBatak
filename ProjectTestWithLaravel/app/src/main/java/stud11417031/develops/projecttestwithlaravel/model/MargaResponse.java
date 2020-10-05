package stud11417031.develops.projecttestwithlaravel.model;

public class MargaResponse {
    private boolean error;
    private String message;

    public MargaResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
