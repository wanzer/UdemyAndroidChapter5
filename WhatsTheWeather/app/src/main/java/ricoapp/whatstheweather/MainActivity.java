package ricoapp.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button btn;
    String main="";
    String description="";
    String temperature;


    class GetWeather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String result="";
            HttpURLConnection httpURLConnection;
            try {
                url=new URL(params[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data=reader.read();
                while (data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject object = new JSONObject(result);
                String weather = object.getString("weather");
                JSONObject temp=object.getJSONObject("main");
                temperature=temp.getString("temp");
                JSONArray array = new JSONArray(weather);
                for (int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    main=jsonObject.getString("description");
                    description=jsonObject.getString("main");
                }

                textView.setText(description+": " +main+"\r\nTemperatute: "+temperature+"c");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("weather ",result);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.printResult);
        editText =(EditText)findViewById(R.id.city);
        btn =(Button)findViewById(R.id.result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromInputMethod(editText.getWindowToken(),0);

                GetWeather weather = new GetWeather();
                weather.execute("http://api.openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&APPID=73ce2269e89c2bc2dde077e775691a4d&units=metric");
            }
        });
    }
}
