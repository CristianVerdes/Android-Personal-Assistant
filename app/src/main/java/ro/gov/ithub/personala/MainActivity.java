package ro.gov.ithub.personala;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;

    Button startVoiceRecognition;
    TextView displayRecognizedText;
    Dialog matchTextDialog;
    ListView textList;
    ArrayList<String> match_text;
    PersonalAssistant personalAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.personalAssistant = new PersonalAssistant(this);
        thingsWeNeed();

        startVoiceRecognition.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View pView) {
                if(isInternetActivated()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.ACTION_RECOGNIZE_SPEECH, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "Please connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public TextToSpeech tts;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            match_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            displayRecognizedText.setText("You have said: " + match_text.get(0));

            // Get text from match_text and do the match_text action
            personalAssistant.giveActionToAssistant(match_text.get(0));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }


    private void thingsWeNeed(){
        startVoiceRecognition = (Button) findViewById(R.id.button);
        displayRecognizedText = (TextView) findViewById(R.id.textView);
        personalAssistant = new PersonalAssistant(MainActivity.this);

    }

    public boolean isInternetActivated(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifiNetworkInfo.isConnected() || mobileNetworkInfo.isConnected()){
            return true;
        } else {
            return false;
        }
    }

}

