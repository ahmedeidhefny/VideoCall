package com.ahmedeid.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.ahmedeid.videocall.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUi();
        initClickAction();
        initData();

    }

    private void initUi() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void initClickAction() {
        binding.btnCreate.setOnClickListener(v->{
            createNewAccount();
        });

        binding.btnLogin.setOnClickListener(v->{
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });
    }

    private void initData() {
    }

    private void createNewAccount() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String name = binding.etName.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            createFirebaseAuthAccount(email, password, name);
        }else {
            Toast.makeText(this, "Email or Password is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void createFirebaseAuthAccount(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    addNewFirestoreUser(email, password, name);
                    Toast.makeText(SignUpActivity.this, "Created An Account..!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignUpActivity.this, ""+ task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addNewFirestoreUser(String email, String password,  String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        db.collection("Users").document().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
               }else {
                    Toast.makeText(SignUpActivity.this, ""+ task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}