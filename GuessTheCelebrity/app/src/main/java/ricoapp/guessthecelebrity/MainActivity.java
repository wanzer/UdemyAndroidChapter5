package ricoapp.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btn1,btn2,btn3,btn4;
    Map<String,String> map;
    List<String> list;
    String randPic;
    String randArr[];

    class Celebrity extends AsyncTask<String,Void,String >{

        @Override
        protected String doInBackground(String... params) {
            String result ="";
            URL url;
            HttpURLConnection connection;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader readData = new InputStreamReader(in);
                int dataPointer = readData.read();
                while (dataPointer != -1) {
                    char current=(char)dataPointer;
                    result += current;
                    dataPointer = readData.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();

            }
               return null;
        }
    }
    class Picture extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap mBitmap;
            URL url;
            HttpURLConnection connection;
            try {
                url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                mBitmap= BitmapFactory.decodeStream(in);
                return mBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public int rand(){
        Random rand=new Random();
        return rand.nextInt(list.size());
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn1:
               if(randArr[0].equals(randPic))
                   Toast.makeText(MainActivity.this,"Correct !",Toast.LENGTH_SHORT).show();
                else
                   Toast.makeText(MainActivity.this,"You're wrong. It's "+randPic,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                if (randArr[1].equals(randPic))
                    Toast.makeText(MainActivity.this,"Correct !",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"You're wrong. It's "+randPic,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                if (randArr[2].equals(randPic))
                    Toast.makeText(MainActivity.this,"Correct !",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"You're wrong. It's "+randPic,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn4:
                if(randArr[3].equals(randPic))
                    Toast.makeText(MainActivity.this,"Correct !",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"You're wrong. It's "+randPic,Toast.LENGTH_SHORT).show();
                break;
        }
        addPicture();
    }
    public void addPicture(){

        Picture picture = new Picture();
        randPic=list.get(rand());
        try {
            Bitmap bitmap=picture.execute(map.get(randPic)).get();
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        randArr=new String[4];
        Random rand = new Random();
        int n=rand.nextInt(4);
        randArr[n]=randPic;
        for (int i=0;i<randArr.length;i++){
            if (randArr[i]!=randPic)
                randArr[i]=list.get(rand());
        }
        btn1.setText(randArr[0]);
        btn2.setText(randArr[1]);
        btn3.setText(randArr[2]);
        btn4.setText(randArr[3]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.image);
        String result=null;
        Celebrity task = new Celebrity();
        map = new HashMap();
        list = new ArrayList();
        try {
           result=task.execute("http://www.posh24.se/kandisar").get();
            String resultSplit[]=result.split("<div class=\"sidebarContainer\">");
            Pattern p =Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(resultSplit[0]);
            Pattern p2 =Pattern.compile("alt=\"(.*?)\"");
            Matcher m2 = p2.matcher(resultSplit[0]);
            while (m.find() && m2.find()){
                map.put(m2.group(1),m.group(1));
                list.add(m2.group(1));
            }
            for (Map.Entry entry: map.entrySet())
                System.out.println("Key: "+entry.getKey()+"Value :"+entry.getValue());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        addPicture();
    }
}
