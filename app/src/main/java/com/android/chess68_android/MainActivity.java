package com.android.chess68_android;

import android.media.Image;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private static final String CLICKEVENT = "CLICK_EVENT";
    private TableLayout ChessBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final BoardManager ChessManager =  BoardManager.getInstance(this);
            ChessBoard = (TableLayout) findViewById(R.id.Board);
            for(int i =0;i<8;i++)
            {
                View temp = ChessBoard.getChildAt(i);
                if(temp instanceof TableRow)
                {
//                    temp.setClickable(true);

                    for(int j =0;j<8;j++)
                    {
                        ImageView imageTemp =(ImageView) (( (TableRow)temp).getChildAt(j));
                        if( imageTemp instanceof ImageView)
                        {
                            imageTemp.setClickable(true);
                          final int x = i ;
                           final int y   = j;

//                            Log.d("WTF??", imageTemp.toString());
                            System.out.println("setting up the listner for ImageTemp:");
                            imageTemp.setOnClickListener(
                                    new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Piece CurrentPiece = ChessManager.Board[x][y];
                                    if(CurrentPiece != null)
                                    {
//                                        Point[] CurrentLegalMoves = ChessManager.getLegalMoves(ChessManager.Board[x][y]);
                                        ChessManager.signalLegalCells(CurrentPiece);

                                    }
                                }
                            });
                        }
                    }
                }Log.d("anton",  i+"th piece" + ChessBoard.getChildAt(i).toString() );

        }
            ImageView myImage = (ImageView) ((TableRow)ChessBoard.getChildAt(0)).getChildAt(0);
            myImage.setImageResource(R.drawable.bishop);
        System.out.println("caching pieces!");
        ChessManager.cacheLegalMoves(Color.White);
        ChessManager.cacheLegalMoves(Color.Black);

    }
}
