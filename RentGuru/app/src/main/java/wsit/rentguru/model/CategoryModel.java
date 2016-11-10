package wsit.rentguru.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workspaceinfotech on 8/8/16.
 */
public class CategoryModel implements Serializable{

    private int id;
    private String name;
    private int sortedOrder;
    private boolean isSubcategory;

    public boolean isSubcategory() {
        return isSubcategory;
    }

    public void setSubcategory(boolean subcategory) {
        isSubcategory = subcategory;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    private Picture picture;
    private ArrayList<CategoryModel> subcategory;

    public CategoryModel()
    {
        this.id = 0;
        this.name = "";
        this.sortedOrder = 0;
        this.subcategory = new ArrayList<CategoryModel>();
        this.isSubcategory=false;

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

    public int getSortedOrder() {
        return sortedOrder;
    }

    public void setSortedOrder(int sortedOrder) {
        this.sortedOrder = sortedOrder;
    }

    public ArrayList<CategoryModel> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(ArrayList<CategoryModel> subcategory) {
        this.subcategory = subcategory;
    }
}
