package com.example.asus.vocabulary.Master;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.vocabulary.Activity.MainActivity;
import com.example.asus.vocabulary.Activity.MasteredActivity;
import com.example.asus.vocabulary.Fragments.LearnFrag;
import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MasterLearnActivity extends AppCompatActivity {
    DatabaseHelper myDbHelper;
    String TAG = MasterLearnActivity.this.getClass().getSimpleName() + "ultimate";
    int counter;
    ArrayList<Word> wordList;
    int session;
    int limit = 20;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_learn);

        myDbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        int ssss=intent.getIntExtra("session",0);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplication(), MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });


        wordList = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);

        String[] columns = {"id", "engWord", "bangWord", "bangSyn", "engSyn", "example", "engPron", "bangPron", "type", "defination", "antonyms", "session", "mood","dificulty","learn"};


        // database call to get the word
        Cursor cursor = myDbHelper.getWord(columns, "mood=? and session=?", new String[]{String.valueOf(2),String.valueOf(ssss) });
        Log.d(TAG, "onCreate: cursor Size: " + cursor.getCount());

        Word word = null;
        cursor.moveToFirst();

        List<Word> dummyList = new ArrayList<>();
        boolean quizFlag = false;
        // if it is not a new value
        while (!cursor.isAfterLast()) {
            word = new Word(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13), cursor.getInt(14));
            if (cursor.getInt(14) == 4) {
                quizFlag = true;
                wordList.add(word);
            } else {
                dummyList.add(word);
            }
            cursor.moveToNext();
        }
        cursor.close();
        wordList.addAll(dummyList);

        final Button quizBtn = (Button) findViewById(R.id.quiz);
        Log.d(TAG + "lll", "onCreate: in " + wordList.size() + "  " + limit);
//        if (!quizFlag) {
//            if (wordList.size() != limit) {
//                quizBtn.setBackgroundColor(Color.parseColor("#FFACA9A9"));
//            }
////            quizBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        } else {
//            quizBtn.setBackgroundColor(Color.parseColor("#FFACA9A9"));
//        }

        Log.d(TAG + "qqq", "onCreate: original list size: " + wordList.size());
        Log.d(TAG + "qqq", "onCreate: dummy list size: " + dummyList.size());


//        String sql = "SELECT * from bigData;";
//        int max = myDbHelper.getSize(sql) - 1;

//        sql = "SELECT * from bigData where mood=1 or mood=2 or mood=3;";
//        session = myDbHelper.getSize(sql);
//        session /= limit;
//        session += 1;

//        Collections.shuffle(wordList);
        counter = 0;
        session=wordList.get(counter).getSession();
        Log.d("sss", "session :" + session);
        Log.d(TAG, "onCreate: wordList Size " + wordList.size());

        if (wordList.size() != 0) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), wordList, quizBtn);
            viewPager.setAdapter(adapter);
//            getNewWord(wordList.get(counter), max);
        } else {
//            rootLayout.setVisibility(View.GONE);
//            noDataLayout.setVisibility(View.VISIBLE);

//            noDataPopup(getWindow().getDecorView());
        }


    }

    public void setPagerFragment(int a) {
        viewPager.setCurrentItem(a, true);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        int currentPos;
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private List<Word> dataList = new ArrayList<>();
        //        private final List<String> mFragmentTitleList = new ArrayList<>();
//        private Fragment fragment;
        Button quizBtn;

        public ViewPagerAdapter(FragmentManager manager, List<Word> dataList, Button qBtn) {
            super(manager);
            this.dataList = dataList;
            this.quizBtn = qBtn;
        }

        @Override
        public Fragment getItem(int position) {


//            return mFragmentList.get(position);
//            notifyDataSetChanged();
            currentPos = viewPager.getCurrentItem();
            Fragment fragmen = new MasterLearnFrag(dataList.get(position), quizBtn, wordList, currentPos);
            return fragmen;
        }

        @Override
        public int getCount() {
            return dataList.size();

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), MasteredActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
