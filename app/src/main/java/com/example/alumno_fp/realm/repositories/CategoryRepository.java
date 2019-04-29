package com.example.alumno_fp.realm.repositories;

import com.example.alumno_fp.realm.models.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryRepository {
    private static CategoryRepository repository = new CategoryRepository();
    private HashMap<String, Category> categories = new HashMap<>();

    public static CategoryRepository getInstance(){return repository;}

    private void saveCategory(Category lead){categories.put(lead.getId(),lead);}

    private CategoryRepository(){
        saveCategory(new Category("Turismo"));
        saveCategory(new Category("Viaje familiar"));
        saveCategory(new Category("Fiesta"));
    }

    public List<Category> getCategories(){return new ArrayList<>(categories.values()); }
}
