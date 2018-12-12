package com.example.surabhi.test_text_to_speech;
import com.example.surabhi.test_text_to_speech.AndroidMultiPartEntity.ProgressListener;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import android.os.AsyncTask;
//import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.*;
import okhttp3.MultipartBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private TextToSpeech TTS;
    private Button call;
    private Button callapi;
    private EditText in;

    private EditText emai;
    private Button ap;
    private TextView t;

    String API_KEY = "SiU6DYyfpsrgkdu0R1lOT6qfru1nzhBP";
    String API_URL = "https://api.fullcontact.com/v2/person.json?";

    //IBM WATSON TTS
    String key = "EIHu-SwxCGE4pW707R2t1yJEfMpadC7e3k-rTKTUUGZV";
    String url = "https://gateway-syd.watsonplatform.net/text-to-speech/api";

//    @Override
//    protected void onDestroy()
//    {
//
//        if(TTS!=null)
//        {
//            TTS.stop();
//            TTS.shutdown();
//        }
//        super.onDestroy();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        in = (EditText) findViewById(R.id.inp);
        call = (Button) findViewById(R.id.tts);
        callapi = (Button)findViewById(R.id.ttsapi);
        emai = (EditText) findViewById(R.id.em);
        ap = (Button) findViewById(R.id.api);
        t = (TextView) findViewById(R.id.tv);


//        TTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//                if (i == TextToSpeech.SUCCESS) {
//                    int res = TTS.setLanguage(Locale.ENGLISH);
//
//                    if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
//
//                        Log.e("TTS", "Language not supported");
//                    }
//                }
//
//                else
//                {
//                    Log.e("TTS", "Initialization failed");
//                }
//            }
//        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tospeak = in.getText().toString();
                in.setText("");
                //TTS.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        callapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new taskclassnew().execute();
//                RequestBody requestBody = new MultipartBody().Builder().setType(MultipartBody.FORM)
//                        .addFormDataPart()
//                new taskclassnew().execute();
            }
        });

        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new taskclass().execute();
            }
        });
    }


    class taskclassnew extends AsyncTask<Void, Void, String>
    {
        protected String doInBackground(Void... urls)
        {
            String path = "/media/surabhi/Data_Backup/surabhi/handwriting/Test_Text_to_Speech/";
            System.out.println("PATH IS: "+path);

            File mFolder = new File(getFilesDir() + "/sample");
            File imgFile = new File(mFolder.getAbsolutePath() + "/hello_world.wav");

            System.out.println(getFilesDir());
            System.out.println(mFolder.getAbsolutePath());

            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
            if (!imgFile.exists()) {
                try {

                    imgFile.createNewFile();
                }

                catch (IOException e)
                {

                }

            }
            FileOutputStream fos = null;

            //OkHttpClient client = new OkHttpClient();
            IamOptions options = new IamOptions.Builder()
                    .apiKey("EIHu-SwxCGE4pW707R2t1yJEfMpadC7e3k-rTKTUUGZV")
                    .build();

            System.out.println("STAGE 1");

            TextToSpeech textToSpeech = new TextToSpeech(options);
            textToSpeech.setEndPoint("https://gateway-syd.watsonplatform.net/text-to-speech/api");
            System.out.println("STAGE 2");

            try
            {
                System.out.println("STAGE 3");
                String loc = path + "hello_world.wav";

                System.out.println("STAGE 3.5");

                OutputStream out = new FileOutputStream(imgFile);
                byte[] buffer = new byte[1024];
                int length;

                System.out.println("STAGE 4");

                SynthesizeOptions synthesizeOptions =
                        new SynthesizeOptions.Builder()
                                .text("hello world")
                                .accept("audio/wav")
                                .voice("en-US_AllisonVoice")
                                .build();

                System.out.println("before synthesize");
                InputStream inputStream = textToSpeech.synthesize(synthesizeOptions).execute();
                System.out.println("after synthesize");
                InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

                System.out.println("Before while");

                while ((length = in.read(buffer)) > 0)
                {
                    System.out.println(buffer);
                    out.write(buffer, 0, length);
                }


                System.out.print("After while");

                out.close();
                in.close();
                inputStream.close();
                System.out.println("done");
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            return "";

        }
    }





    //ASYNCTASK

    class taskclass extends AsyncTask<Void, Void, String>
    {
        protected void onPreExecute() {
        t.setText("");
    }

//    public static String getRequest()
//    {
//        StringBuffer stringBuffer = new StringBuffer("");
//        BufferedReader bufferedReader = null;
//        try {
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet();
//
//            URI uri = new URI("http://sample.campfirenow.com/rooms.xml");
//            httpGet.setURI(uri);
//            httpGet.addHeader(BasicScheme.authenticate(
//                    new UsernamePasswordCredentials("user", "password"),
//                    HTTP.UTF_8, false));
//
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            InputStream inputStream = httpResponse.getEntity().getContent();
//            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String readLine = bufferedReader.readLine();
//            while (readLine != null) {
//                stringBuffer.append(readLine);
//                stringBuffer.append("\n");
//                readLine = bufferedReader.readLine();
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    // TODO: handle exception
//                }
//            }
//        }
//        return stringBuffer.toString();
//    }


    protected String doInBackground(Void... urls)
    {
        String email = emai.getText().toString();
        // Do some validation here

        try {
            URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }

            finally
            {
                urlConnection.disconnect();
            }
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response)
    {

        if (response == null)
        {
            response = "THERE WAS AN ERROR";
        }

        Log.i("INFO", response);
        t.setText(response);
    }
}

}


