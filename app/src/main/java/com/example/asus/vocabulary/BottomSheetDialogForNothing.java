package com.example.asus.vocabulary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class BottomSheetDialogForNothing extends BottomSheetDialogFragment {

    BottomSheetListenerForNothig mListener;

     String heading,comment,buttonString,msg;

    @SuppressLint("ValidFragment")
    public BottomSheetDialogForNothing(String heading,String comment,String buttonString,String msg) {

        this.heading = heading;
        this.comment = comment;
        this.buttonString = buttonString;
        this.msg = msg;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout_for_nothing, container, false);


        Log.d("aaa", "onButtonClick: "+msg);
        Button btn = (Button) view.findViewById(R.id.okay);


        TextView commentText = (TextView) view.findViewById(R.id.comment_text);
        TextView successText = (TextView) view.findViewById(R.id.status_text);

        successText.setText(heading);
        commentText.setText(comment);
        btn.setText(buttonString);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msg.equals("practice")){

                    mListener.onButtonClick("practice");
                }
                else if (msg.equals("okay")){
                    mListener.onButtonClick("okay");
                }else if (msg.equals("master")){
                    mListener.onButtonClick("master");

                }else if (msg.equals("ultimate")){
                    mListener.onButtonClick("ultimate");

                }else if (msg.equals("discover")){
//                    Log.d("aaa", "onButtonClick: ");
                    mListener.onButtonClick("discover");

                }
                dismiss();
            }
        });


        return view;
    }


    public interface BottomSheetListenerForNothig {
        void onButtonClick(String text);
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (msg.equals("okay")){
        mListener.onButtonClick("dismissOkay");
        }else if (msg.equals("master")){
            mListener.onButtonClick("dismissMaster");

        }
        else if (msg.equals("ultimate")){

            mListener.onButtonClick("dismissMaster");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListenerForNothig) context;
        } catch (Exception e) {

        }
    }
}
