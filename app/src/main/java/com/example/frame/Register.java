package com.example.frame;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {
    //Initialize Variable
   private EditText etName, etMobile, etEmail, etUsername, etPassword, etConfirmPassword;
    private Button btSubmit;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        //Assign Variable
        etName = findViewById(R.id.et_name);
        etMobile = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);
        etUsername = findViewById(R.id.et_user);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

     //   btSubmit = findViewById(R.id.bt_submit);

        findViewById(R.id.bt_submit).setOnClickListener(this);

    }

    private void userSignUp(){
    String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if(name.isEmpty()){
         etName.setError("Name is required");
         etName.requestFocus();
         return;
        }
    if(password.isEmpty()){
        etMobile.setError("Password required");
        etMobile.requestFocus();
        return;
    }
    if(password.length() < 10){
        etMobile.setError("Must be valid phone number - 10 character");
        etMobile.requestFocus();
        return;
    }
    if(email.isEmpty()){
        etEmail.setError("email required");
        etEmail.requestFocus();
        return;
    }

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        etMobile.setError("Please enter a valid email");
        etMobile.requestFocus();
        return;
    }

        if(username.isEmpty()){
            etEmail.setError("username required");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("password required");
            etPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            etConfirmPassword.setError("password required");
            etConfirmPassword.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getRegisterApi()
                .createUser(name,mobile,email,username,password,confirmPassword);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String s = response.body().string();
                    Toast.makeText(Register.this, s, Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_submit:
                userSignUp();
            break;
        }
    }
}