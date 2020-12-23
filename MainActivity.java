package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.project_aid.R;
import java.util.ArrayList;
import java.util.Locale;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;


public class MainActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private EditText editText;
    private ImageView micButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        editText = findViewById(R.id.text);
        micButton = findViewById(R.id.button);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // initializing string arrays
        final String[] AlphaNumeric = new String[37];

        // string array for storing alphabets and numbers
        final String[] AlphaNumeric1 = new String[37];
        final String[] AlphaNumeric2 = new String[4];

        // string array for storing corresponding morse code
        // assigning alphabets to the string array Alphanumeric[]
        AlphaNumeric[0] = "A";
        AlphaNumeric[1] = "B";
        AlphaNumeric[2] = "C";
        AlphaNumeric[3] = "D";
        AlphaNumeric[4] = "E";
        AlphaNumeric[5] = "F";
        AlphaNumeric[6] = "G";
        AlphaNumeric[7] = "H";
        AlphaNumeric[8] = "I";
        AlphaNumeric[9] = "J";
        AlphaNumeric[10] = "K";
        AlphaNumeric[11] = "L";
        AlphaNumeric[12] = "M";
        AlphaNumeric[13] = "N";
        AlphaNumeric[14] = "O";
        AlphaNumeric[15] = "P";
        AlphaNumeric[16] = "Q";
        AlphaNumeric[17] = "R";
        AlphaNumeric[18] = "S";
        AlphaNumeric[19] = "T";
        AlphaNumeric[20] = "U";
        AlphaNumeric[21] = "V";
        AlphaNumeric[22] = "W";
        AlphaNumeric[23] = "X";
        AlphaNumeric[24] = "Y";
        AlphaNumeric[25] = "Z";
        AlphaNumeric[26] = "0";
        AlphaNumeric[27] = "1";
        AlphaNumeric[28] = "2";
        AlphaNumeric[29] = "3";
        AlphaNumeric[30] = "4";
        AlphaNumeric[31] = "5";
        AlphaNumeric[32] = "6";
        AlphaNumeric[33] = "7";
        AlphaNumeric[34] = "8";
        AlphaNumeric[35] = "9";
        AlphaNumeric[36] = " ";

        // assigning the corresponding morse code
        // for each letter and number to
        // Alphanumeric1[] array
        AlphaNumeric1[0] = ".-";
        AlphaNumeric1[1] = "-...";
        AlphaNumeric1[2] = "-.-.";
        AlphaNumeric1[3] = "-..";
        AlphaNumeric1[4] = ".";
        AlphaNumeric1[5] = "..-.";
        AlphaNumeric1[6] = "--.";
        AlphaNumeric1[7] = "....";
        AlphaNumeric1[8] = "..";
        AlphaNumeric1[9] = ".---";
        AlphaNumeric1[10] = "-.-";
        AlphaNumeric1[11] = ".-..";
        AlphaNumeric1[12] = "--";
        AlphaNumeric1[13] = "-.";
        AlphaNumeric1[14] = "---";
        AlphaNumeric1[15] = ".--.";
        AlphaNumeric1[16] = "--.-";
        AlphaNumeric1[17] = ".-.";
        AlphaNumeric1[18] = "...";
        AlphaNumeric1[19] = "-";
        AlphaNumeric1[20] = "..-";
        AlphaNumeric1[21] = "...-";
        AlphaNumeric1[22] = ".--";
        AlphaNumeric1[23] = "-..-";
        AlphaNumeric1[24] = "-.--";
        AlphaNumeric1[25] = "--..";
        AlphaNumeric1[26] = "-----";
        AlphaNumeric1[27] = ".----";
        AlphaNumeric1[28] = "..---";
        AlphaNumeric1[29] = "...--";
        AlphaNumeric1[30] = "....-";
        AlphaNumeric1[31] = ".....";
        AlphaNumeric1[32] = "-....";
        AlphaNumeric1[33] = "--...";
        AlphaNumeric1[34] = "---..";
        AlphaNumeric1[35] = "----.";
        AlphaNumeric1[36] = "/";

        AlphaNumeric2[0] = ".";
        AlphaNumeric2[1] = "-";
        AlphaNumeric2[2] = "/";
        AlphaNumeric2[3] = "0";

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editText.setText("");
                editText.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResults(Bundle bundle) {
                micButton.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String input = data.get(0);

                String output = "";

                // variable used to compute the output
                // to get the length of the input string
                int l = input.length();

                // variables used in loops
                int i, j;

                for (i = 0; i < l; i++) {

                    // to extract each Token of the string at a time
                    String ch = input.substring(i, i + 1);

                    // the loop to check the extracted token with
                    // each letter and store the morse code in
                    // the output variable accordingly
                    for (j = 0; j < 37; j++) {

                        if (ch.equalsIgnoreCase(AlphaNumeric[j])) {

                            // concat space is used to separate
                            // the morse code of each token
                            output = output.concat(AlphaNumeric1[j]).concat(" ");


                        }
                    }
                    for (i = 0; i < l; i++) {

                        // to extract each Token of the string at a time
                        String bee = output.substring(i, i + 1);

                        // the loop to check the extracted token with

                        // the output variable according to the morse code


                        if (bee.equalsIgnoreCase(AlphaNumeric2[0])) {
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 400 milliseconds
                            v.vibrate(400);
                        }
                        if (bee.equalsIgnoreCase(AlphaNumeric2[1])) {
                            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,400);
                        }
                        if (bee.equalsIgnoreCase(AlphaNumeric2[2])) {
                            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 0);
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,600);
                        }
                        if (bee.equalsIgnoreCase(AlphaNumeric2[3])) {
                            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 0);
                            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,200);
                        }

                    }
                }
                editText.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    micButton.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }
}
