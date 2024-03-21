package com.example.oilWellChecklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.oilWellChecklist.SplashScreen.SplashFragment;
import com.example.oilWellChecklist.database_models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText _emailText, _passwordText, _phoneText, _nameText;
    private Button _loginButton;
    private Boolean _error = true, _register = false;
    private static final Pattern Password_Pattern =
            Pattern.compile("^(?=.*[!@#$%^&+=])" +
                                  "(?=.*[0-9])" +
                                  "(?=.*[A-Z])" +
                                  "(?=\\S+$).{8,}" +
                                  "$");

    private FirebaseAuth _authorizer;
    private FirebaseUser _currentUser;

    private GoogleSignInClient _googleSignInClient;

    private ActivityResultLauncher<Intent> _activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        _authorizer = FirebaseAuth.getInstance();
        _currentUser = _authorizer.getCurrentUser();
        _emailText = findViewById(R.id.username_text);
        _passwordText = findViewById(R.id.password_text);
        _nameText = findViewById(R.id.name_text);
        _phoneText = findViewById(R.id.phone_text);
        _loginButton =  findViewById(R.id.login_button);
        SignInButton _googleLoginButton = findViewById(R.id.google_login_button);

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

        _loginButton.setOnClickListener(this::onLoginClick);
        register.setOnClickListener(this::onRegisterClick);
        forgotPassword.setOnClickListener(this::onForgotPasswordClick);
        _googleLoginButton.setOnClickListener(this::googleOnLoginClick);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        _googleSignInClient = GoogleSignIn.getClient(this, gso);

        _activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) ->
                {
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);
                    }
                });


        SetVisibility(View.GONE, getString(R.string.login));

    }

    public void onLoginClick(View loginButtonView)
    {
        if(_error)
        {
            Toast.makeText(this, R.string.please_resolve_email_password_issues, Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(_register)
            {
               RegisterUser();
            }
            else
            {
                _authorizer.signInWithEmailAndPassword(_emailText.getText().toString(), _passwordText.getText().toString())
                        .addOnFailureListener(this::SignOnFailedListener)
                        .addOnSuccessListener(this::SignOnSuccessListener);
            }

        }
    }

    private void RegisterUser()
    {
        if(_currentUser != null)
        {
            if(_currentUser.isEmailVerified())
            {
                Toast.makeText(this, R.string.user_already_exists, Toast.LENGTH_SHORT).show();
            }
            else
            {
                _currentUser.sendEmailVerification();
                Toast.makeText(this, R.string.registration_email, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
           _authorizer.createUserWithEmailAndPassword(_emailText.getText().toString(), _passwordText.getText().toString())
                    .addOnFailureListener(this::RegisterUserFailureListener)
                    .addOnSuccessListener(this::RegisterUserSuccessListener);
        }
    }

    public void onRegisterClick(View registerUserButtonView)
    {
        if(_register)
        {
            SetVisibility(View.GONE, getString(R.string.login));
        }
        else
        {
            SetVisibility(View.VISIBLE, getString(R.string.register));
        }
        _register = !_register;
    }

    public void onForgotPasswordClick(View forgotPasswordButtonView)
    {
        String email = _emailText.getText().toString();

        if(email.isEmpty())
        {
            Toast.makeText(this, R.string.enter_email_address, Toast.LENGTH_SHORT).show();
        }
        else
        {
            _authorizer.sendPasswordResetEmail(email)
                    .addOnFailureListener(this::EmailFailedListener)
                    .addOnSuccessListener(this::EmailSucceededListener);
        }
    }

    private void SetVisibility(int visibility, String name)
    {
        _nameText.setVisibility(visibility);
        _phoneText.setVisibility(visibility);
        _nameText.setText("");
        _phoneText.setText("");
        _loginButton.setText(name);
    }

    private void EmailFailedListener(@NonNull Exception ex)
    {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void EmailSucceededListener(Void aVoid)
    {
        Toast.makeText(this, R.string.check_inbox, Toast.LENGTH_SHORT).show();
    }

    private void SignOnFailedListener(@NonNull Exception ex)
    {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void SignOnSuccessListener(AuthResult authResult)
    {
        _currentUser = authResult.getUser();
        if(_currentUser == null)
        {
            Toast.makeText(LoginActivity.this, R.string.error_signOn, Toast.LENGTH_SHORT).show();
        }
        else if(_currentUser.isEmailVerified())
        {
            setContentView(R.layout.activity_splash_screen);
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer,new SplashFragment());
            fragmentTransaction.commit();
            new CountDownTimer(2000, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                    finish();
                }
            }.start();
        }
        else
        {
            Toast.makeText(LoginActivity.this, getString(R.string.verify_email_first), Toast.LENGTH_SHORT).show();
        }
    }


    private void RegisterUserFailureListener(@NonNull Exception ex)
    {
        Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void RegisterUserSuccessListener(AuthResult authResult)
    {
        _currentUser = authResult.getUser();

        if(_currentUser == null)
        {
            Toast.makeText(LoginActivity.this, R.string.error_registering_user_please_try_again, Toast.LENGTH_SHORT).show();
        }
        else
        {
           SendEmailVerification();
        }
    }

    private void SaveUserToDatabase()
    {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
        userReference.child(_currentUser.getUid()).setValue(
                new User(GetText(_nameText), GetText(_emailText), GetText(_phoneText)));
    }

    private String GetText(TextView view)
    {
        return view.getText().toString();
    }


    private void SendEmailVerification()
    {
        Toast.makeText(this, R.string.registration_email, Toast.LENGTH_SHORT).show();

        _currentUser.sendEmailVerification()
                .addOnSuccessListener(this::EmailVerificationSuccessListener)
                .addOnFailureListener(this::EmailVerificationFailureListener);

    }

    private void EmailVerificationSuccessListener(Void aVoid)
    {
        Toast.makeText(LoginActivity.this, R.string.registration_successful_please_verify_email, Toast.LENGTH_SHORT).show();
        SaveUserToDatabase();
        SetVisibility(View.GONE, getString(R.string.login));
    }

    private void EmailVerificationFailureListener(Exception ex)
    {
        Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void googleOnLoginClick(View view) {
        Intent signInIntent = _googleSignInClient.getSignInIntent();
        _activityResultLauncher.launch(signInIntent);

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

            _authorizer.signInWithCredential(firebaseCredential)
                    .addOnFailureListener(this::SignOnFailedListener)
                    .addOnSuccessListener(this::SignOnSuccessListener);


        } catch (ApiException ex) {
            Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
