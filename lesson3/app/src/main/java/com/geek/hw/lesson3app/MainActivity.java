package com.geek.hw.lesson3app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences appSettings;
    private final String SETTINGS_STORAGE = "ColorSettings";
    private final String SETTINGS_STORAGE_SPINNER_ID = "SpinnerId";

    private Spinner spinner;
    private Button button;
    private TextView textView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getBooleanExtra(Intent.EXTRA_TEXT, false))
                textView.setText(getResources().getText(R.string.back_text_true));
            else textView.setText(getResources().getText(R.string.back_text_false));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSettings = getSharedPreferences(SETTINGS_STORAGE, Context.MODE_PRIVATE);

        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView_result);

        button.setOnClickListener(onClickListener);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                appSettings.edit().putInt(SETTINGS_STORAGE_SPINNER_ID, pos).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinner.setSelection(appSettings.getInt(SETTINGS_STORAGE_SPINNER_ID, 0));
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String result = ColorSpec.getEffect(MainActivity.this, spinner.getSelectedItemPosition());

            Intent intent = new Intent(MainActivity.this, ReceiveMessageActivity.class);
            intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE_RESULT, result);
            intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE_COLOR, spinner.getSelectedItemPosition());
//            startActivity(intent);
            startActivityForResult(intent, 1);
        }
    };

}