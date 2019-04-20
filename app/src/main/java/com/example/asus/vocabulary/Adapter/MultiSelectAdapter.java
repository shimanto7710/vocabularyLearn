package com.example.asus.vocabulary.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.vocabulary.Model.SessionModel;
import com.example.asus.vocabulary.R;

import java.util.ArrayList;


/**
 * Created by Jaison on 08/10/16.
 */

public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.MyViewHolder> {

    public ArrayList<SessionModel> sessionList=new ArrayList<>();
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


    public MultiSelectAdapter(Context context, ArrayList<SessionModel> sessionList) {
        this.mContext=context;
        this.sessionList = sessionList;
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
        SessionModel sessionModel = sessionList.get(position);
        holder.sessionNumber.setText(String.valueOf("Level - "+sessionModel.getSessionNumber()));
//        holder.startDate.setText("daaaa");
        holder.endDate.setText("Completed");

//        holder.thumbnailImage.setImageBitmap(movie.getBitmap());




    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }
}

