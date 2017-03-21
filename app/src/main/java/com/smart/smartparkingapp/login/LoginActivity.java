package com.smart.smartparkingapp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import com.smart.smartparkingapp.map.MapsActivity;
import com.smart.smartparkingapp.menu.MenuActivity;
import com.smart.smartparkingapp.R;
import com.smart.smartparkingapp.login.entity.LoginReqParam;
import com.smart.smartparkingapp.login.entity.Result;
import com.smart.smartparkingapp.login.interfaces.LoginPresenterOps;
import com.smart.smartparkingapp.login.interfaces.LoginViewOps;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginViewOps {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //Presenter interface
    LoginPresenterOps mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupMVP();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        addEmailsToAutoComplete();

    }

    @Override
    protected void onResume(){
        super.onResume();
        mPresenter.onResume();
    }
    private void setupMVP() {
        LoginPresenter presenter = new LoginPresenter(this);
        mPresenter = presenter;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        Log.d("...", "view attemptLogin invoked");
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        LoginReqParam loginReqParam = new LoginReqParam(email, password);

        mPresenter.attemptLogin(loginReqParam);

    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @Override
    public void showProgress(final boolean show) {

        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }


    private void addEmailsToAutoComplete() {
        List<String> emailAddressCollection = new ArrayList<>();
        emailAddressCollection.add("example@doe.com");

        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void showLoginError(Result msg) {
        switch (msg)
        {
            case LoginInvalid:
                mEmailView.setError(getString(R.string.error_invalid_email));
                break;
            case PasswordInvalid:
                mPasswordView.setError(getString(R.string.error_invalid_password));
                break;
            case AuthFailed:
                mPasswordView.setError(getString(R.string.error_authentication_failed));
                break;
            default:
                mEmailView.setError(getString(R.string.error_login_other));
                Log.e("...","showLoginError, unexpected result code: "+ msg);
                break;
        }

    }

    @Override
    public void resetLoginErrors() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public void showMapActivity() {
        Intent myIntent = new Intent(this, MapsActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }
}


