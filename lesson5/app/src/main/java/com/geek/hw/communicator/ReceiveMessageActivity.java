package com.geek.hw.communicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveMessageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        // Получаем Intent
        Intent intent = getIntent();

        // Обязательно проверяем на null
        if (intent != null) {
            // Если все в порядке, достаем по ключу EXTRA_MESSAGE наше сообщение
            String messageText = intent.getStringExtra(Intent.EXTRA_TEXT);

            // Находим TextView
            TextView messageView = findViewById(R.id.textView);
            // Задаем текст
            messageView.setText(messageText);
        }

        // для onActivityResult
        setResult(RESULT_OK, null);
    }
}
