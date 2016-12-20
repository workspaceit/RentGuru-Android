package wsit.rentguru24.model;

/**
 * Created by Tomal on 11/2/2016.
 */

public class UserPaypalCredential {
    private int id;
    private AppCredential appCredential;
    private String email;
    private String createdDate;

    public UserPaypalCredential() {
        this.id=0;
        this.appCredential=new AppCredential();
        this.email="";
        this.createdDate="";
    }

    public AppCredential getAppCredential() {
        return appCredential;
    }

    public void setAppCredential(AppCredential appCredential) {
        this.appCredential = appCredential;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
