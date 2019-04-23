package com.example.alumno_fp.realm.application;

import android.app.Application;

import com.example.alumno_fp.realm.models.Place;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MApplication extends Application {

    public static AtomicInteger placeId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        placeId = getByTable(realm, Place.class);
        realm.close();
    }

    private void setUpRealmConfig(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getByTable(Realm realm, Class<T> anyclass){
        RealmResults<T> results = realm.where(anyclass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }
}
