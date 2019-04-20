package com.example.asus.vocabulary.Activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.asus.vocabulary.BottomSheetDialogForNothing;
import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.Random;

public class DiscoverWord extends AppCompatActivity implements BottomSheetDialogForNothing.BottomSheetListenerForNothig {

    DatabaseHelper myDbHelper;
    String TAG = DiscoverWord.this.getClass().getSimpleName() + "zzz";
    TextView textWord, textop1, textop2, textop3, textop4, textop5;

    LinearLayout doneLayout;
    Dialog myDialog;
    int session;
    CardView rootLayout;
    LinearLayout noDataLayout;
    //    TextView answerStatusText;
    String MY_PREFS_NAME = "MyPrefsFile";
    int discoverWordCount;
    private ProgressBar progressBar;
    int limit = 20;
    TextView indexText;

    boolean cantBeClick=false;
    int completeCount;
    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_word);
        //initialize
        doneLayout = (LinearLayout) findViewById(R.id.done_layout);
        noDataLayout = (LinearLayout) findViewById(R.id.no_data_layout);
        rootLayout = (CardView) findViewById(R.id.discover_root_layout);
        myDbHelper = new DatabaseHelper(this);
        textWord = (TextView) findViewById(R.id.discover_word);
        textop1 = (TextView) findViewById(R.id.discover_op1);
        textop2 = (TextView) findViewById(R.id.discover_op2);
        textop3 = (TextView) findViewById(R.id.discover_op3);
        textop4 = (TextView) findViewById(R.id.discover_op4);
        textop5 = (TextView) findViewById(R.id.discover_op5);
        indexText = (TextView) findViewById(R.id.index);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        int active = intent.getIntExtra("active", 0);

        //------------------
        String sql = "SELECT * from bigData;";
        int max = myDbHelper.getSize(sql) - 1;

        myDialog = new Dialog(this);

        sql = "SELECT * from bigData where mood=1 or mood=2 or mood=3;";
        session = myDbHelper.getSize(sql);
        Log.d(TAG, "onCreate: total word used " + session);
        session = session / limit;
        session += 1;
        Log.d(TAG, "onCreate: session " + session);
        myDialog = new Dialog(this);

        sql = "SELECT * from bigData where mood=1 or mood=3;";
        completeCount = myDbHelper.getSize(sql);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        discoverWordCount = prefs.getInt("disCount", 0);

        Cursor cursor1 = myDbHelper.getWord(new String[]{"id"}, "mood=? or mood=?", new String[]{String.valueOf(1), String.valueOf(3)});
        if (discoverWordCount == limit) {
            if (cursor1.getCount() == 0) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("disCount", 0);
                editor.apply();
                discoverWordCount = 0;
            }
        }


        if (discoverWordCount < limit && active == session) {
            getNewWord(max);
        } else {
            rootLayout.setVisibility(View.GONE);
//            noDataLayout.setVisibility(View.VISIBLE);

            BottomSheetDialogForNothing bottomSheetDialog = new BottomSheetDialogForNothing("Discovered All Words !!", "Go to learn segment to learn.", "Learn", "okay");
            bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");
//            noDataPopup(getWindow().getCurrentFocus());
        }


    }

    /**
     * @param max maximum range of random variable to get word
     * @return void
     */
    public void getNewWord(final int max) {
        progressBar.setProgress((100 / limit) * (completeCount + 1));
        indexText.setText((completeCount + 1) + "/" + limit);


        int min = 0;

        String[] columns = {"id", "engWord", "bangWord", "bangSyn", "engSyn", "example", "engPron", "bangPron", "type", "defination", "antonyms", "session", "mood"};

        // random value to get random word
        Random r = new Random();
        int ran = r.nextInt((max - min) + 1);
        Log.d(TAG, "getNewWord: random value " + ran);

        Log.d("qqqqq", "random value: " + ran);
        // database call to get the word
        Cursor cursor = myDbHelper.getWord(columns, "mood is null and id=?", new String[]{String.valueOf(ran)});


        // if it is not a new value
        while (cursor.getCount() == 0) {
            ran = r.nextInt((max - min) + 1);
            Log.d(TAG, "getNewWord: in while loop " + ran);
            cursor = myDbHelper.getWord(columns, "mood is null and id=?", new String[]{String.valueOf(ran)});
            Log.d("qqqqq", "loop");
        }
        Log.d(TAG, "getNewWord: word which is found ENG " + cursor.getString(0));
        Log.d(TAG, "getNewWord: word which is found BANG " + cursor.getString(1));
//        Log.d(TAG, "getNewWord: cursor size " + cursor.getCount());


        Word word = null;
        cursor.moveToFirst();
        Log.d(TAG, "getNewWord: cursor " + cursor.getString(2));
        if (cursor != null) {
            word = new Word(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
        }
        Log.d(TAG, "getNewWord: word info " + word.toString());
        // random value to determine correct option
        int selectOption = r.nextInt((4 - 0) + 1);
        Log.d(TAG, "getNewWord: select option " + selectOption);
        // set new word to header

        String w = cursor.getString(1);
        textWord.setText(w.substring(0, 1).toUpperCase() + w.substring(1));

        // set all the option randomly by the getOption function
        textop1.setText(getOption(ran, max));
        textop2.setText(getOption(ran, max));
        textop3.setText(getOption(ran, max));
        textop4.setText(getOption(ran, max));
        textop5.setText(getOption(ran, max));

        final Word finalWord = word;
        textop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!cantBeClick){
                    textop1.setClickable(false);
                    textop2.setClickable(false);
                    textop3.setClickable(false);
                    textop4.setClickable(false);
                    textop5.setClickable(false);
                    cantBeClick=true;
                    startAnimation(getWindow().getDecorView(), finalWord, max, textop1.getText().toString());
                }

            }
        });

        textop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cantBeClick){
                    textop1.setClickable(false);
                    textop2.setClickable(false);
                    textop3.setClickable(false);
                    textop4.setClickable(false);
                    textop5.setClickable(false);
                    cantBeClick=true;
                    startAnimation(getWindow().getDecorView(), finalWord, max, textop2.getText().toString());
                }
