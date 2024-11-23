package com.example.contrasena;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePasswordActivity extends AppCompatActivity {

    private EditText etSiteName, etUsername, etPassword, etNotes;
    private Button btnSave;

    private PasswordRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        etSiteName = findViewById(R.id.etSiteName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etNotes = findViewById(R.id.etNotes);
        btnSave = findViewById(R.id.btnSave);

        repository = new PasswordRepository();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String siteName = etSiteName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String notes = etNotes.getText().toString();

                if (siteName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CreatePasswordActivity.this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                Password newPassword = new Password(null, siteName, username, password, notes);
                repository.createPassword(newPassword);

                Toast.makeText(CreatePasswordActivity.this, "Contraseña guardada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
