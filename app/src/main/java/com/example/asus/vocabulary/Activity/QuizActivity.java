package com.example.asus.vocabulary.Activity;

import android.animation.Animator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.asus.vocabulary.BottomSheetDialog;
import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {
    TextView textWord, textop1, textop2, textop3, textop4, textop5;
    int session;
    DatabaseHelper myDbHelper;
    String TAG = QuizActivity.this.getClass().getSimpleName() + "zzz";
    ArrayList<Word> wordList;
    int score;
    int count = 0;
    boolean dontClick = true;
    LottieAnimationView animationView;
    TextView indexText;
    private ProgressBar progressBar;
    int waitFlag = 0;
    private int mProgressStatus = 0;
    private Handler mHandler;
    private ProgressBar mProgressBar;

    int limit = 20, mood;
    boolean finishFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        myDbHelper = new DatabaseHelper(this);
        textWord = (TextView) findViewById(R.id.discover_word);
        textop1 = (TextView) findViewById(R.id.discover_op1);
        textop2 = (TextView) findViewById(R.id.discover_op2);
        textop3 = (TextView) findViewById(R.id.discover_op3);
        textop4 = (TextView) findViewById(R.id.discover_op4);
        textop5 = (TextView) findViewById(R.id.discover_op5);
        indexText = (TextView) findViewById(R.id.index);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        score = 0;
        Intent intent = getIntent();
        session = intent.getIntExtra("sessionNumber", 0);
        mood = intent.getIntExtra("mood", 0);


        Log.d(TAG, "onCreate: session " + session);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String[] columns = {"id", "engWord", "bangWord", "bangSyn", "engSyn", "example", "engPron", "bangPron", "type", "defination", "antonyms", "session", "mood"};
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        // database call to get the word
        Cursor cursor = myDbHelper.getWord(columns, "session=? and mood=?", new String[]{String.valueOf(session), String.valueOf(mood)});
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
//        Log.d(TAG, "onCreate: word size " + wordList);

        String sql = "SELECT * from bigData;";
        int max = myDbHelper.getSize(sql) - 1;


        Collections.shuffle(wordList);
        mHandler = new Handler();
        if (wordList.size() != 0) {
//            progressBar.setProgress((100/wordList.size())*(count+1));
            indexText.setText(count+1 + " / " + wordList.size());
//            Log.d(TAG, "onCreate: count : "+count+"  " +wordList.get(count).getEngWord());
            getNewWord(wordList.get(count), max);


        }

    }

    public void getNewWord(final Word word, final int max) {
        waitFlag = 0;
        wordList.get(count).setMood(1);
        wordList.get(count).setLearn(4);
        mProgressStatus = 0;
        delay(100, max);
        progressBar.setProgress((100 / wordList.size()) * (count+1));
        indexText.setText(count+1 + " / " + wordList.size());
        dontClick = true;
        Log.d(TAG + "iiiii", "onCreate: count : " + count + "  " + wordList.get(count).getEngWord());
        Log.d(TAG + "score", "getNewWord: score: " + score);
        animationView.cancelAnimation();
        animationView.clearAnimation();
        animationView.setVisibility(View.INVISIBLE);

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

        GradientDrawable tvBackground1 = (GradientDrawable) textop1.getBackground();
        tvBackground1.setColor(Color.TRANSPARENT);
        GradientDrawable tvBackground2 = (GradientDrawable) textop2.getBackground();
        tvBackground2.setColor(Color.TRANSPARENT);
        GradientDrawable tvBackground3 = (GradientDrawable) textop3.getBackground();
        tvBackground3.setColor(Color.TRANSPARENT);
        GradientDrawable tvBackground4 = (GradientDrawable) textop4.getBackground();
        tvBackground4.setColor(Color.TRANSPARENT);
        GradientDrawable tvBackground5 = (GradientDrawable) textop5.getBackground();
        tvBackground5.setColor(Color.TRANSPARENT);
        final Word finalWord = word;
        textop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                waitFlag = 1;
                boastUp(10);
                if (dontClick) {


                    dontClick = false;

                    if (word.getBangWord().equals(textop1.getText().toString())) {
                        score++;
                        GradientDrawable tvBackground = (GradientDrawable) textop1.getBackground();
                        tvBackground.setColor(Color.parseColor("#1b5e20"));

                        animationView.setAnimation("correct.json");
                        wordList.get(count).setMood(3);
                        wordList.get(count).setLearn(2);
                        startCorrectAnimation(max);

                    } else {
                        GradientDrawable tvBackground = (GradientDrawable) textop1.getBackground();
                        tvBackground.setColor(Color.parseColor("#b71c1c"));
                        animationView.setAnimation("wrong.json");
                        wordList.get(count).setMood(1);
                        wordList.get(count).setLearn(4);
                        startCorrectAnimation(max);
                    }
                }

//                onButtonShowPopupWindowClick(getWindow().getDecorView(), finalWord, max, textop1.getText().toString());
            }
        });
        textop2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boastUp(10);
