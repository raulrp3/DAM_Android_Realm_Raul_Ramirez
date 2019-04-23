package com.example.alumno_fp.realm;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alumno_fp.realm.adapters.PlaceAdapter;
import com.example.alumno_fp.realm.models.Place;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Place>> {

    public Realm realm;
    private FloatingActionButton buttonAdd;
    private RealmResults<Place> places;
    private RecyclerView rvPlaces;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        initUI();
    }

    private void initUI(){
        buttonAdd = findViewById(R.id.fabAdd);
        select();
        places.addChangeListener(this);
        rvPlaces = findViewById(R.id.rvPlaces);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        placeAdapter = new PlaceAdapter(getApplicationContext(),places);
        rvPlaces.setAdapter(placeAdapter);

        listeOnClick();
    }

    private void listeOnClick(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPlace();
            }
        });

        placeAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place = places.get(rvPlaces.getChildAdapterPosition(v));
                updatePlace(place);
            }
        });
    }

    private void insertPlace(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.place_dialog, null);
        builder.setView(view);

        final EditText etName = view.findViewById(R.id.etName);
        final EditText etCountry = view.findViewById(R.id.etCountry);

        builder.setMessage("Añadir un nuevo lugar");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                if (validate(name) && validate(country)){
                    insert(name,country);
                }else{
                    Toast.makeText(getApplicationContext(),"¡Campos obligatorios!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updatePlace(final Place place){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.place_dialog, null);
        builder.setView(view);

        final EditText etName = view.findViewById(R.id.etName);
        final EditText etCountry = view.findViewById(R.id.etCountry);

        builder.setMessage("Modificar el lugar");
        etName.setText(place.getName());
        etCountry.setText(place.getCountry());

        etName.setSelection(etName.getText().length());
        etCountry.setSelection(etCountry.getText().length());

        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                if (validate(name) && validate(country)){
                    update(name,country,place);
                }else{
                    Toast.makeText(getApplicationContext(),"¡Campos obligatorios!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onChange(RealmResults<Place> places) {
        placeAdapter.notifyDataSetChanged();
    }

    private boolean validate(String string){
        boolean isValid = true;
        if (string.isEmpty()){
            isValid = false;
        }

        return isValid;
    }

    public void insert(String name,String country){
        realm.beginTransaction();
        Place place = new Place(name,country);
        realm.copyToRealm(place);
        realm.commitTransaction();
    }

    public void select(){
        places = realm.where(Place.class).findAll();
    }

    public void update(String name,String country,Place place){
        realm.beginTransaction();
        place.setName(name);
        place.setCountry(country);
        realm.commitTransaction();
    }
}
