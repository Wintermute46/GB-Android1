package com.geek.hw.lesson6app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveMessageActivity extends AppCompatActivity {

    private TextView textView;
    private EditText phoneNum;
    private Button share_button, sms_button, back_button;
    private boolean isShare = false;
    private final String TAG = "ReceiveMessageActivity";

    public static final String EXTRA_MESSAGE_RESULT = "resColor";
    public static final String EXTRA_MESSAGE_COLOR = "colorId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_receive_message);

        textView = findViewById(R.id.textView_result);
        share_button = findViewById(R.id.share_button);
        share_button.setOnClickListener(onClickListener);
        sms_button = findViewById(R.id.sms_button);
        sms_button.setOnClickListener(onClickListener);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(onClickListener);
        phoneNum = findViewById(R.id.editText);
        phoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        Intent intent = getIntent();
        if (intent != null) {
            textView.setText(intent.getStringExtra(EXTRA_MESSAGE_RESULT));
            switch (intent.getIntExtra(EXTRA_MESSAGE_COLOR, -1)) {
                case 0:
                    textView.setTextColor(getResources().getColor(R.color.resColorRED));
                    break;
                case 1:
                    textView.setTextColor(getResources().getColor(R.color.resColorORANGE));
                    break;
                case 2:
                    textView.setTextColor(getResources().getColor(R.color.resColorYELLOW));
                    break;
                case 3:
                    textView.setTextColor(getResources().getColor(R.color.resColorGREEN));
                    break;
                case 4:
                    textView.setTextColor(getResources().getColor(R.color.resColorCYAN));
                    break;
                case 5:
                    textView.setTextColor(getResources().getColor(R.color.resColorBLUE));
                    break;
                case 6:
                    textView.setTextColor(getResources().getColor(R.color.resColorPURPLE));
                    break;
                default:
                    return;
            }
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.share_button:
                    isShare = true;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
                    Intent chIntent = Intent.createChooser(intent, getResources().getString(R.string.chooser_title));
                    startActivity(chIntent);
                    break;
                case R.id.sms_button:
                    isShare = true;
                    intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + phoneNum.getText().toString()));
                    intent.putExtra("sms_body", textView.getText().toString());
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.e(TAG, getResources().getString(R.string.no_act_toast));
                        Toast toast = Toast.makeText(ReceiveMessageActivity.this, getResources().getString(R.string.no_act_toast), Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
                case R.id.back_button:
                    intent = new Intent();
                    intent.putExtra(Intent.EXTRA_TEXT, isShare);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    };
}
