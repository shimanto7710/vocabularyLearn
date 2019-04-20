package com.example.asus.vocabulary.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MasteredWordListActivity extends AppCompatActivity {
    DatabaseHelper myDbHelper;
    String TAG = MasteredWordListActivity.this.getClass().getSimpleName() + "masterActivity";
    TextView textWord, textop1, textop2, textop3, textop4,textop5;
    int counter;
    ArrayList<Word> wordList;
    int session;
    int limit=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mastered_word_list);

        Intent intent = getIntent();
        session = intent.getIntExtra("sessionNumber", 0);
        Log.d(TAG, "onCreate: session " + session);

        myDbHelper = new DatabaseHelper(this);
        textWord = (TextView) findViewById(R.id.discover_word);
        textop1 = (TextView) findViewById(R.id.discover_op1);
        textop2 = (TextView) findViewById(R.id.discover_op2);
        textop3 = (TextView) findViewById(R.id.discover_op3);
        textop4 = (TextView) findViewById(R.id.discover_op4);
        textop5 = (TextView) findViewById(R.id.discover_op5);

        String[] columns = {"id", "engWord", "bangWord", "bangSyn", "engSyn", "example", "engPron", "bangPron", "type", "defination", "antonyms", "session", "mood"};


        Log.d(TAG+"sss", "onCreate: sssv: "+session);
        // database call to get the word
        Cursor cursor = myDbHelper.getWord(columns, "session=? and mood=?", new String[]{String.valueOf(session), String.valueOf(2)});
        Log.d(TAG, "onCreate: cursor Size: " + cursor.getCount());

        Word word = null;
        cursor.moveToFirst();

        wordList = new ArrayList<>();
        // if it is not a new value
        while (!cursor.isAfterLast()) {
            word = new Word(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
            wordList.add(word);
            cursor.moveToNext();
        }

        cursor.close();

        Log.d(TAG, "onCreate: word size " + wordList.size());

        String sql = "SELECT * from bigData;";
        int max = myDbHelper.getSize(sql)-1;


        Collections.shuffle(wordList);
        counter = 0;
        Log.d(TAG, "onCreate: wordList Size " + wordList.size());
        if (wordList.size() != 0) {
            getNewWord(wordList.get(counter), max);
        }

    }

    public void getNewWord(Word word, final int max) {


        Random r = new Random();
        // random value to determine correct option
        int selectOption = r.nextInt((4 - 0) + 1);
        Log.d(TAG, "getNewWord: select option " + selectOption);
        // set new word to header

        textWord.setText(word.getEngWord().substring(0, 1).toUpperCase() + word.getEngWord().substring(1));

        int ran = r.nextInt((max - 0) + 1);

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
                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop1.getText().toString());
            }
        });
        textop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop2.getText().toString());
            }
        });
        textop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop3.getText().toString());
            }
        });
        textop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop4.getText().toString());
            }
        });
        textop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop5.getText().toString());
            }
        });

        // randomly set up the correct option
        switch (selectOption) {
            case 0:
                textop1.setText(word.getBangWord());
                break;
            case 1:
                textop2.setText(word.getBangWord());
                break;
            case 2:
                textop3.setText(word.getBangWord());
                break;
            case 3:
                textop4.setText(word.getBangWord());
                break;
            case 4:
                textop4.setText(word.getBangWord());
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
        return cursor.getString(0);
    }


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

        if (word.getBangWord().equals(result)) {
            answerStatusText.setBackgroundColor(Color.parseColor("#A5D6A7"));
            answerStatusText.setText("Correct");
        } else {
            answerStatusText.setBackgroundColor(Color.parseColor("#EF9A9A"));
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
            }catch (Exception e){
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
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (word.getMood() == 1) {
//                    wordList.get(counter).setMood(3);
//                    ContentValues values = new ContentValues();
//                    values.put("mood", "3");
//                    values.put("session", session);
//                    myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(word.getId())});
//                } else if (word.getMood() == 3) {
//                    wordList.get(counter).setMood(2);
//                    ContentValues values = new ContentValues();
//                    values.put("mood", "2");
//                    values.put("session", session);
//                    myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(word.getId())});
//                }

                counter++;
                if (counter<wordList.size()){
                    getNewWord(wordList.get(counter),max);
                }else {
                    counter=0;
                    Collections.shuffle(wordList);
                    getNewWord(wordList.get(counter),max);
                }

                popupWindow.dismiss();

            }
        });
    }


}