//                waitFlag = 1;
                if (dontClick) {
                    dontClick = false;
                    if (word.getBangWord().equals(textop2.getText().toString())) {
                        score++;
                        GradientDrawable tvBackground = (GradientDrawable) textop2.getBackground();
                        tvBackground.setColor(Color.parseColor("#1b5e20"));

                        animationView.setAnimation("correct.json");
                        wordList.get(count).setMood(3);
                        wordList.get(count).setLearn(2);
                        startCorrectAnimation(max);
                    } else {
                        GradientDrawable tvBackground = (GradientDrawable) textop2.getBackground();
                        tvBackground.setColor(Color.parseColor("#b71c1c"));
                        animationView.setAnimation("wrong.json");
                        wordList.get(count).setMood(1);
                        wordList.get(count).setLearn(4);
                        startCorrectAnimation(max);
                    }
                }

            }
        });
        textop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boastUp(10);
//                waitFlag = 1;
                if (dontClick) {
                    dontClick = false;
                    if (word.getBangWord().equals(textop3.getText().toString())) {
                        score++;
                        GradientDrawable tvBackground = (GradientDrawable) textop3.getBackground();
                        tvBackground.setColor(Color.parseColor("#1b5e20"));

                        animationView.setAnimation("correct.json");
                        wordList.get(count).setMood(3);
                        wordList.get(count).setLearn(2);
                        startCorrectAnimation(max);

                    } else {
                        GradientDrawable tvBackground = (GradientDrawable) textop3.getBackground();
                        tvBackground.setColor(Color.parseColor("#b71c1c"));
                        animationView.setAnimation("wrong.json");
                        wordList.get(count).setMood(1);
                        wordList.get(count).setLearn(4);
                        startCorrectAnimation(max);
                    }
                }
            }
        });
        textop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boastUp(10);
//                waitFlag = 1;
                if (dontClick) {
                    dontClick = false;
                    if (word.getBangWord().equals(textop4.getText().toString())) {
                        score++;
                        GradientDrawable tvBackground = (GradientDrawable) textop4.getBackground();
                        tvBackground.setColor(Color.parseColor("#1b5e20"));
                        animationView.setAnimation("correct.json");
                        wordList.get(count).setMood(3);
                        wordList.get(count).setLearn(2);
                        startCorrectAnimation(max);
                    } else {
                        GradientDrawable tvBackground = (GradientDrawable) textop4.getBackground();
                        tvBackground.setColor(Color.parseColor("#b71c1c"));
                        animationView.setAnimation("wrong.json");
                        wordList.get(count).setMood(1);
                        wordList.get(count).setLearn(4);
                        startCorrectAnimation(max);
                    }
                }
            }
        });
        textop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boastUp(10);
