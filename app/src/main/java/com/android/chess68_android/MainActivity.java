package com.android.chess68_android;

import android.media.Image;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
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
    public static final String STATE  = "BOARDSTATE";
    public static final String ISSUE  = "LOGIC_ISSUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final BoardManager ChessManager =  BoardManager.getInstance(this);
        BoardManager.getInstance().updateCurrentPlayerPrompt();
        for(int i =0;i<8;i++)
            {
                View temp = BoardManager.getInstance().ChessBoard.getChildAt(i);
                if(temp instanceof TableRow)
                {
//                    temp.setClickable(true);
                    BoardManager.getInstance().updateCurrentPlayerPrompt();
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
                                    BoardManager.getInstance().updateCurrentPlayerPrompt();
                                    Log.d(STATE,
                                             "\n"+ChessManager.toString() + "\n " +"Current Color:" + ChessManager.CurrentColor);
                                    Piece TargetPiece = ChessManager.Board[x][y];
                                    Point Destination = new Point(x, y);

                                    if(TargetPiece == null)
                                    {
                                        if (ChessManager.CurrentSelectedPiece != null)
                                        {
//                                            Log.d("First move for piece:" + ChessManager.CurrentSelectedPiece.FirstMove);
                                            System.out.println("Empassant? outside of simulate???");

                                            if(ChessManager.makeRealMove(Destination))
                                            {
                                             //TODO: Write this code in a function. Also remember to check for empassant and castiling inisde that function.
                                                System.out.println("Empassant????");
//                                                Log.d(STATE, "Simulate move true for "  + ChessManager.CurrentSelectedPiece);
//                                                ChessManager.setImageCell(Destination, (int)ChessManager.getCell(ChessManager.CurrentSelectedPiece.CurrentPosition ).getTag());
//                                                ChessManager.setImageCell(ChessManager.CurrentSelectedPiece.CurrentPosition, 0);
//                                                Log.d(STATE ,"Real move for "+  ChessManager.CurrentSelectedPiece+ ":"  +ChessManager.makeMove(Destination, null));
//                                                ChessManager.switchCurrentColor();
//                                                ChessManager.makeRealMove(Destination);
                                            }
                                            else
                                                {
                                                    //Are you in check, stalemate?
                                                    Log.d(STATE, "Simulate move false for " + ChessManager.CurrentSelectedPiece);

                                                }

                                        }
                                        ChessManager.broadcastLegalCells(TargetPiece);
                                    }
                                    else
                                        {
                                            if (ChessManager.CurrentSelectedPiece != null)
                                            {
                                                System.out.println("First move for piece:" + ChessManager.CurrentSelectedPiece.FirstMove);

                                                if (TargetPiece.PieceColor == ChessManager.CurrentColor)
                                                {
//                                                    Log.d(STATE, "Is this where the problem is???");
                                                    ChessManager.broadcastLegalCells(TargetPiece);
                                                }
                                                else
                                                    {
                                                        if(ChessManager.makeRealMove(Destination))
                                                        {
                                                            System.out.println("Attacking piece!");
                                                        }
                                                    }
                                                ChessManager.broadcastLegalCells(TargetPiece);
                                            }
                                            else
                                                {
                                                    if(TargetPiece.PieceColor == ChessManager.CurrentColor)
                                                    {
//                                                        System.out.println("Broadcasting legal cells");
                                                        ChessManager.broadcastLegalCells(TargetPiece);
                                                    }
                                                }
                                    }
                                }
                            });
                        }
                    }
                }
//                Log.d("anton",  i+"th piece" + ChessBoard.getChildAt(i).toString() );

        }
        BoardManager.getInstance().UndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(BoardManager.getInstance().LastPlayedPiece!= null)
                {
                    BoardManager.getInstance().undoMove();
                }
            }
        });

        System.out.println("caching pieces!");
        ChessManager.cacheLegalMoves(Color.White);


    }
}
