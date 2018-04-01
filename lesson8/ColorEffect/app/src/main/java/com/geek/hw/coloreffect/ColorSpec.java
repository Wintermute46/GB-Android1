package com.geek.hw.coloreffect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ColorSpec extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_color_spec, container, false);

        textView = view.findViewById(R.id.textView_result);
        if(savedInstanceState != null){
            textView.setText(savedInstanceState.getString(StartScreen.RESULT_TEXT));
            textView.setTextColor(savedInstanceState.getInt(StartScreen.RESULT_COLOR));
        } else {
            textView.setText(getArguments().getString(StartScreen.RESULT_TEXT));
            textView.setTextColor(getArguments().getInt(StartScreen.RESULT_COLOR));
        }



        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(StartScreen.RESULT_TEXT, textView.getText().toString());
        outState.putInt(StartScreen.RESULT_COLOR, textView.getCurrentTextColor());
    }
}
