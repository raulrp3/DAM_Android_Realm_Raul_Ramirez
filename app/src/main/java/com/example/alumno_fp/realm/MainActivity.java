package com.example.alumno_fp.realm;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alumno_fp.realm.adapters.PlaceAdapter;
import com.example.alumno_fp.realm.interfaces.AdapterCustomClick;
import com.example.alumno_fp.realm.models.Category;
import com.example.alumno_fp.realm.models.Place;
import com.example.alumno_fp.realm.repositories.CategoryRepository;
import com.example.alumno_fp.realm.repositories.PlaceRepository;
import com.example.alumno_fp.realm.utils.MValidation;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Place>> {

    public Realm realm;
    private FloatingActionButton buttonAdd;
    private RealmResults<Place> places;
    private RecyclerView rvPlaces;
    private PlaceAdapter placeAdapter;
    private List<Category> categories;
    private ArrayAdapter<Category> categoryAdapter;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        initUI();
    }

    private void initUI(){
        buttonAdd = findViewById(R.id.fabAdd);
        places = PlaceRepository.select(realm,places);
        places.addChangeListener(this);
        categories = CategoryRepository.getInstance().getCategories();
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        rvPlaces = findViewById(R.id.rvPlaces);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        placeAdapter = new PlaceAdapter(getApplicationContext(), places, new AdapterCustomClick() {
            @Override
            public void onClick(View view, int index) {
                Place place = places.get(index);
                dialogInsertUpdate(1,"Modificar un lugar", place);
            }

            @Override
            public void onLongClick(View view, int index) {
                Place place = places.get(index);
                dialogDelete(place);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInsertUpdate(0,"Añadir un nuevo lugar", null);
            }
        });

        rvPlaces.setAdapter(placeAdapter);
    }

    @Override
    public void onChange(RealmResults<Place> places) {
        placeAdapter.notifyDataSetChanged();
    }

    public void dialogInsertUpdate(int code, String message, final Place place){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.place_dialog, null);
        builder.setView(view);

        final EditText etName = view.findViewById(R.id.etName);
        final EditText etCountry = view.findViewById(R.id.etCountry);
        final Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (Category) spinnerCategory.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setMessage(message);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        switch (code){
            case 0:{
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String country = etCountry.getText().toString().trim();
                        if (MValidation.validateEmpty(name) && MValidation.validateEmpty(country)){
                            PlaceRepository.insert(realm,name,country,category);
                        }else{
                            Toast.makeText(getApplicationContext(),"¡Campos obligatorios! Rellen los campos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            break;
            case 1:{
                etName.setText(place.getName());
                etCountry.setText(place.getCountry());

                etName.setSelection(etName.getText().length());
                etCountry.setSelection(etCountry.getText().length());
                builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String country = etCountry.getText().toString().trim();
                        if (MValidation.validateEmpty(name) && MValidation.validateEmpty(country)){
                            PlaceRepository.update(realm,place,name,country);
                        }else{
                            Toast.makeText(getApplicationContext(),"¡Campos obligatorios! Rellen los campos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            break;
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void dialogDelete(final Place place){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.confirm_dialog, null);
        builder.setView(view);

        builder.setMessage("Eliminar un lugar");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlaceRepository.delete(realm,place);
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
}
