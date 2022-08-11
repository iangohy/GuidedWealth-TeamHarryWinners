package com.iangohy.harrywinnersclientapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.iangohy.harrywinnersclientapp.model.Client;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
    private EditText mPassword, mUsername;
    private ImageButton btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPassword = (EditText) findViewById(R.id.password);
        mUsername = (EditText) findViewById(R.id.enter_username);
        btnLogin = (ImageButton) findViewById(R.id.login);

        btnLogin.setOnClickListener(view -> {
            String userName = mUsername.getText().toString();

            FirebaseUtil.setUname(userName);
            DocumentReference userDoc = FirebaseUtil.getUserDocument();

            userDoc.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {
                        FirebaseUtil.generateUser();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            });
        });
    }
}
