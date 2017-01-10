package ro.gov.ithub.personala;

/**
 * Created by andrei.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;

import java.util.Locale;

public class PersonalAssistant implements Command.ICommand {
    private Context context;
    private TextToSpeech assistantSpeech;

    public PersonalAssistant(Context context){
        this.context = context;

        // Create Text To Speech Object
        assistantSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    assistantSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void giveActionToAssistant(String action) {
        // parse action
        if (action.equals("hello")) {
            // Do action here

            // Tell to the user what the assistant did
            assistantSpeech.speak("Hi, Chris! What do you need me to do?", TextToSpeech.QUEUE_FLUSH, null);
        } else if(action.equals("set a reminder")){
            // Do action here

            // Tell to the user what the assistant did
            assistantSpeech.speak("Reminder saved", TextToSpeech.QUEUE_FLUSH, null);
        } else if(action.equals("call")) {
            // Tell to the user what the assistant did
            assistantSpeech.speak("Making your call", TextToSpeech.QUEUE_FLUSH, null);
            // Request call permission
            int permissionCheck = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE);
            if (permissionCheck == android.content.pm.PackageManager.PERMISSION_DENIED){
                //Tell the user that you dnt have the permission to do a call
                assistantSpeech.speak("I don't have the permission to make a call", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                // If permission is granted make tha call
                String phoneNumber = "tel:0745745017";
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));

                context.startActivity(callIntent);
            }
        } else {
            assistantSpeech.speak("I don't understant", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void takeAction(String pAction){
        Command command = Command.fromString(pAction);
        command.execute(this);
    }

    @Override public void onNullCommand() {

    }

    @Override public void onCallCommand() {

    }
}
