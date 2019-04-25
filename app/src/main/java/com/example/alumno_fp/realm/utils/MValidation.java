package com.example.alumno_fp.realm.utils;

public class MValidation {

    public static boolean validateEmpty(String string){
        boolean isValid = true;
        if (string.isEmpty()){
            isValid = false;
        }

        return isValid;
    }
}