//                waitFlag = 1;
                if (dontClick) {
                    dontClick = false;
                    if (word.getBangWord().equals(textop1.getText().toString())) {
                        score++;
                        GradientDrawable tvBackground = (GradientDrawable) textop5.getBackground();
                        tvBackground.setColor(Color.parseColor("#1b5e20"));
                        animationView.setAnimation("correct.json");
                        wordList.get(count).setMood(3);
                        wordList.get(count).setLearn(2);
                        startCorrectAnimation(max);
                    } else {
                        GradientDrawable tvBackground = (GradientDrawable) textop5.getBackground();
                        tvBackground.setColor(Color.parseColor("#b71c1c"));
                        animationView.setAnimation("wrong.json");
                        wordList.get(count).setMood(1);
                        wordList.get(count).setLearn(4);
                        startCorrectAnimation(max);
                    }
                }
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

    public void nextQue(int max, int count) {
        if (count < wordList.size()) {
            getNewWord(wordList.get(count), max);
        } else {

        }
    }

    public void startCorrectAnimation(final int max) {

        final int[] flag = {1};
//        animationView.loop(true);
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


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

    public void progresbar(final int time, final int max) {
//        ultimateTime=time;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < time) {
                    if (waitFlag == 0) {
                        mProgressStatus++;
                        SystemClock.sleep(time);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressStatus);
                            }
                        });
                    }
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        count++;

                        if (count == wordList.size() && !finishFlag) {
                            if (mood == 3) {
                                int scorePar = (100 / wordList.size()) * score;
                                indexText.setText((count+1) + " / " + wordList.size());
                                progressBar.setProgress((100 / wordList.size()) * (count+1));

                                Log.d(TAG + "score", "run: score :" + score + " p: " + scorePar);
                                if (scorePar >= 80) {
                                    ContentValues values = new ContentValues();
                                    values.put("mood", "2");
                                    for (int i = 0; i < wordList.size(); i++) {
                                        myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(wordList.get(i).getId())});
                                    }
//                                ContentValues contentValues=new ContentValues();
//                                contentValues.put("session",wordList.get(0).getSession());
//                                contentValues.put("done",1);
//                                long returnId= myDbHelper.writeData(contentValues);
//                                Log.d(TAG+"insert", "run: "+returnId);
                                } else {

                                    for (int i = 0; i < wordList.size(); i++) {
                                        ContentValues values = new ContentValues();
                                        values.put("mood", wordList.get(i).getMood());
                                        myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(wordList.get(i).getId())});
                                    }
                                }
                            } else if (mood == 2) {
                                int scorePar = (100 / wordList.size()) * score;
                                indexText.setText((count+1) + " / " + wordList.size());
                                progressBar.setProgress((100 / wordList.size()) * (count+1));

                                Log.d(TAG + "score", "run: score :" + score + " p: " + scorePar);

                                if (scorePar >= 80) {

                                } else {
                                    for (int i = 0; i < wordList.size(); i++) {
                                        ContentValues values = new ContentValues();
                                        values.put("learn", wordList.get(i).getLearn());
                                        myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(wordList.get(i).getId())});
                                    }
                                }
                            }


                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(score, wordList.size(), mood);
                            bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");

                        } else {
                            nextQue(max, count);
                        }
//                        mLoadingText.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    public void boastUp(final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < time * 10) {
                    mProgressStatus++;
                    android.os.SystemClock.sleep(time);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);

                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d("ffff","boast Up");


                        animationView.cancelAnimation();
                        animationView.clearAnimation();
                        animationView.setVisibility(View.INVISIBLE);
//                        nextQue();
//                        mLoadingText.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    public void delay(int time, int max) {
//        new TimedProgress();
        progresbar(time, max);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        waitFlag = 0;
        finishFlag = true;


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onButtonClick(String text) {


        if (text.equals("learn")) {
            Intent i = new Intent(getApplicationContext(), UltimateLearnActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(i);
        } else if (text.equals("tryAgain")) {
            Intent i = new Intent(getApplicationContext(), QuizActivity.class);
            i.putExtra("sessionNumber", session);
            i.putExtra("mood", 3);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(i);
        } else if (text.equals("dismiss")) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(i);
        } else if (text.equals("next")) {
            Intent i = new Intent(getApplicationContext(), DisLvlActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(i);
        } else if (text.equals("home")) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(i);
        }
    }


}
