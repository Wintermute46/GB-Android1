package com.geek.hw.lesson2app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences appSettings;
    private final String SETTINGS_STORAGE = "ColorSettings";
    private final String SETTINGS_STORAGE_SPINNER_ID = "SpinnerId";

    private TextView textView;
    private Spinner spinner;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSettings = getSharedPreferences(SETTINGS_STORAGE, Context.MODE_PRIVATE);

        textView = (TextView) findViewById(R.id.textView_result);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(onClickListener);

        restoreSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreSettings();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSettings();
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String result = ColorSpec.getEffect(MainActivity.this, spinner.getSelectedItemPosition());

            switch ((int)spinner.getSelectedItemId()) {
                case 0: textView.setTextColor(getResources().getColor(R.color.resColorRED)); break;
                case 1: textView.setTextColor(getResources().getColor(R.color.resColorORANGE)); break;
                case 2: textView.setTextColor(getResources().getColor(R.color.resColorYELLOW)); break;
                case 3: textView.setTextColor(getResources().getColor(R.color.resColorGREEN)); break;
                case 4: textView.setTextColor(getResources().getColor(R.color.resColorCYAN)); break;
                case 5: textView.setTextColor(getResources().getColor(R.color.resColorBLUE)); break;
                case 6: textView.setTextColor(getResources().getColor(R.color.resColorPURPLE)); break;
            }

            textView.setText(result);
        }
    };

    private void saveSettings() {
        SharedPreferences.Editor editor = appSettings.edit();
        editor.putLong(SETTINGS_STORAGE_SPINNER_ID, spinner.getSelectedItemId());
        editor.apply();
    }

    private void restoreSettings() {
        if (appSettings.contains(SETTINGS_STORAGE_SPINNER_ID)) {
            spinner.setSelection((int)appSettings.getLong(SETTINGS_STORAGE_SPINNER_ID, 0));
            button.callOnClick();
        }
    }
}
