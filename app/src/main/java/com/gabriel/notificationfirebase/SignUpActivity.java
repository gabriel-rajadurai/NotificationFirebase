package com.gabriel.notificationfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button btReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        etEmail = findViewById(R.id.email_reg_et);
        etPass = findViewById(R.id.pass_reg_et);
        btReg = findViewById(R.id.register_bt);
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndRegister();
            }
        });
    }

    private void validateAndRegister() {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Please enter mail id!!");
        } else if (pass.isEmpty()) {
            etPass.setError("Please enter password!!");
        } else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SignUpActivity.this.onBackPressed();
                            } else {
                                Toast.makeText(SignUpActivity.this,
                                        "Account Registration failed. Please check if you have entered correct details!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}
