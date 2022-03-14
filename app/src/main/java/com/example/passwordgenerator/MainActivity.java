package com.example.passwordgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private PasswordCreator passwordCreator;

    public static final String APP_PREFERENCES = "mySettings";
    public static final String PASSWORD_LENGTH = "password_length";
    public static final String PASSWORD_HARD = "password_hard";
    private static SharedPreferences mySharedPreference = null;

    public static SharedPreferences getMySharedPreference() {
        return mySharedPreference;
    }

    ClipboardManager clipboardManager;
    ClipData clipData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        passwordCreator = new PasswordCreator();
        setContentView(R.layout.activity_main);

        setOnClickButton();

        loadSettings();
        //Подключение toolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addSwitchForStrongPassword();

    }


    /**
     * Загрузка настроек
     * Загружает длину пароля и положение выключателя сложности
     */
    private void loadSettings() {
        if (mySharedPreference == null) {
            mySharedPreference = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        }
        if (mySharedPreference.contains(PASSWORD_LENGTH)) {
            PasswordCreator.passwordLength = mySharedPreference.getInt(PASSWORD_LENGTH, 0);
        }
        if (mySharedPreference.contains(PASSWORD_HARD)) {
            PasswordCreator.strongPassword = mySharedPreference.getBoolean(PASSWORD_HARD, true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_help:
                Intent intent1 = new Intent(this, HelpActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.translate_down, R.anim.translate_downer);
                return true;
            case R.id.action_settings:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.translate_down, R.anim.translate_downer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }






    private void addSwitchForStrongPassword() {
        SwitchCompat switch1 = findViewById(R.id.switchForTypeOfModelGeneration);
        switch1.setChecked(!PasswordCreator.oldGeneration);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PasswordCreator.oldGeneration = !isChecked;
        });
    }

    private void setOnClickButton() {
        Button generateButton = findViewById(R.id.generate_button);
        generateButton.setOnClickListener(o -> createPassword());
    }

    private void createPassword() {
        String generatedPassword = "";
        EditText resourceNameEdit = findViewById(R.id.get_site_name);
        EditText keyEdit = findViewById(R.id.get_key_word);
        String resourceName = resourceNameEdit.getText().toString();
        String key = keyEdit.getText().toString();
        TextView passwordField = findViewById(R.id.generated_password);

        if (!resourceName.equals("") && !key.equals("")) {
            generatedPassword = passwordCreator.createPassword(resourceName, key);
            passwordField.setText(generatedPassword);
            copyPasswordToClipboard(generatedPassword);
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                resourceNameEdit.setText("");
                keyEdit.setText("");
                passwordField.setText("");
            }
        }, 5000);


    }

    private void copyPasswordToClipboard(String generatedPassword) {
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipData = ClipData.newPlainText("text", generatedPassword);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), R.string.password_was_copied, Toast.LENGTH_LONG).show();
    }
}
/*2-rnssubuEJ*/