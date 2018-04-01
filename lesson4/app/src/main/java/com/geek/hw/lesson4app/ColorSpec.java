package com.geek.hw.lesson4app;

import android.content.Context;


public class ColorSpec {

    static String getEffect(Context context, int position) {
        String[] strings = context.getResources().getStringArray(R.array.effect);
        String effect = strings[position];
        return effect;
    }

}
