package com.example.contrasena;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText etSiteName, etUsername, etPassword, etNotes;
    private Button btnUpdate;
    private PasswordRepository repository;

    private String passwordId; // ID del documento en Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        etSiteName = findViewById(R.id.etSiteName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etNotes = findViewById(R.id.etNotes);
        btnUpdate = findViewById(R.id.btnUpdate);

        repository = new PasswordRepository();

        // Obtener datos del Intent
        passwordId = getIntent().getStringExtra("id");
        etSiteName.setText(getIntent().getStringExtra("siteName"));
        etUsername.setText(getIntent().getStringExtra("username"));
        etPassword.setText(getIntent().getStringExtra("password"));
        etNotes.setText(getIntent().getStringExtra("notes"));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String siteName = etSiteName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String notes = etNotes.getText().toString();

                if (siteName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(UpdatePasswordActivity.this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                Password updatedPassword = new Password(passwordId, siteName, username, password, notes);
                repository.updatePassword(passwordId, updatedPassword);

                Toast.makeText(UpdatePasswordActivity.this, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
