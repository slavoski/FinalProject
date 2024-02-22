package com.example.oilWellChecklist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.PasswordAuthentication;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText _emailText, _passwordText;
    private Boolean _error = true;
    private static final Pattern Password_Pattern =
            Pattern.compile("^(?=.*[!@#$%^&+=])" +
                                  "(?=.*[0-9])" +
                                  "(?=.*[A-Z])" +
                                  "(?=\\S+$).{8,}" +
                                  "$");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        _emailText = findViewById(R.id.username_text);
        _passwordText = findViewById(R.id.password_text);

        Button login = findViewById(R.id.login_button);
        Button register = findViewById(R.id.register_user);
        Button forgotPassword = findViewById(R.id.forgot_password);

        _emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _error = false;
                if(Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches())
                {
                    _emailText.setError(null);
                }
                else
                {
                    _error = true;
                    _emailText.setError(getString(R.string.email_format));
                }
            }
        });

        _passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String password = s.toString().trim();
                //noinspection ReassignedVariable
                String result = null;
                _error = false;
                if(password.isEmpty())
                {
                    result = getString(R.string.password_empty);
                    _error = true;
                }
                else if(!Password_Pattern.matcher(password).matches())
                {
                    result = getString(R.string.password_validation_error);
                    _error = true;
                }

                _passwordText.setError(result);
            }
        });

        login.setOnClickListener(this::onLoginClick);
        register.setOnClickListener(this::onRegisterClick);
        forgotPassword.setOnClickListener(this::onForgotPasswordClick);


    }

    public void onLoginClick(View loginButtonView)
    {
        if(_error)
        {
            Toast toast = Toast.makeText(this, "Please Resolve Email/Password Issues", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onRegisterClick(View registerUserButtonView)
    {

    }

    public void onForgotPasswordClick(View forgotPasswordButtonView)
    {

    }

}
