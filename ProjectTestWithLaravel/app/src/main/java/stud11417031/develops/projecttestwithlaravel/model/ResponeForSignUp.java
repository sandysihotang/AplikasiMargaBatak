package stud11417031.develops.projecttestwithlaravel.model;

import com.google.gson.annotations.SerializedName;

public class ResponeForSignUp {
    @SerializedName("error")
    private boolean err;

    @SerializedName("message")
    private String msg;

    public ResponeForSignUp(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}
