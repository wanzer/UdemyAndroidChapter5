package ricoapp.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static int countMe=0;
    static int countPc=0;
    int sum;
    TextView result,timerCount,btn1,btn2,btn3,btn4,summury,count;
    Button btn;
    CountDownTimer countDownTimer;
    ConstraintLayout constraintLayout;
    GridLayout grid;
    int arr[];
    public void restart(){
        countMe=0;
        countPc=0;
        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
        result.setVisibility(View.INVISIBLE);
        timerCount.setVisibility(View.INVISIBLE);
        grid.setVisibility(View.INVISIBLE);
        summury.setVisibility(View.INVISIBLE);
        count.setVisibility(View.INVISIBLE);
        btn.setTranslationX(0);
        btn.setText("Try againe !");
    }
    public void showUi(){
        result.setVisibility(View.VISIBLE);
        timerCount.setVisibility(View.VISIBLE);
        grid.setVisibility(View.VISIBLE);
        summury.setVisibility(View.VISIBLE);
        count.setVisibility(View.VISIBLE);
    }
    public void hideBtn(){btn.setTranslationX(1000);}

    public int rand(){
        Random rand=new Random();
        return rand.nextInt(50);
    }
    public void randArr(){
        int one=rand();
        int two=rand();
        sum=one+two;
        arr = new int [4];
        Random ran= new Random();
        int r=ran.nextInt(3)+1;
        arr[r]=sum;
        for (int i=0;i<arr.length;i++){
            if (arr[i]!=sum)
                arr[i]=rand();
        }
        summury.setText(Integer.toString(one)+"+"+Integer.toString(two));
        btn1.setText(Integer.toString(arr[0]));
        btn2.setText(Integer.toString(arr[1]));
        btn3.setText(Integer.toString(arr[2]));
        btn4.setText(Integer.toString(arr[3]));

    }

   public  void timer(){
     countDownTimer= new CountDownTimer(30000+100,1000) {
           @Override
           public void onTick(long  millisecondsUntilDone) {
               timerCount.setText(String.valueOf(millisecondsUntilDone/1000)+"s");
           }

           @Override
           public void onFinish() {
               timerCount.setText("0s");
               if (countMe>countPc)
                   Toast.makeText(MainActivity.this,"YOU WIN !",Toast.LENGTH_LONG).show();
               else if (countMe<countPc)
                   Toast.makeText(MainActivity.this,"YOU LOST !",Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this,"EQUAL SCORE !",Toast.LENGTH_LONG).show();
               restart();
           }
     }.start();
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.btn1:
                    if (arr[0] == sum) {
                        countMe++;
                        Toast.makeText(MainActivity.this, Integer.toString(arr[0]), Toast.LENGTH_SHORT).show();
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Correct");
                    } else {
                        countPc++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Wrong");
                    }
                    break;
                case R.id.btn2:
                    if (arr[1] == sum){
                        countMe++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Correct");
                    }
                    else{
                        countPc++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Wrong");
                    }
                    break;
                case R.id.btn3:
                    if (arr[2] == sum){
                        countMe++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Correct");
                    }
                    else{
                        countPc++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Wrong");
                    }
                    break;
                case R.id.btn4:
                    if (arr[3] == sum){
                        countMe++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Correct");
                    }
                    else{
                        countPc++;
                        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
                        result.setText("Wrong");
                    }
                    break;
            }
        randArr();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result=(TextView)findViewById(R.id.result);
        timerCount=(TextView)findViewById(R.id.timer);
        count=(TextView)findViewById(R.id.count);
        summury=(TextView)findViewById(R.id.sum);
        btn=(Button)findViewById(R.id.go);
        btn1=(TextView) findViewById(R.id.btn1);
        btn2=(TextView) findViewById(R.id.btn2);
        btn3=(TextView) findViewById(R.id.btn3);
        btn4=(TextView) findViewById(R.id.btn4);
        constraintLayout=(ConstraintLayout)findViewById(R.id.conteiner);
        grid=(GridLayout)findViewById(R.id.grid);

               btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timer();
                showUi();
                hideBtn();
            }
        });
        count.setText(Integer.toString(countMe)+"/"+Integer.toString(countPc));
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        randArr();
    }
}