//                progressBar.setProgress((100/limit)*(completeCount+1));
//                indexText.setText((completeCount+1)+"/"+limit);
//                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop2.getText().toString());
            }
        });

        textop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cantBeClick){
                    textop1.setClickable(false);
                    textop2.setClickable(false);
                    textop3.setClickable(false);
                    textop4.setClickable(false);
                    textop5.setClickable(false);
                    cantBeClick=true;
                    startAnimation(getWindow().getDecorView(), finalWord, max, textop3.getText().toString());
                }
 //                progressBar.setProgress((100/limit)*(completeCount+1));
//                indexText.setText((completeCount+1)+"/"+limit);
//                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop3.getText().toString());
            }
        });

        textop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cantBeClick){
                    textop1.setClickable(false);
                    textop2.setClickable(false);
                    textop3.setClickable(false);
                    textop4.setClickable(false);
                    textop5.setClickable(false);
                    cantBeClick=true;
                    startAnimation(getWindow().getDecorView(), finalWord, max, textop4.getText().toString());
                }
 //                progressBar.setProgress((100/limit)*(completeCount+1));
//                indexText.setText((completeCount+1)+"/"+limit);
//                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop4.getText().toString());
            }
        });

        textop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cantBeClick){
                    textop1.setClickable(false);
                    textop2.setClickable(false);
                    textop3.setClickable(false);
                    textop4.setClickable(false);
                    textop5.setClickable(false);
                    cantBeClick=true;
                    startAnimation(getWindow().getDecorView(), finalWord, max, textop5.getText().toString());
                }
 //                progressBar.setProgress((100/limit)*(completeCount+1));
