package com.example.asus.vocabulary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.asus.vocabulary.Adapter.LvlAdapter;
import com.example.asus.vocabulary.Adapter.MultiSelectAdapter;
import com.example.asus.vocabulary.BottomSheetDialog;
import com.example.asus.vocabulary.BottomSheetDialogForNothing;
import com.example.asus.vocabulary.Model.SessionModel;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.RecyclerItemClickListener;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.ArrayList;

public class DisLvlActivity extends AppCompatActivity implements BottomSheetDialogForNothing.BottomSheetListenerForNothig{
    private RecyclerView recyclerView;
    LvlAdapter lvlAdapter;
    ArrayList<SessionModel> lvl = new ArrayList<>();
    DatabaseHelper myDbHelper;
    String TAG = DisLvlActivity.this.getClass().getSimpleName() + "lvl";
    int limit=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_lvl);

        myDbHelper=new DatabaseHelper(this);
        String sql="SELECT * from bigData where mood=2;";
        int session=myDbHelper.getSize(sql);
        Log.d(TAG, "onCreate: total word in mastered "+session);
        session/=limit;
        session+=1;

        sql = "SELECT * from bigData where mood=1 or mood=3;";
        int completeCount = myDbHelper.getSize(sql);

        for (int i=1;i<=20;i++){

            lvl.add(new SessionModel(i,session,"date"));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView11);

        lvlAdapter = new LvlAdapter(this, lvl,completeCount);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(lvlAdapter);


        final int finalSession = session;
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG+"vvv", "onItemClick: session before click "+finalSession);
                if (position<finalSession){
                    if ((position+1)==finalSession){

                        Log.d(TAG+"vvv", "onItemClick: session after click "+finalSession);
                        Intent intent = new Intent(getApplicationContext(), DiscoverWord.class);
                        intent.putExtra("active", position+1);
                        startActivity(intent);

                    }
                    else {
                        BottomSheetDialogForNothing bottomSheetDialog = new BottomSheetDialogForNothing("Completed This Level", "Now you can practice this Level in mastered segment", "practice","practice");
                        bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onButtonClick(String text) {
        if (text.equals("practice")) {
            Intent i = new Intent(getApplicationContext(), MasteredActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
