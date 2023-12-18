package com.example.appduantotnghiep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appduantotnghiep.R;
import com.example.appduantotnghiep.database.FirebaseHelper;
import com.example.appduantotnghiep.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText ed_name,ed_email,ed_pass,ed_rePass;
    Button btn_signUp;
    TextView tv_goSignIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ed_name   = findViewById(R.id.ed_name);
        ed_email   = findViewById(R.id.ed_email);
        ed_pass   = findViewById(R.id.ed_pass);
        ed_rePass   = findViewById(R.id.ed_rePass);
        btn_signUp   = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        tv_goSignIn   = findViewById(R.id.tv_goSignIn);
        tv_goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void signUp(){
        String name = ed_name.getText().toString().trim();
        String email = ed_email.getText().toString().trim();
        String pass = ed_pass.getText().toString().trim();
        String rePass = ed_rePass.getText().toString().trim();
        if (checkValidate(name,email,pass,rePass)){

            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                firebaseUser = firebaseAuth.getCurrentUser();
                                String id = firebaseUser.getUid();
                                User user = new User();
                                user.setUsername(name);
                                user.setEmail(email);
                                user.setPassword(pass);
                                user.setId(id);
                                user.setPhone("");
                                user.setImg("https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=");
                                user.setLock(false);
                                user.setUser_type(false);
                                user.setWallet(0.0);
                                usersRef = firebaseHelper.getUsersRef();
                                usersRef.child(id).setValue(user);

                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finishAffinity();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean checkValidate(String name, String email, String pass, String rePass){
        if (email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Email, Password, RePassword không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }

        String emailForm = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        String nameForm1 = "^[a-zA-Z]+$";
        String nameForm2 = "^[a-zA-Z]+( [a-zA-Z]+)*$";

        if (!name.matches(nameForm1) || !name.matches(nameForm2)){
            Toast.makeText(this, "Tên không đúng định dạng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.matches(emailForm)){
            Toast.makeText(this, "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pass.length() < 6 || pass.length() > 12){
            Toast.makeText(this, "Mật khẩu phải có 6 đến 12 ký tự!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.equals(rePass)){
            Toast.makeText(this, "Xác nhận mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}