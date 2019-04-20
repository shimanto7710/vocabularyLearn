package com.example.asus.vocabulary.Activity;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.vocabulary.BottomSheetDialog;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDbHelper;

    String TAG=MainActivity.this.getClass().getSimpleName()+"mmm";
    CardView discoverCard,learnCard,masterCard;
    TextView discoverSessionText,learnSessionText,masteredWordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog();
//        bottomSheetDialog.show(getSupportFragmentManager(),"exampleBottomSheet");
        setSupportActionBar(toolbar);
        myDbHelper=new DatabaseHelper(this);

        discoverCard=(CardView)findViewById(R.id.discover_card);
        learnCard=(CardView)findViewById(R.id.learn_card);
        masterCard=(CardView)findViewById(R.id.mastered_card);
        discoverSessionText=(TextView) findViewById(R.id.session_text_discover_main_activity);
        learnSessionText=(TextView) findViewById(R.id.session_text_learn_main_activity);
//        needToFinishText=(TextView) findViewById(R.id.need_to_finish_text_learn_main_activity);
        masteredWordText=(TextView) findViewById(R.id.no_of_word_text_mastered_main_activity);

        String sql = "SELECT * from bigData where mood=1 or mood=2 or mood=3;";
        int session = myDbHelper.getSize(sql);
        Log.d(TAG, "onCreate: total word used " + session);
        session = session / 20;
        session += 1;

        discoverSessionText.setText("Running Level - "+(session));
        learnSessionText.setText("Learn and test yourself");
        Cursor cursor = myDbHelper.getWord(new String[]{"id"}, "mood=? or mood=?", new String[]{String.valueOf(1), String.valueOf(3)});
        int size =cursor.getCount();
        Log.d(TAG, "onCreate: cursor size: "+size);
//        needToFinishText.setText("Need To Finish By "+(size)+" Words");
        if (size==0){
//            needToFinishText.setText("Need to discover word first");
        }

        Cursor cursor1 = myDbHelper.getWord(new String[]{"id"}, "mood=?", new String[]{String.valueOf(2)});
        masteredWordText.setText(cursor1.getCount()+" words completed");

        discoverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DisLvlActivity.class);
                startActivity(i);
            }
        });

        learnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),UltimateLearnActivity.class);
                startActivity(i);
            }
        });
        masterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MasteredActivity.class);
                startActivity(i);
            }
        });



    }




    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

//    @Override
//    public void onButtonClick(String text) {
//        Toast.makeText(getApplicationContext(), "Clicked!",
//                                Toast.LENGTH_LONG).show();
//        Intent i=new Intent(MainActivity.this,MainActivity.class);
//        startActivity(i);
//    }
}
