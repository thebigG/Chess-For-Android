package com.android.chess68_android;

import android.media.Image;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;


public class MainActivity extends AppCompatActivity {

    private TableLayout ChessBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            ChessBoard = (TableLayout) findViewById(R.id.Board);
            for(int i =0;i<8;i++)
            {
                View temp = ChessBoard.getChildAt(i);
                if(temp instanceof TableRow)
                {
                    for(int j =0;j<8;j++)
                    {
                        ImageView imageTemp =(ImageView) (( (TableRow)temp).getChildAt(i));
                        if( imageTemp instanceof ImageView)
                        {
                            Log.d("WTF??", imageTemp.toString());
                        }
                    }
                }Log.d("anton",  i+"th piece" + ChessBoard.getChildAt(i).toString() );
        }

        //TableLayout = findViewById()
    }
}
