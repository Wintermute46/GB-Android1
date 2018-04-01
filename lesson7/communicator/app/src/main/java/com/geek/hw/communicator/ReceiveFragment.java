package com.geek.hw.communicator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReceiveFragment extends Fragment {

    private String messageText;


    public ReceiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_receive, container, false);
        TextView messageView = view.findViewById(R.id.textView);
        messageView.setText(messageText);
        return view;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
