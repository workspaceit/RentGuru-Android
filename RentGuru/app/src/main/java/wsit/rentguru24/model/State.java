package wsit.rentguru24.model;

import java.io.Serializable;

/**
 * Created by Tomal on 11/30/2016.
 */

public class State implements Serializable {
    private int id;
    private String code;
    private String name;
    private String createdDate;

    public State(){
        this.id=0;
        this.code="";
        this.name="";
        this.createdDate="";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

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
