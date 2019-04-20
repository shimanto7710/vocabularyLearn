package com.example.asus.vocabulary.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.vocabulary.Model.MasteredWord;
import com.example.asus.vocabulary.Model.SessionModel;
import com.example.asus.vocabulary.R;

import java.util.ArrayList;


/**
 * Created by Jaison on 08/10/16.
 */

public class MasterWordAdapter extends RecyclerView.Adapter<MasterWordAdapter.MyViewHolder> {

    public ArrayList<MasteredWord> masteredWords=new ArrayList<>();
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sessionNumber, startDate,endDate;
        ImageView thumbnailImage;
        public CardView ll_listitem;

        public MyViewHolder(View view) {
            super(view);
            sessionNumber = (TextView) view.findViewById(R.id.multi_selected_session);
//            startDate = (TextView) view.findViewById(R.id.multi_selected_start_date);
            endDate = (TextView) view.findViewById(R.id.multi_selected_end_date_);
//            ll_listitem=(CardView) view.findViewById(R.id.ll_listitem);

        }
    }


    public MasterWordAdapter(Context context, ArrayList<MasteredWord> masteredWords) {
        this.mContext=context;
        this.masteredWords = masteredWords;
//        this.selected_usersList = selectedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multi_selected_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MasteredWord masteredWord = masteredWords.get(position);
        String aa=masteredWord.getEngWord().substring(0, 1).toUpperCase() + masteredWord.getEngWord().substring(1);
        holder.sessionNumber.setText(String.valueOf(aa));
//        holder.startDate.setText("daaaa");
        holder.endDate.setText(masteredWord.getBangWord());

//        holder.thumbnailImage.setImageBitmap(movie.getBitmap());




    }

    @Override
    public int getItemCount() {
        return masteredWords.size();
    }
}

