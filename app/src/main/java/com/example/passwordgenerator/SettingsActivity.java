package com.example.passwordgenerator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    //  private PasswordCreator passwordCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  passwordCreator = new PasswordCreator();
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setSeekBar();
        setPasswordLengthToTextView();
        addSwitchForStrongPassword();
    }


    //Подключение переключателя для изменения сложности пароля
    private void addSwitchForStrongPassword() {
        SwitchCompat switch1 = findViewById(R.id.switch1);
switch1.setChecked(PasswordCreator.strongPassword);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                PasswordCreator.strongPassword = true;
            } else {
                PasswordCreator.strongPassword = false;
            }
        });
    }


    private void setPasswordLengthToTextView() {
        TextView textView = findViewById(R.id.seekBarValue);
        textView.setText(String.valueOf(PasswordCreator.passwordLength));
    }


    private void setSeekBar() {
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView textView = findViewById(R.id.seekBarValue);
        seekBar.setProgress(PasswordCreator.passwordLength);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
                PasswordCreator.passwordLength = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}