package com.example.contrasena;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PasswordRepository {

    private final CollectionReference passwordsCollection;

    public PasswordRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        passwordsCollection = db.collection("passwords");
    }

    // Crear nueva contraseña
    public void createPassword(Password password) {
        passwordsCollection.add(password);
    }

    // Actualizar contraseña
    public void updatePassword(String id, Password password) {
        passwordsCollection.document(id).set(password);
    }

    // Eliminar contraseña
    public void deletePassword(String id) {
        passwordsCollection.document(id).delete();
    }

    // Obtener todas las contraseñas
    public CollectionReference getPasswordsCollection() {
        return passwordsCollection;
    }
}