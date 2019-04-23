package com.example.alumno_fp.realm.models;

import com.example.alumno_fp.realm.application.MApplication;

import java.util.Date;

import io.realm.RealmObject;

public class Place extends RealmObject {

    private int id;
    private String name;
    private String country;
    private Date date;

    public Place(){

    }

    public Place(String name, String country) {
        this.id = MApplication.placeId.incrementAndGet();
        this.name = name;
        this.country = country;
        this.date = new Date();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", date=" + date +
                '}';
    }
}
