package br.com.mespinas.fiapandroidpersistence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {

    private final String DEFAULT_LOGIN = "fiap";
    private final String DEFAULT_PASSWORD = "123";

    private final String KEY_APP_PREFERENCES = "login";
    private final String KEY_LOGIN = "login";

    private TextInputLayout tilLogin;
    private TextInputLayout tilPassword;
    private CheckBox cbKeepConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        cbKeepConnected = (CheckBox) findViewById(R.id.cbKeepConnected);

        if(isConnected())
            startApp();
    }

    private void login(View v) {
        if(isValidLogin()) {
            if(cbKeepConnected.isChecked())
                keepConnected();

            startApp();
        }
    }

    private boolean isValidLogin() {
        String login = tilLogin.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        if(login.equals(DEFAULT_LOGIN) && password.equals(DEFAULT_PASSWORD))
            return true;

        return false;
    }

    private void keepConnected() {
        String login = tilLogin.getEditText().getText().toString();

        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConnected() {
        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
        String login = pref.getString(KEY_LOGIN, "");

        if(login.equals(""))
            return false;

        return true;
    }

    private void startApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
