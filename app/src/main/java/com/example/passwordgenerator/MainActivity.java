package com.example.passwordgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private PasswordCreator passwordCreator;
    public static boolean strongPassword = true;


    ClipboardManager clipboardManager;
    ClipData clipData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordCreator = new PasswordCreator();
        setContentView(R.layout.activity_main);
        //setPasswordLengthToTextView();
      //  setSeekBar();
        setOnClickButton();

        //Подключение toolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Подключение переключателя для изменения сложности пароля
      //  addSwitchForStrongPassword();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_help:
               Intent intent1 = new Intent(this,HelpActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_settings:
               Intent intent2 = new Intent(this,SettingsActivity.class);
                startActivity(intent2);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
//
//    //Подключение переключателя для изменения сложности пароля
//    private void addSwitchForStrongPassword(){
//        SwitchCompat switch1 = findViewById(R.id.switch1);
//
//        switch1.setOnCheckedChangeListener((buttonView, isChecked) ->{
//
//            if(isChecked){
//                strongPassword = true;
//
//            }else{
//                strongPassword = false;
//
//            }
//        });
//    }

//    private void setPasswordLengthToTextView() {
//        TextView textView =  findViewById(R.id.seekBarValue);
//        textView.setText(String.valueOf(passwordCreator.getPasswordLength()));
//    }

//    private void setSeekBar() {
//        SeekBar seekBar =  findViewById(R.id.seekBar);
//        TextView textView = findViewById(R.id.seekBarValue);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textView.setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//    }


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
      //  SeekBar seekBar = findViewById(R.id.seekBar);
      //  int passwordLength = seekBar.getProgress();

        if(!resourceName.equals("") && !key.equals("")) {
            generatedPassword = passwordCreator.createPassword(resourceName, key);
            TextView passwordField = findViewById(R.id.generated_password);
            passwordField.setText(generatedPassword);
            copyPasswordToClipboard(generatedPassword);
        }
    }

    private void copyPasswordToClipboard(String generatedPassword){
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipData = ClipData.newPlainText("text", generatedPassword);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), R.string.password_was_copied, Toast.LENGTH_LONG).show();
    }
}