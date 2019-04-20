package com.example.asus.vocabulary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class BottomSheetDialog extends BottomSheetDialogFragment {

    BottomSheetListener mListener;

    int score, total, mood;

    @SuppressLint("ValidFragment")
    public BottomSheetDialog(int score, int total, int mood) {

        this.score = score;
        this.total = total;
        this.mood = mood;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);


        Button learnBtn = (Button) view.findViewById(R.id.learnbtn);
        Button homeBtn = (Button) view.findViewById(R.id.homebtn);
        Button nextLvlBtn = (Button) view.findViewById(R.id.extlvlbtn);
        TextView scoreTxt = (TextView) view.findViewById(R.id.score_text);
        TextView totalTxt = (TextView) view.findViewById(R.id.total_score_text);
        TextView percentScoreText = (TextView) view.findViewById(R.id.percentScoreTxt);
        TextView commentText = (TextView) view.findViewById(R.id.comment_text);
        TextView commentText2 = (TextView) view.findViewById(R.id.comment_text2);
        TextView successText = (TextView) view.findViewById(R.id.status_text);

//        Log.d("sheet",score+" / "+total);
//        Log.d("sheet",session+" / "+session);

        learnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick("learn");
                dismiss();
            }
        });
        nextLvlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick("next");
                dismiss();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClick("home");
                dismiss();
            }
        });


        scoreTxt.setText(String.valueOf(score));
        totalTxt.setText(String.valueOf(total));
        int p = (100 / total) * score;
        percentScoreText.setText(String.valueOf(p));
        String ccc = "";
        String toDo = "";
        String success = "";

        if (p >= 95) {
            ccc = "excellent !!";
            toDo = "You are an extra ordinary learner.";
            success = "Success";
        } else if (p >= 90) {
            ccc = "Very Good !!";
            toDo = "You are a pro learner try the next one.";
            success = "Success";
        } else if (p >= 85) {
            ccc = "Good !!";
            toDo = "You are good to go to the next lvl.";
            success = "Success";
        } else if (p >= 80) {
            ccc = "Fair !!";
            toDo = "Be careful for the next time.";
            success = "Success";
        } else if (p > 70) {
            ccc = "Very Close !!";
            toDo = "Try again surly you can do it next time";
            success = "Failed";

        } else if (p > 60) {
            ccc = "Not Good !!";
            toDo = "Need to concentrate more";
            success = "Failed";
        } else if (p > 50) {
            ccc = "Poor !!";
            toDo = "Try harder then do it again";
            success = "Failed";
        } else if (p > 40) {
            ccc = "Bellow Average !!";
            toDo = "You did not learn well ! learn then try it";
            success = "Failed";
        } else if (p > 30) {
            ccc = "Very Poor !!";
            toDo = "Learn First then do it";
            success = "Failed";
        } else {
            ccc = "No Comment !!";
            toDo = "That's not fair !! You are doing for time pass";
            success = "Failed";
        }


        if (mood == 3) {
            if (p >= 80) {

                learnBtn.setVisibility(View.GONE);
                nextLvlBtn.setVisibility(View.VISIBLE);

            }
        }
        else if (mood==2){
            learnBtn.setVisibility(View.GONE);
            homeBtn.setVisibility(View.VISIBLE);
        }

        successText.setText(String.valueOf(success));
        commentText.setText(String.valueOf(ccc));
        commentText2.setText(String.valueOf(toDo));

        return view;
    }


    public interface BottomSheetListener {
        void onButtonClick(String text);
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mListener.onButtonClick("dismiss");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (Exception e) {

        }
    }
}
