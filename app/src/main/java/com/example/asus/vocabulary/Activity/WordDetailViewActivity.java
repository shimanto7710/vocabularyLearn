package com.example.asus.vocabulary.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asus.vocabulary.Fragments.LearnFrag;
import com.example.asus.vocabulary.Model.MasteredWord;
import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.Locale;

public class WordDetailViewActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener{
    DatabaseHelper myDbHelper;
    private TextToSpeech tts;
    String TAG = WordDetailViewActivity.this.getClass().getSimpleName() + "learnFrag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail_view);
        tts = new TextToSpeech(getApplicationContext(), this);

        myDbHelper=new DatabaseHelper(this);
        Intent intent = getIntent();
        final int id=intent.getIntExtra("id",0);


        String[] columns = {"id", "engWord", "bangWord", "bangSyn", "engSyn", "example", "engPron", "bangPron", "type", "defination", "antonyms", "session", "mood","dificulty","learn"};


        // database call to get the word
        Cursor cursor = myDbHelper.getWord(columns, "id=?", new String[]{String.valueOf(id) });

        cursor.moveToFirst();
        Word word = null;
        word = new Word(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));


        final TextView wordText = (TextView)  findViewById(R.id.word_text);
        TextView typeText = (TextView) findViewById(R.id.type);
        TextView bngWordText = (TextView)  findViewById(R.id.bang_word_text);
        TextView bngPronText = (TextView)  findViewById(R.id.bng_pron_txt);
        TextView synBangText = (TextView)  findViewById(R.id.bang_syn_txt);
        TextView defText = (TextView)  findViewById(R.id.defi_txt);
        TextView synEngText = (TextView) findViewById(R.id.eng_syn_txt);
        TextView antoText = (TextView)  findViewById(R.id.eng_antonyms_txt);
        TextView exampleText = (TextView)  findViewById(R.id.exanple_txt);
        TextView eng_pron_txt = (TextView)  findViewById(R.id.eng_pron_txt);
        final TextView answerStatusText = (TextView)  findViewById(R.id.answer_status);
        ImageButton btnSoundBtn=(ImageButton)  findViewById(R.id.soundButton);
//        word=wordList.get(pos);



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
        eng_pron_txt.setText(word.getEngPron());
        Log.d(TAG, "onButtonShowPopupWindowClick: bngSyn " + word.getBngSyn());

        synBangText.setText(word.getBngSyn());

        final Word finalWord = word;
        btnSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TextToSpeech tts = new TextToSpeech(context, context);
//                tts.setLanguage(Locale.US);
//                tts.speak(listItem.getEngWord(), TextToSpeech.QUEUE_ADD, null);

                speakOut(finalWord.getEngWord());

            }
        });

        if (word.getDefinition() != null) {
            defText.setText(word.getDefinition().substring(0, 1).toUpperCase() + word.getDefinition().substring(1));
        } else defText.setText("NA");

        if (word.getEngSyn() != null && word.getEngSyn() != " ") {

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



    }


    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String txt) {

        tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
    }


}
