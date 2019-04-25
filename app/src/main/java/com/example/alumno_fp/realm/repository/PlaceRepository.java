package com.example.alumno_fp.realm.repository;

import com.example.alumno_fp.realm.models.Place;

import io.realm.Realm;
import io.realm.RealmResults;

public class PlaceRepository {

    public static void insert(Realm realm,String name,String country){
        realm.beginTransaction();
        Place place = new Place(name,country);
        realm.copyToRealm(place);
        realm.commitTransaction();
    }

    public static RealmResults<Place> select(Realm realm,RealmResults<Place> places){
        places = realm.where(Place.class).findAll();
        return places;
    }

    public static void update(Realm realm,Place place,String name,String country){
        realm.beginTransaction();
        place.setName(name);
        place.setCountry(country);
        realm.commitTransaction();
    }

    public static void delete(Realm realm,Place place){
        realm.beginTransaction();
        assert place != null;
        place.deleteFromRealm();
        realm.commitTransaction();
    }
}
