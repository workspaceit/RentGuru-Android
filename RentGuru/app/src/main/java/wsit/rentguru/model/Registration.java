package wsit.rentguru.model;

/**
 * Created by workspaceinfotech on 8/5/16.
 */
public class Registration {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private IdentityType identityType;
    private String identityDocToken;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    public String getIdentityDocToken() {
        return identityDocToken;
    }

    public void setIdentityDocToken(String identityDocToken) {
        this.identityDocToken = identityDocToken;
    }



}
