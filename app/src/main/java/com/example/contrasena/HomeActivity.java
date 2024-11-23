package com.example.contrasena;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PasswordAdapter passwordAdapter;
    private List<Password> passwordList;
    private FirebaseFirestore db;
    private Button btnCreatePassword, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerViewPasswords);
        btnCreatePassword = findViewById(R.id.btnCreatePassword);
        btnLogout = findViewById(R.id.btnLogout);

        passwordList = new ArrayList<>();
        passwordAdapter = new PasswordAdapter(passwordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(passwordAdapter);

        db = FirebaseFirestore.getInstance();

        loadPasswords();

        btnCreatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreatePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadPasswords() {
        db.collection("passwords")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        passwordList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Password password = document.toObject(Password.class);
                            password.setId(document.getId()); // Guarda el ID del documento
                            passwordList.add(password);
                        }
                        passwordAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HomeActivity.this, "Error al cargar contraseñas", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
