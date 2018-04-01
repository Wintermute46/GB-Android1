package com.geek.hw.coloreffect;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StartScreen extends Fragment {

    private RecyclerView recyclerView;
    final static String RESULT_TEXT = "result";
    final static String RESULT_COLOR = "color";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_start_screen, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linLayout = new LinearLayoutManager(getActivity());
        linLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linLayout);
        recyclerView.setAdapter(new MyAdapter());


        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
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

        class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView listItem;

            public MyHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.res_view_item, parent, false));
                itemView.setOnClickListener(this);
                listItem = itemView.findViewById(R.id.recyclerView_item);
            }

            void bind(int pos) {
                String[] list = getResources().getStringArray(R.array.color_selection);
                TypedArray colors = getResources().obtainTypedArray(R.array.list_colors);
                listItem.setText(list[pos]);
                listItem.setTextColor(colors.getColor(pos, 0));
                colors.recycle();
            }

            @Override
            public void onClick(View view) {
                    int color = this.listItem.getCurrentTextColor();
                    String result = getResources().getStringArray(R.array.effect)[this.getLayoutPosition()];
                    ResultScreen resultScreen = new ResultScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString(RESULT_TEXT, result);
                    bundle.putInt(RESULT_COLOR, color);
                    resultScreen.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_container, resultScreen).addToBackStack(null).commit();
            }
        }


    }

}
