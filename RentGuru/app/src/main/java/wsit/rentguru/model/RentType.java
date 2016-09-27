package wsit.rentguru.model;

import java.io.Serializable;

/**
 * Created by workspaceinfotech on 8/16/16.
 */
public class RentType implements Serializable {

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
