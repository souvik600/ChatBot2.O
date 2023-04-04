package com.example.chatbot2o;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ImageView imageBack;
    ImageView imageExit;

    //chat
    RecyclerView recyclerView;
    TextView welcome_text;
    EditText text_message;
    ImageButton send_btn;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    //
    public static final MediaType JSON
            = MediaType.get ( "application/json; charset=utf-8" );

    OkHttpClient client = new OkHttpClient ( );
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        messageList = new ArrayList<> ( );
        imageBack = findViewById ( R.id.imageBack );
        imageExit = findViewById ( R.id.imageExit );
        recyclerView = findViewById ( R.id.recyclerView );
        welcome_text = findViewById ( R.id.welcome_text );
        text_message = findViewById ( R.id.text_message );
        send_btn = findViewById ( R.id.send_btn );

        //recyclerView Setup
        messageAdapter = new MessageAdapter ( messageList );
        recyclerView.setAdapter ( messageAdapter );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager ( this );
        linearLayoutManager.setStackFromEnd ( true );
        recyclerView.setLayoutManager ( linearLayoutManager );

        //send Button

        send_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String question = text_message.getText ( ).toString ( ).trim ( );
                addToChat ( question, Message.SEND_BY_ME );
                text_message.setText ( "" );
                callAPI ( question );
                welcome_text.setVisibility ( View.GONE );
            }
        } );

        imageBack.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent imageBack;
                imageBack = new Intent ( MainActivity.this, Splash_Screen_Activity.class );
                startActivity ( imageBack );
            }
        } );
        imageExit.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finishAffinity ( );
            }
        } );
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread ( new Runnable ( ) {
            @Override
            public void run() {
                messageList.add ( new Message ( message, sentBy ) );
                messageAdapter.notifyDataSetChanged ( );
                recyclerView.smoothScrollToPosition ( messageAdapter.getItemCount ( ) );
            }
        } );
    }

    void addResponse(String response) {
        addToChat ( response, Message.SEND_BY_BOT );
        messageList.remove ( messageList.size ()-1 );
    }


    void callAPI(String question) {
        //okhttp setup
        messageList.add ( new Message ( "Typing.....",Message.SEND_BY_BOT ) );
        JSONObject jsonBody = new JSONObject ( );

        try {
            jsonBody.put ( "model", "text-davinci-003" );
            jsonBody.put ( "prompt", question );
            jsonBody.put ( "max_tokens", 4000 );
            jsonBody.put ( "temperature", 0 );

        } catch (JSONException e) {
            e.printStackTrace ( );
        }
        RequestBody body = RequestBody.create ( jsonBody.toString ( ), JSON );
        Request request = new Request.Builder ( )
                .url ( "https://api.openai.com/v1/completions" )
                .header ( "Authorization", "Bearer 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" )
                .post ( body )
                .build ( );
        client.newCall ( request ).enqueue ( new Callback ( ) {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse ( "Faild to load Response due to" + e.getMessage ( ) );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful ()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject (response.body ().string ());
                        JSONArray jsonArray = jsonObject.getJSONArray ( "choices" );
                        String result = jsonArray.getJSONObject ( 0 ).getString ( "text" );
                        addResponse ( result.trim () );
                    } catch (JSONException e) {
                        throw new RuntimeException ( e );
                    }

                }else {
                    addResponse ( "Faild to load Response due to" +response.body ().toString ());
                }

            }
        } );
    }


//    void addResponse(String response) {
//        addToChat ( response, Message.SEND_BY_BOT );
//    }

//

//    Void callAPI(String question) {
//        //okhttp setup
//        JSONObject jsonBody = new JSONObject ( );
//
//        try {
//            jsonBody.put ( "model", "text-davinci-003" );
//            jsonBody.put ( "prompt", question );
//            jsonBody.put ( "max_tokens", 4000 );
//            jsonBody.put ( "temperature", 0 );
//
//        } catch (JSONException e) {
//            e.printStackTrace ( );
//        }
//        RequestBody body = RequestBody.create ( jsonBody.toString ( ), JSON );
//        Request request = new Request.Builder ( )
//                .url ( "https://api.openai.com/v1/completions" )
//                .header ( "Authorization", "Bearer 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" )
//                .post ( body )
//                .build ( );
//
//        client.newCall ( request ).enqueue ( new Callback ( ) {
//            public void onFailure(Call call, IOException e) {
//                addResponse ( "Faild to load Response due to" + e.getMessage ( ) );
//
//            }
//
//            public void onResponse(@NonNull Call call, Response response)throws IOException {
//
//
//            }
//
//        } );
//    }
}