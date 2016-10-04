package com.example.hp.databasesqlite;

/**
 * Created by HP on 9/8/2016.
 */
public class Flower {



    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name+"  "+description;
    }
}
