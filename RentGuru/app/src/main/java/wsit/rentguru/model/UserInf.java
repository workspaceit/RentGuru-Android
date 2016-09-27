package wsit.rentguru.model;




import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class UserInf implements Serializable {


    private int id;
    private int addressId;
    private String firstName;
    private String lastName;
    private Picture profilePicture;
    private String createdDate;
    private UserAddress  userAddress;
    private String identityDocUrl;
    private IdentityType identityType;


    public UserInf()
    {

        this.id = 0;
        this.addressId = 0;
        this.firstName = "";
        this.lastName = "";
        this.createdDate = "";
        this.userAddress = new UserAddress();
        this.profilePicture = new Picture();
        this.identityDocUrl = "";
        this.identityType = new IdentityType();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getIdentityDocUrl() {
        return identityDocUrl;
    }

    public void setIdentityDocUrl(String identityDocUrl) {
        this.identityDocUrl = identityDocUrl;
    }

    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }
}
