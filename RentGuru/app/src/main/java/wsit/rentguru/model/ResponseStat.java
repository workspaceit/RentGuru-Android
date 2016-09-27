package wsit.rentguru.model;

import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class ResponseStat {

    private boolean status;
    private boolean isLogin;
    private String msg;
    private ArrayList<RequestError> requestErrors;

    public ResponseStat() {
        this.status = false;
        this.isLogin = false;
        this.msg = "";
        this.requestErrors = new ArrayList<RequestError>();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public ArrayList<RequestError> getRequestErrors() {
        return requestErrors;
    }

    public void setRequestErrors(ArrayList<RequestError> requestErrors) {
        this.requestErrors = requestErrors;
    }
}