//                indexText.setText((completeCount+1)+"/"+limit);
//                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop5.getText().toString());
            }
        });


        // randomly set up the correct option
        switch (selectOption) {
            case 0:
                textop1.setText(cursor.getString(2));
                break;
            case 1:
                textop2.setText(cursor.getString(2));
                break;
            case 2:
                textop3.setText(cursor.getString(2));
                break;
            case 3:
                textop4.setText(cursor.getString(2));
                break;
            case 4:
                textop5.setText(cursor.getString(2));
                break;
        }


    }

    /**
     * @param answeredOpId which word is already found
     * @param max          maximum range to take a word
     * @return String meaning of a word
     */
    public String getOption(int answeredOpId, int max) {
        String[] columns = {"bangWord"};
        int min = 0;
        Random r = new Random();
        int ran = r.nextInt((max - min) + 1);

        Cursor cursor = myDbHelper.getWord(columns, "id!=? and id=?", new String[]{String.valueOf(answeredOpId), String.valueOf(ran)});
//        Log.d("ppppp","cursor: "+ String.valueOf(cursor));


        return cursor.getString(0);
    }


    @SuppressLint("ResourceAsColor")
    public void onButtonShowPopupWindowClick(View view, final Word word, final int max, String result) {


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        TextView wordText = (TextView) popupView.findViewById(R.id.word_text);
        TextView typeText = (TextView) popupView.findViewById(R.id.type);
        TextView bngWordText = (TextView) popupView.findViewById(R.id.bang_word_text);
        TextView bngPronText = (TextView) popupView.findViewById(R.id.bng_pron_txt);
        TextView synBangText = (TextView) popupView.findViewById(R.id.bang_syn_txt);
        TextView defText = (TextView) popupView.findViewById(R.id.defi_txt);
        TextView synEngText = (TextView) popupView.findViewById(R.id.eng_syn_txt);
        TextView antoText = (TextView) popupView.findViewById(R.id.eng_antonyms_txt);
        TextView exampleText = (TextView) popupView.findViewById(R.id.exanple_txt);
        TextView answerStatusText = (TextView) popupView.findViewById(R.id.answer_status);

        Log.d(TAG, "onButtonShowPopupWindowClick: "+result);
        Log.d(TAG, "onButtonShowPopupWindowClick: bn "+word.getBangWord());
        boolean flag = false;
        if (word.getBangWord().equals(result)) {
            answerStatusText.setBackgroundColor(Color.parseColor("#588F27"));
            answerStatusText.setText("Correct");
            flag = true;
        } else {
            answerStatusText.setBackgroundColor(Color.parseColor("#CD2C24"));
            answerStatusText.setText("Wrong");
//            answerStatusText.setBackgroundColor(R.color.wrong_answer);
        }

        Log.d(TAG, "onButtonShowPopupWindowClick: " + word);
        wordText.setText(word.getEngWord().substring(0, 1).toUpperCase() + word.getEngWord().substring(1));

//        if (word.getType() != null) {

        if (word.getType() != null) {
            typeText.setText(word.getType().toUpperCase());
        } else typeText.setText("NA");
//        }
//        else typeText.setText(word.getType());
        bngWordText.setText(word.getBangWord());
        bngPronText.setText(word.getBangPron());
        Log.d(TAG, "onButtonShowPopupWindowClick: bngSyn " + word.getBngSyn());

        synBangText.setText(word.getBngSyn());


        if (word.getDefinition() != null) {
            defText.setText(word.getDefinition().substring(0, 1).toUpperCase() + word.getDefinition().substring(1));
        } else defText.setText("NA");

        if (word.getEngSyn() != null) {

            try {
                String enSyn = "";
                String b[] = word.getEngSyn().split(",");
                for (int i = 0; i < b.length; i++) {
                    b[i] = b[i].substring(0, 1).toUpperCase() + b[i].substring(1);
                    enSyn += b[i] + "," + " ";
                }
                synEngText.setText(enSyn);
            } catch (Exception e) {
                synEngText.setText("NA");

            }
        } else synEngText.setText("NA");

//        synEngText.setText(word.getEngSyn());
        if (word.getAntonyms() != null) {

            antoText.setText(word.getAntonyms().substring(0, 1).toUpperCase() + word.getAntonyms().substring(1));
        } else antoText.setText("NA");
        exampleText.setText(word.getExample());

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });



//        doneLayout.setVisibility(View.VISIBLE);
        Button doneBtn = (Button) popupView.findViewById(R.id.doneBtn);
        final boolean finalFlag = flag;
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                completeCount++;
                discoverWordCount += 1;
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("disCount", discoverWordCount);
                editor.apply();


                ContentValues values = new ContentValues();
                if (finalFlag) {
                    values.put("mood", "3");
                } else {
                    values.put("mood", "1");
                }
                values.put("session", session);
                Log.d(TAG + "sss", "onClick: dis ssss: " + session);

                myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(word.getId())});

//                Cursor cursor = myDbHelper.getWord(new String[]{"id"}, "mood=?", new String[]{String.valueOf(1)});
                if (discoverWordCount < limit) {
                    getNewWord(max);
                } else {
//                    noDataPopup(getWindow().getDecorView());
                    rootLayout.setVisibility(View.GONE);
                    BottomSheetDialogForNothing bottomSheetDialog = new BottomSheetDialogForNothing("Discovered All Words !!", "Go to learn segment to learn.", "Learn", "okay");
                    bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");
//                    noDataLayout.setVisibility(View.VISIBLE);
                }

//                getNewWord(max);
                popupWindow.dismiss();


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), DisLvlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onButtonClick(String text) {
        if (text.equals("okay")) {
            Intent i = new Intent(getApplicationContext(), UltimateLearnActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (text.equals("dismissOkay")) {
            Intent i = new Intent(getApplicationContext(), DisLvlActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }


    public void startAnimation(View view, final Word word, final int max, final String result) {


        final boolean[] flag = {true};
        animationView.setVisibility(View.VISIBLE);
        if (result.equals(word.getBangWord())){

//            animationView.setLayoutParams(new RelativeLayout.LayoutParams(500, 500, 1f));
            animationView.setAnimation("cc.json");
        }else {
            animationView.setAnimation("ww.json");
        }

        animationView.playAnimation();

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (flag[0]){
                    textop1.setClickable(false);
                    textop2.setClickable(false);
                    textop3.setClickable(false);
                    textop4.setClickable(false);
                    textop5.setClickable(false);
                    flag[0] =false;
                    Log.d(TAG, "onAnimationEnd: " +"animation");
                    cantBeClick=false;
                    animationView.cancelAnimation();
                    animationView.clearAnimation();
                    animationView.setVisibility(View.GONE);
                    onButtonShowPopupWindowClick(getWindow().getDecorView(), word, max, result);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("fffff", "cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("fffff", "repeat");
            }
        });
    }



}
