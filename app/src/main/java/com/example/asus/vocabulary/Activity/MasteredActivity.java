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
import android.widget.TextView;

import com.example.asus.vocabulary.Adapter.MultiSelectAdapter;
import com.example.asus.vocabulary.BottomSheetDialogForNothing;
import com.example.asus.vocabulary.Master.MasterLearnActivity;
import com.example.asus.vocabulary.Model.SessionModel;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.RecyclerItemClickListener;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.ArrayList;

public class MasteredActivity extends AppCompatActivity implements BottomSheetDialogForNothing.BottomSheetListenerForNothig{
    private RecyclerView recyclerView;
    MultiSelectAdapter multiSelectAdapter;
    ArrayList<SessionModel> sessionModels = new ArrayList<>();
    DatabaseHelper myDbHelper;
    String TAG = MasteredActivity.this.getClass().getSimpleName() + "masteredActivity";
    TextView noDataFoundText;
    int limit=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        noDataFoundText=(TextView)findViewById(R.id.no_data_found);
        myDbHelper=new DatabaseHelper(this);
        String sql="SELECT * from bigData where mood=2 or mood=4;";
        int session=myDbHelper.getSize(sql);
        Log.d(TAG, "onCreate: total word in mastered "+session);
//        session/=limit;
//        for (int i=0;i<=session;i++){
//            sessionModels.add(new SessionModel(i+1,1,"date"));
//        }
//        SessionModel sessionModel=new SessionModel();
//        lvl.add(new SessionModel(1,"date","date"));

        Cursor cursor1 = myDbHelper.getWord(new String[]{"id"}, "mood=?", new String[]{String.valueOf(2)});
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView11);

        session/=limit;

//        session+=1;
        Log.d(TAG+"sss", "onCreate: session :"+session);

//        session+=1;

        if (cursor1.getCount()!=0){

            for (int i=0;i<session;i++){
                sessionModels.add(new SessionModel(i+1,1,"date"));
            }

            multiSelectAdapter = new MultiSelectAdapter(this,sessionModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(multiSelectAdapter);

        }else {
            recyclerView.setVisibility(View.GONE);
//            noDataFoundText.setVisibility(View.VISIBLE);

            BottomSheetDialogForNothing bottomSheetDialog = new BottomSheetDialogForNothing("Learn First !!", "First complete a level to add your master list", "Okay","master");
            bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");
        }



        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(getApplicationContext(), ViewMasterWordActivity.class);
//                intent.putExtra("sessionNumber",sessionModels.get(position).getSessionNumber());
                intent.putExtra("session", (position+1));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onButtonClick(String text) {

        if(text.equals("master")){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (text.equals("dismissMaster")){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
