package com.example.shamsulkarim.vastvocabulary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{


    EditText loginEmail, loginPassword;
    TextView dontHaveAnAccount;
    Button singIn;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser() != null){

            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        loginEmail = (EditText) findViewById(R.id.email_login);
        loginPassword = (EditText) findViewById(R.id.password_login);
        dontHaveAnAccount = (TextView) findViewById(R.id.dontHaveAnAccount);
        singIn = (Button)findViewById(R.id.singIn);

        singIn.setOnClickListener(this);
        dontHaveAnAccount.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {


        if(view == singIn){
            singIn();

        }

        if(view == dontHaveAnAccount){

            finish();
            startActivity(new Intent(this, RegisterActivity.class));

        }

    }


    private  void singIn(){

        String email = this.loginEmail.getText().toString().trim();
        String password = this.loginPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Enter email please",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Enter password please",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("You are loggin in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                            Toast.makeText(SignInActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, SyncingFirebaseToSQL.class));
                            finish();
                        }else {

                            Toast.makeText(SignInActivity.this, "Login Failed",Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

}