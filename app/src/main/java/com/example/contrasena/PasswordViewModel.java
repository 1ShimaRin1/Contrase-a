package com.example.contrasena;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PasswordViewModel extends ViewModel {
    private final FirebaseFirestore db;
    private final MutableLiveData<List<Password>> passwordsLiveData;

    public PasswordViewModel() {
        db = FirebaseFirestore.getInstance();
        passwordsLiveData = new MutableLiveData<>(new ArrayList<>());
        fetchPasswords();
    }

    public void savePassword(String siteName, String username, String password, String notes) {
        String id = db.collection("passwords").document().getId();
        Password newPassword = new Password(id, siteName, username, password, notes);

        db.collection("passwords").document(id)
                .set(newPassword)
                .addOnSuccessListener(aVoid -> Log.d("PasswordViewModel", "Password saved successfully"))
                .addOnFailureListener(e -> Log.e("PasswordViewModel", "Error saving password", e));
    }

    public LiveData<List<Password>> getAllPasswords() {
        return passwordsLiveData;
    }

    private void fetchPasswords() {
        db.collection("passwords")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Password> passwords = queryDocumentSnapshots.toObjects(Password.class);
                    passwordsLiveData.setValue(passwords);
                })
                .addOnFailureListener(e -> Log.e("PasswordViewModel", "Error fetching passwords", e));
    }
}
