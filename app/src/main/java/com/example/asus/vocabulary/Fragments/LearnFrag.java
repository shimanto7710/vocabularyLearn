package com.example.asus.vocabulary.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.vocabulary.Activity.DisLvlActivity;
import com.example.asus.vocabulary.Activity.LearnActivity;
import com.example.asus.vocabulary.Activity.MainActivity;
import com.example.asus.vocabulary.Activity.QuizActivity;
import com.example.asus.vocabulary.Activity.UltimateLearnActivity;
import com.example.asus.vocabulary.BottomSheetDialogForNothing;
import com.example.asus.vocabulary.BottomSheetDialogForTwo;
import com.example.asus.vocabulary.Model.Word;
import com.example.asus.vocabulary.R;
import com.example.asus.vocabulary.db.DatabaseHelper;

import java.util.List;
import java.util.Locale;


public class LearnFrag extends Fragment implements
        TextToSpeech.OnInitListener, BottomSheetDialogForNothing.BottomSheetListenerForNothig{

    Word word;
    private TextToSpeech tts;
    DatabaseHelper myDbHelper;
    String TAG = LearnFrag.this.getClass().getSimpleName() + "learnFrag";
    int session;
    int limit = 20;
    Button qBtn;
    List<Word> wordList;
    int pos;
    public ImageButton btnSoundBtn;
    public LearnFrag() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public LearnFrag(Word word, Button qBtn, List<Word> wordList, int pos) {
        this.word = word;
        this.qBtn = qBtn;
        this.wordList = wordList;
        this.pos = pos;

        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        myDbHelper = new DatabaseHelper(getContext());
        tts = new TextToSpeech(getContext(), this);
        final ImageButton doneBtn = (ImageButton) view.findViewById(R.id.doneBtn);
//        textWord.setText(word.getEngWord().substring(0, 1).toUpperCase() + word.getEngWord().substring(1));
        String sql = "SELECT * from bigData where mood=3 or mood=1 or mood=2;";
        session = myDbHelper.getSize(sql);
        session /= limit;
//        qBtn.setVisibility(Color.parseColor("#FFFFFF"));
        Log.d(TAG + "pos", "onCreateView: pos " + pos);
//        qBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));

        final TextView wordText = (TextView) view.findViewById(R.id.word_text);
        TextView typeText = (TextView) view.findViewById(R.id.type);
        TextView bngWordText = (TextView) view.findViewById(R.id.bang_word_text);
        TextView bngPronText = (TextView) view.findViewById(R.id.bng_pron_txt);
        TextView synBangText = (TextView) view.findViewById(R.id.bang_syn_txt);
        TextView defText = (TextView) view.findViewById(R.id.defi_txt);
        TextView synEngText = (TextView) view.findViewById(R.id.eng_syn_txt);
        TextView antoText = (TextView) view.findViewById(R.id.eng_antonyms_txt);
        TextView exampleText = (TextView) view.findViewById(R.id.exanple_txt);
        TextView eng_pron_txt = (TextView) view.findViewById(R.id.eng_pron_txt);
        final TextView answerStatusText = (TextView) view.findViewById(R.id.answer_status);
        btnSoundBtn=(ImageButton) view.findViewById(R.id.soundButton);
//        word=wordList.get(pos);

        if (word.getMood() == 3) {
            answerStatusText.setBackgroundColor(Color.parseColor("#588F27"));
            answerStatusText.setText("Already Learned");
            doneBtn.setVisibility(View.INVISIBLE);

        } else {
            answerStatusText.setBackgroundColor(Color.parseColor("#CD2C24"));
            answerStatusText.setText("Learn It?");

            doneBtn.setBackgroundColor(Color.parseColor("#CD2C24"));
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
        eng_pron_txt.setText(word.getEngPron());
        Log.d(TAG, "onButtonShowPopupWindowClick: bngSyn " + word.getBngSyn());

        synBangText.setText(word.getBngSyn());

        btnSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TextToSpeech tts = new TextToSpeech(context, context);
//                tts.setLanguage(Locale.US);
//                tts.speak(listItem.getEngWord(), TextToSpeech.QUEUE_ADD, null);

                speakOut(word.getEngWord());

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


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newFrag = 0;
                int nextOne = 0;
                int didNotComplete = -1;
                boolean newWordFlag = false;
                boolean newWordFlag2 = false;
                int j = 0;
                for (int i = 0; i < wordList.size(); i++) {
                    if (didNotComplete == -1 && wordList.get(i).getMood() == 1) {
                        didNotComplete = i;
                    }
                    if (wordList.get(i).getEngWord().equals(word.getEngWord())) {
                        newFrag = i;
                        pos = i;
                        newWordFlag = true;
                        j = i;
                        nextOne = i;
                        break;
                    }
                }
                j++;
                for (; j < wordList.size(); j++) {
                    if (wordList.get(j).getMood() == 1) {
                        newWordFlag = true;
                        newFrag = j;
                        newWordFlag2 = true;
                        break;
                    }
                }

                nextOne++;
//                newFrag++;


                doneBtn.setBackgroundColor(Color.parseColor("#588F27"));
                answerStatusText.setBackgroundColor(Color.parseColor("#588F27"));
                answerStatusText.setText("Already Learned");
                doneBtn.setVisibility(View.INVISIBLE);

                wordList.get(pos).setMood(3);

                ContentValues values = new ContentValues();
                values.put("mood", "3");
//                        values.put("session", session);
                Log.d(TAG + "sss", "onClick: learn sss " + session);
                myDbHelper.updateData(values, "id=?", new String[]{String.valueOf(word.getId())});

                boolean f = false;
                for (int i = 0; i < wordList.size(); i++) {
                    if (wordList.get(i).getMood() == 1) {
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    if (wordList.size() == limit) {
                        qBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
                //the number of the new Fragment to show
                UltimateLearnActivity parent = (UltimateLearnActivity) getActivity();
                Log.d(TAG + "qqq", "onClick: didNotCom " + didNotComplete);
                Log.d(TAG + "qqq", "onClick: newFrag :" + newFrag);
                Log.d(TAG + "qqq", "onClick: newFragFlag :" + newWordFlag);
                Log.d(TAG + "qqq", "onClick: newFragFlag2 :" + newWordFlag2);

                if (newWordFlag2) {
                    newWordFlag = false;
                    newWordFlag2 = false;
                    didNotComplete = -1;
                    parent.setPagerFragment(newFrag);
                } else if (nextOne < wordList.size()) {

                    parent.setPagerFragment(nextOne);
                } else if (didNotComplete != -1) {
                    newWordFlag = false;
                    didNotComplete = -1;
                    newWordFlag2 = false;
                    parent.setPagerFragment(didNotComplete);
                }
//                if (newFrag < wordList.size()) {
//                    parent.setPagerFragment(newFrag);
//                }else if (didNotComplete!=-1){
////                    parent.setPagerFragment(didNotComplete);
//                }
            }
        });


        qBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean f = false;
                for (int i = 0; i < wordList.size(); i++) {
                    if (wordList.get(i).getMood() == 1) {
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    if (wordList.size() == limit) {
                        String sql = "SELECT * from bigData where mood=3 or mood=1 or mood=2;";
                        session = myDbHelper.getSize(sql);
                        session /= limit;

                        Intent intent = new Intent(getContext(), QuizActivity.class);
                        Log.d(TAG + "asd", "onClick: session learn: " + session);
                        intent.putExtra("sessionNumber", session);
                        intent.putExtra("mood", 3);
                        startActivity(intent);
                    } else {

//                        Toast.makeText(getContext(), "Discover all words from your current lvl!",
//                                Toast.LENGTH_LONG).show();


                        BottomSheetDialogForNothing bottomSheetDialog = new BottomSheetDialogForNothing("Discover !!", "Discover rest of the words of current level to proceed.", "Discover","discover");
                        bottomSheetDialog.show(getFragmentManager(), "exampleBottomSheet");

                    }
//                        quizBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {

                    BottomSheetDialogForTwo bottomSheetDialog = new BottomSheetDialogForTwo();
                    bottomSheetDialog.show(getFragmentManager(), "exampleBottomSheet");

//                    Toast.makeText(getContext(), "You have to complete all words!",
//                            Toast.LENGTH_LONG).show();
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
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

    @Override
    public void onButtonClick(String text) {
        Log.d(TAG, "onButtonClick: "+text);
        if (text.equals("discover")){
            Log.d(TAG, "onButtonClick: asdasdsadsadasd");
            Intent i = new Intent(getContext(), DisLvlActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
