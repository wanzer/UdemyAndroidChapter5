package ricoapp.timestable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    public void timesTable(int h){
        String str[][] = new String[20][20];
        for (int i = 0; i <str.length; i++) {
            for (int j = 0; j <str[i].length; j++) {
                str[i][j] = Integer.toString((i+1)*(j+1));
            }
        }
        ArrayAdapter<Integer> adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,str[h]);
        listView.setAdapter(adapter);
    }

   static int prog=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prog=seekBar.getProgress();
                timesTable(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

         timesTable(prog);
    }
}

