package com.geek.hw.coloreffect;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResultScreen extends Fragment {

    public interface onPushBackButton {
        void goBackResult(boolean result);
    }

    private final static String COLORSPEC_FRAGMENT_TAG = "12c7fbb2-350d-11e8-b467-0ed5f89f718b";
    private final static String TAG = "ResultScreen";

    private EditText phoneNum;
    private boolean isShare = false;
    private Button shareButton, smsButton, backButton;
    onPushBackButton pushBackButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            pushBackButton = (onPushBackButton) context;
        } catch (Exception ex) {
            Log.e(TAG, context.toString() + getResources().getString(R.string.activity_implement));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_screen, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        ColorSpec colorSpec = (ColorSpec) fragmentManager.findFragmentByTag(COLORSPEC_FRAGMENT_TAG);
        if(colorSpec == null) {
            colorSpec = new ColorSpec();
            colorSpec.setArguments(getArguments());
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.color_res_container, colorSpec).commit();
        }

        shareButton = view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(onClickListener);
        smsButton = view.findViewById(R.id.sms_button);
        smsButton.setOnClickListener(onClickListener);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(onClickListener);
        phoneNum = view.findViewById(R.id.editText);
        phoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        return view;
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.share_button:
                    isShare = true;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, getArguments().getString(StartScreen.RESULT_TEXT));
                    Intent chIntent = Intent.createChooser(intent, getResources().getString(R.string.chooser_title));
                    startActivity(chIntent);
                    break;
                case R.id.sms_button:
                    isShare = true;
                    intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + phoneNum.getText().toString()));
                    intent.putExtra("sms_body", getArguments().getString(StartScreen.RESULT_TEXT));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.e(TAG, getResources().getString(R.string.no_act_toast));
                        Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.no_act_toast), Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
                case R.id.back_button:
                    pushBackButton.goBackResult(isShare);
                    getActivity().getSupportFragmentManager().popBackStack();
                    break;
            }
        }
    };

}
