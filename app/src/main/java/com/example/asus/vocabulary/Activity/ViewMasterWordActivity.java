package com.example.asus.vocabulary.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.asus.vocabulary.Adapter.MasterWordAdapter;
import com.example.asus.vocabulary.Adapter.MultiSelectAdapter;
import com.example.asus.vocabulary.Master.MasterLearnActivity;
import com.example.asus.vocabulary.Model.MasteredWord;
import com.example.asus.vocabulary.Model.SessionModel;
import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.RecyclerItemClickListener;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.ArrayList;

public class ViewMasterWordActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    MasterWordAdapter masterWordAdapter;
    ArrayList<MasteredWord> masteredWords = new ArrayList<>();
    DatabaseHelper myDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_master_word);


        myDbHelper=new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView11);

        Intent intent = getIntent();
        final int ssss=intent.getIntExtra("session",0);

        String[] columns = {"id", "engWord", "bangWord", "bangSyn", "engSyn", "example", "engPron", "bangPron", "type", "defination", "antonyms", "session", "mood","dificulty","learn"};


        // database call to get the word
        Cursor cursor = myDbHelper.getWord(columns, "mood=? and session=?", new String[]{String.valueOf(2),String.valueOf(ssss) });

        MasteredWord word = null;
        while (!cursor.isAfterLast()) {
            word=new MasteredWord(cursor.getInt(0),cursor.getString(1),cursor.getString(2));

            masteredWords.add(word);
//                sessionModels.add(String.valueOf(cursor.get));

            cursor.moveToNext();
        }
        cursor.close();


        masterWordAdapter = new MasterWordAdapter(this,masteredWords);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(masterWordAdapter);


        final Button quizBtn = (Button) findViewById(R.id.quiz);

        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MasterLearnActivity.class);
//                intent.putExtra("sessionNumber",sessionModels.get(position).getSessionNumber());
                intent.putExtra("session", (ssss));
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(getApplicationContext(), WordDetailViewActivity.class);
//                intent.putExtra("sessionNumber",sessionModels.get(position).getSessionNumber());
                intent.putExtra("id", masteredWords.get(position).getId());
                startActivity(intent);

//                Intent intent=new Intent(getApplicationContext(), MasterLearnActivity.class);
////                intent.putExtra("sessionNumber",sessionModels.get(position).getSessionNumber());
//                intent.putExtra("session", (position+1));
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));


    }
}
