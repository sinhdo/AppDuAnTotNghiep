package com.example.appduantotnghiep.database;

import com.google.firebase.database.DataSnapshot;

public class FirebaseRole {
    public static boolean isAdmin(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            Boolean isAdmin = dataSnapshot.child("user_type").getValue(Boolean.class);
            return isAdmin != null && isAdmin;
        }
        return false;
    }
}
