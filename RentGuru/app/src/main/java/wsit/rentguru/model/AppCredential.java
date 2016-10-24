package wsit.rentguru.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/4/16.
 */
public class AppCredential implements Serializable{

    private int id;
    private int userId;
    private int role;
    private String email;
    private String createdDate;
    private UserInf userInf;

    public AppCredential()
    {
        this.id = 0;
        this.userId = 0;
        this.role = 0;
        this.email = "";
        this.createdDate = "";
        this.userInf = new UserInf();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public UserInf getUser() {
        return userInf;
    }

    public void setUser(UserInf user) {
        this.userInf = user;
    }




}
