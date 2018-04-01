package com.geek.hw.coloreffect;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ResultScreen.onPushBackButton {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            StartScreen startScreen = new StartScreen();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frag_container, startScreen);
            transaction.commit();
        }

    }

    @Override
    public void goBackResult(boolean result) {
        String message;
        if (result)
            message = getResources().getText(R.string.back_text_true).toString();
        else message = getResources().getText(R.string.back_text_false).toString();
        Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
