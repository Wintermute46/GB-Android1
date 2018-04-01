package com.geek.hw.communicator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateMessageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private SharedPreferences appSettings;
    private static final String SETTINGS_STORAGE = "RadioSettings";
    private static final String SETTINGS_STORAGE_CHECKED_ID = "CheckedId";
    private static final String TAG = "CreateMessageActivity";

    private EditText editText;
    private RadioGroup radios;
    private EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        appSettings = getSharedPreferences(SETTINGS_STORAGE, Context.MODE_PRIVATE);

        Button button = findViewById(R.id.button_send);
        editText = findViewById(R.id.edittext_message);
        editPhone = findViewById(R.id.editPhone);
        editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        radios = findViewById(R.id.radioGroup);

        button.setOnClickListener(onClickListener);
        radios.setOnCheckedChangeListener(onCheckedChangeListener);

        if (savedInstanceState == null) {
            RadioButton rb = findViewById(appSettings.getInt(SETTINGS_STORAGE_CHECKED_ID, R.id.radioButton1));
            rb.setChecked(true);
        }

    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String messageText = editText.getText().toString();
            Intent intent = new Intent(CreateMessageActivity.this,
                    ReceiveMessageActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, messageText);

            switch (view.getId()) {
                case R.id.button_send:
                    switch (radios.getCheckedRadioButtonId()) {
                        case R.id.radioButton1:
                            startActivityForResult(intent, REQUEST_CODE);
                            break;
                        case R.id.radioButton2:
                            intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto:" + editPhone.getText().toString()));
                            intent.putExtra("sms_body", editText.getText().toString());
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Log.e(TAG, getResources().getString(R.string.no_act_toast));
                                Toast toast = Toast.makeText(CreateMessageActivity.this,
                                        getResources().getString(R.string.no_act_toast), Toast.LENGTH_LONG);
                                toast.show();
                            }
                            break;
                        case R.id.radioButton3:
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                            Intent chIntent = Intent.createChooser(intent, getResources().getString(R.string.chooser_title));
                            startActivity(chIntent);
                            break;
                        default:
                            return;
                    }
                    break;
                default:
                    return;
            }
        }
    };

    private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener;

    {
        onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                appSettings.edit().putInt(SETTINGS_STORAGE_CHECKED_ID, i).apply();
                int wrapContent = RadioGroup.LayoutParams.WRAP_CONTENT;
                RadioGroup.LayoutParams lPars = new RadioGroup.LayoutParams(wrapContent, wrapContent);
                switch (i) {
                    case R.id.radioButton2:
                        editPhone.setLayoutParams(lPars);
                        editPhone.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioButton1:
                    case R.id.radioButton3:
                        editPhone.setVisibility(View.INVISIBLE);
                        editPhone.setText("");
                        lPars.height = 0;
                        editPhone.setLayoutParams(lPars);
                        break;
                }

            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            RadioButton rb = findViewById(R.id.radioButton2);
            rb.setChecked(true);
        }
    }
}
