package stud11417031.develops.projecttestwithlaravel.model;

import java.util.List;

public class ResponseMarga {
    private boolean error;
    private List<Marga> margas;

    public ResponseMarga(boolean error, List<Marga> margas) {
        this.error = error;
        this.margas = margas;
    }

    public boolean isError() {
        return error;
    }

    public List<Marga> getMargas() {
        return margas;
    }
}
