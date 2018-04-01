package com.geek.hw.lesson6app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView_result);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linLayout = new LinearLayoutManager(MainActivity.this);
        linLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linLayout);
        recyclerView.setAdapter(new MyAdapter());
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView listItem;

        public MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.res_view_item, parent, false));
            itemView.setOnClickListener(this);
            listItem = itemView.findViewById(R.id.recyclerView_item);
        }

        void bind(int pos) {
            String[] list = getResources().getStringArray(R.array.color_selection);
            listItem.setText(list[pos]);
            switch (pos) {
                case 0:
                    listItem.setTextColor(getResources().getColor(R.color.resColorRED));
                    break;
                case 1:
                    listItem.setTextColor(getResources().getColor(R.color.resColorORANGE));
                    break;
                case 2:
                    listItem.setTextColor(getResources().getColor(R.color.resColorYELLOW));
                    break;
                case 3:
                    listItem.setTextColor(getResources().getColor(R.color.resColorGREEN));
                    break;
                case 4:
                    listItem.setTextColor(getResources().getColor(R.color.resColorCYAN));
                    break;
                case 5:
                    listItem.setTextColor(getResources().getColor(R.color.resColorBLUE));
                    break;
                case 6:
                    listItem.setTextColor(getResources().getColor(R.color.resColorPURPLE));
                    break;
                default:
                    return;
            }
        }

        @Override
        public void onClick(View view) {
            String result = getResources().getStringArray(R.array.effect)[this.getLayoutPosition()];
            Intent intent = new Intent(MainActivity.this, ReceiveMessageActivity.class);
            intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE_RESULT, result);
            intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE_COLOR, this.getLayoutPosition());
            startActivityForResult(intent, 1);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater =LayoutInflater.from(getApplicationContext());
            return new MyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return getResources().getStringArray(R.array.color_selection).length;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null) {
                String message;
                if (data.getBooleanExtra(Intent.EXTRA_TEXT, false))
                    message = getResources().getText(R.string.back_text_true).toString();
                else message = getResources().getText(R.string.back_text_false).toString();
                Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}


