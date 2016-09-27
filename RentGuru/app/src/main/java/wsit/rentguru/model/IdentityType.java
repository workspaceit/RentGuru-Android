package wsit.rentguru.model;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by workspaceinfotech on 8/3/16.
 */
public class IdentityType implements Serializable {

    private int id;
    private String name;
    private String timestamp;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
