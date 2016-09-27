package wsit.rentguru.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/5/16.
 */
public class AuthCredential extends AppCredential implements Serializable {


    private String accesstoken;
    private boolean verified;
    private boolean blocked;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }







}
