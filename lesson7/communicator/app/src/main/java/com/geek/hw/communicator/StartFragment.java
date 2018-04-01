package com.geek.hw.communicator;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StartFragment extends Fragment {

    private SharedPreferences appSettings;
    private static final String SETTINGS_STORAGE = "RadioSettings";
    private static final String SETTINGS_STORAGE_CHECKED_ID = "CheckedId";
    private static final String TAG = "StartFragment";

    private EditText editText;
    private RadioGroup radios;
    private EditText editPhone;


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_start, container, false);

        appSettings = getActivity().getSharedPreferences(SETTINGS_STORAGE, Context.MODE_PRIVATE);

        Button button = view.findViewById(R.id.button_send);
        editText = view.findViewById(R.id.edittext_message);
        editPhone = view.findViewById(R.id.editPhone);
        editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        radios = view.findViewById(R.id.radioGroup);

        button.setOnClickListener(onClickListener);
        radios.setOnCheckedChangeListener(onCheckedChangeListener);

        if (savedInstanceState == null) {
            RadioButton rb = view.findViewById(appSettings.getInt(SETTINGS_STORAGE_CHECKED_ID, R.id.radioButton1));
            rb.setChecked(true);
        }

        return view;
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String messageText = editText.getText().toString();
            Intent intent;

            switch (view.getId()) {
                case R.id.button_send:
                    switch (radios.getCheckedRadioButtonId()) {
                        case R.id.radioButton1:
                            ReceiveFragment receiveFragment = new ReceiveFragment();
                            receiveFragment.setMessageText(messageText);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, receiveFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;
                        case R.id.radioButton2:
                            intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto:" + editPhone.getText().toString()));
                            intent.putExtra("sms_body", editText.getText().toString());
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Log.e(TAG, getResources().getString(R.string.no_act_toast));
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.no_act_toast), Toast.LENGTH_LONG);
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

}
