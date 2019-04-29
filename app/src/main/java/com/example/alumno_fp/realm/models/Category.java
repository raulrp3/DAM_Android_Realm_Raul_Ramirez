package com.example.alumno_fp.realm.models;

import java.util.UUID;
import io.realm.RealmObject;

public class Category extends RealmObject{

    private String id;
    private String name;

    public Category(){

    }

    public Category(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
