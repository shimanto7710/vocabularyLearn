package com.example.asus.vocabulary.Adapter;

import android.content.Context;
import android.graphics.Color;
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

public class LvlAdapter extends RecyclerView.Adapter<LvlAdapter.MyViewHolder> {

    public ArrayList<SessionModel> sessionList=new ArrayList<>();
    Context mContext;
    int completeCount;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sessionNumber, startDate,endDate;
        ImageView thumbnailImage;
        public CardView ll_listitem;


        public MyViewHolder(View view) {
            super(view);
            sessionNumber = (TextView) view.findViewById(R.id.multi_selected_session);
            startDate = (TextView) view.findViewById(R.id.multi_selected_start_date);
            endDate = (TextView) view.findViewById(R.id.end);
            ll_listitem=(CardView) view.findViewById(R.id.ll_listitem);

        }
    }


    public LvlAdapter(Context context, ArrayList<SessionModel> sessionList,int completeCount) {
        this.mContext=context;
        this.sessionList = sessionList;
        this.completeCount = completeCount;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lvl_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SessionModel sessionModel = sessionList.get(position);
        if ((position+1)==sessionModel.getRunningLvl()){
            holder.ll_listitem.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.startDate.setText("Running Lvl");
            holder.endDate.setText("Completed "+completeCount+" Out of 20");

            holder.startDate.setTextColor(Color.parseColor("#D81B60"));
            holder.endDate.setTextColor(Color.parseColor("#D81B60"));
            holder.sessionNumber.setTextColor(Color.parseColor("#D81B60"));

        }else if (position<sessionModel.getRunningLvl()){
            holder.ll_listitem.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.startDate.setText("Completed");
            holder.endDate.setText("-");

            holder.startDate.setTextColor(Color.parseColor("#00574B"));
            holder.endDate.setTextColor(Color.parseColor("#00574B"));
            holder.sessionNumber.setTextColor(Color.parseColor("#00574B"));
        }else {
            holder.startDate.setText("Need to Unlock");
            holder.ll_listitem.setCardBackgroundColor(Color.parseColor("#FFB4B4B4"));
            holder.endDate.setText("-");

            holder.startDate.setTextColor(Color.parseColor("#FF494848"));
            holder.endDate.setTextColor(Color.parseColor("#FF494848"));
            holder.sessionNumber.setTextColor(Color.parseColor("#FF494848"));
        }
        holder.sessionNumber.setText(String.valueOf("Level - "+sessionModel.getSessionNumber()));


//        holder.thumbnailImage.setImageBitmap(movie.getBitmap());




    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }
}

