/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.android.chess68_android;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Element;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import javax.xml.transform.Source;

/**
 *
 * @author lorenzogomez
 * This class manages the board visually and logically--that includes providing method for checking  checkmate and 
 * testing the boundaries of a location.
 * Note that this class is a singleton class-there is ONLY ONE instance for it in the lifetime of the game.
 */

public class BoardManager 
{

private  final String[] row_label = {"8","7","6","5", "4", "3", "2", "1"};
private  final String col_label = "a  b  c  d  e  f  g  h";

private Point[] AllLocations = null ; // All of the locations of the board to be pre-loaded before the math begin
private static BoardManager Instance;
private StringBuilder BoardImage; //used for representing the board as a simple, mutable string
public  Piece[][] Board;
public King CurrentKing;
private HashMap<Point, Point[]> CurrentLegalMovesForBlack;
private HashMap<Point, Point[]> CurrentLegalMovesForWhite;
public Color CurrentColor = Color.White;
public TableLayout ChessBoard;
public Button  UndoButton;
public TextView CurrentPlayerPrompt;
public Piece LastAttackedPiece; //undo variable
public Point LastDestinationPlayed; //undo variable
public Piece LastPlayedPiece;
public Point LastSourcePositionPlayed;
public int LastImageViewTag;
public TextView InCheckView;
public Button RandomButton;
public Button ResignButton;
public Button DrawButton;
public boolean undoAble = false;
 /*
These containers allow us to optimize iteration over all of the pieces, without 
having to iterate over the entire board, which contains 64 empty sqaures--
 four times more than the actual number of pieces.
 Note that the cost of these containers is minimal as they are merely references that point to the actual objects.
*/
public  ArrayList<Piece> BlackContainer;
 public  ArrayList<Piece> WhiteContainer;
 private Activity ChessActivity;
 public Piece CurrentSelectedPiece; //Piece that is currently selected by current player
 public static int NotifyColor;
 public static int DarkColor;
 public static int LightColor;
 public static int BlackBishop = R.drawable.bishop;
 public static int BlackRook = R.drawable.rook;
 public static int BlackQueen = R.drawable.queen;
 public static int BlackKing = R.drawable.king;
 public static int BlackPawn = R.drawable.pawn;
 public static int BlackKnight = R.drawable.knight;
 public static int WhiteBishop = R.drawable.wbishop;
    public static int WhiteRook = R.drawable.wrook;
    public static int WhiteQueen = R.drawable.wqueen;
    public static int WhiteKing = R.drawable.wking;
    public static int WhitePawn = R.drawable.wpawn;
    public static int WhiteKnight = R.drawable.wknight;
 private BoardManager(Activity ChessActivity)
 {
     this.ChessActivity = ChessActivity;
     UndoButton = this.ChessActivity.findViewById(R.id.undo_button);
     InCheckView = this.ChessActivity.findViewById(R.id.InCheckView);
     DrawButton = this.ChessActivity.findViewById(R.id.Draw_Button);
     ResignButton = this.ChessActivity.findViewById(R.id.Resign_Button)
     RandomButton = this.ChessActivity.findViewById(R.id.Random_Button);
     CurrentPlayerPrompt = (TextView)ChessActivity.findViewById(R.id.CurrentPlayer);
     ChessBoard = (TableLayout) ChessActivity.findViewById(R.id.Board);
     Board = new Piece[8][8];
     AllLocations = new Point[64];
     BoardImage  = new StringBuilder(128);
     BlackContainer = new ArrayList<>(16);
     WhiteContainer = new ArrayList<>(16);
     CurrentKing = fetchKing(Color.White);
     CurrentLegalMovesForWhite = new HashMap<>();
     CurrentLegalMovesForBlack = new HashMap<>();
     NotifyColor =  ChessActivity.getResources().getColor(R.color.yellow, null);
     DarkColor =  ChessActivity.getResources().getColor(R.color.saddle_brown, null);;
     LightColor =  ChessActivity.getResources().getColor(R.color.sandy_brown, null);
//     NotifyColorId = ChessActivity.getResources().;
     generateBoard();
 }

 public static BoardManager getInstance(Activity ChessActivity)
 {
     if(Instance == null)
     {
         Instance  = new BoardManager(ChessActivity);
     }
     return Instance;
 }
    public static BoardManager getInstance()
    {
        return Instance;
    }
 public Point[] getLegalMoves(Piece Source)
 {
     if(Source != null)
     {
//         System.out.println("Is this running??");
         if(Source.PieceColor == Color.White) {
//             System.out.println();
             return CurrentLegalMovesForWhite.get(Source.CurrentPosition);
         }
         else
             {
             return CurrentLegalMovesForBlack.get(Source.CurrentPosition);
         }
     }
     return null;
 }

 /**
  * Generates an initial board to start the game with. This function also sets up the containers.
  */
 public void generateBoard()
 {
     int BlackContainerCounter = 0;
     int WhiteContainerCounter = 0;
     for(int row = 0;row<Board.length;row++)
     {
         for(int col = 0;col<Board[row].length;col++)
         {
             ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(0);
             if(row == 0)
             {
                 if(col == 0 || col == 7)
                 {
                     Board[row][col] = new Rook(new Point(row, col), Color.Black); 
                     BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(BlackRook);
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(BlackRook);
                     
                     BlackContainerCounter++;
                 }
                 else if(col == 1 || col == 6)
                 {
                     Board[row][col] = new Knight(new Point(row, col), Color.Black); 
                     BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(BlackKnight);
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(BlackKnight);
                     BlackContainerCounter++;
                 }
                else if(col == 2 || col == 5)
                 {
                     Board[row][col] = new Bishop(new Point(row, col), Color.Black); 
                     BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                     BlackContainerCounter++;
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(BlackBishop);
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(BlackBishop);
                 }
                else if(col ==  3)
                {
                    Board[row][col] = new Queen(new Point(row, col), Color.Black); 
                    BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                    BlackContainerCounter++;
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(BlackQueen);
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(BlackQueen);
                }
                  else if(col ==  4)
                {
                    Board[row][col] = new King(new Point(row, col), Color.Black); 
                    BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                    BlackContainerCounter++;
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(BlackKing);
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(BlackKing);
                }
             }
             else if(row == 1)
             {
                 Board[row][col] = new Pawn(new Point(row, col), Color.Black);
                  BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                 Log.d("row:", ("Black pawn" + " for col: " + col) + "," +"row::" + row);
                  BlackContainerCounter++;
                 ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(BlackPawn);
                 ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(BlackPawn);
             }
             else if(row == 6)
             {
                 Board[row][col] = new Pawn(new Point(row, col), Color.White);
                  WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                  WhiteContainerCounter++;
                 ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(WhitePawn);
                 ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(WhitePawn);

             }
             else if(row == 7)
             {
                 if(col == 0 || col == 7)
                 {
                     Board[row][col] = new Rook(new Point(row, col), Color.White);
                     WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(WhiteRook);
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(WhiteRook);


                 }
                 else if(col == 1 || col == 6)
                 {
                     Board[row][col] = new Knight(new Point(row, col), Color.White); 
                     WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(WhiteKnight);
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(WhiteKnight);

                 }
                else if(col == 2 || col == 5)
                 {
                     Board[row][col] = new Bishop(new Point(row, col), Color.White); 
                     WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(WhiteBishop);
                     ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(WhiteBishop);
                 }
                else if(col ==  3)
                {
                    Board[row][col] = new Queen(new Point(row, col), Color.White); 
                    WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(WhiteQueen);
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(WhiteQueen);

                }
                  else if(col ==  4)
                {
                    Board[row][col] = new King(new Point(row, col), Color.White);
                    WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setTag(WhiteKing);
                    ((ImageView) ((TableRow)ChessBoard.getChildAt(row)).getChildAt(col)).setImageResource(WhiteKing);
                }
             }
         }
     }
  
  
 }
 public String toString()
 {
     return Render();
 }
 public void switchCurrentColor()
 {
     if(CurrentColor == Color.Black)
     {
         CurrentColor = Color.White;
              CurrentKing = fetchKing(CurrentColor);
         updateCurrentPlayerPrompt();
         cacheLegalMoves(Color.White);
         updateCheckStatus();
         return;
     }
   
     CurrentColor = Color.Black;
     CurrentKing = fetchKing(CurrentColor);
     updateCheckStatus();
     updateCurrentPlayerPrompt();
     cacheLegalMoves(Color.Black);
 }
 public void makeRandomMove()
 {
     CurrentSelectedPiece = null;
     int RandomKeyIndex = 0;
     int RandomDestinationIndex = 0;
     Random randomTemp = new Random();
     if(CurrentColor == Color.Black)
     {
         Point[] LegalPiecePositions = null;
         Point[] LegalDestinations = null;
         do
         {
             LegalPiecePositions = new Point[CurrentLegalMovesForBlack.keySet().toArray().length];
             LegalPiecePositions = CurrentLegalMovesForBlack.keySet().toArray(LegalPiecePositions);
             Point LegalTempPositoin = LegalPiecePositions[randomTemp.nextInt(LegalPiecePositions.length)];
             CurrentSelectedPiece = Board[LegalTempPositoin.getX()][LegalTempPositoin.getY()];
             System.out.println("Random selected piece:" + CurrentSelectedPiece);
             LegalDestinations = CurrentLegalMovesForBlack.get(CurrentSelectedPiece.CurrentPosition);
         }while(LegalDestinations.length <= 0);
         makeRealMove(LegalDestinations[randomTemp.nextInt(LegalDestinations.length)]);
     }
     else {
//             CurrentLegalMovesForWhite.keySet().toArray()
         Point[] LegalPiecePositions = null;
         Point[] LegalDestinations = null;
         do
         {
             LegalPiecePositions = new Point[CurrentLegalMovesForWhite.keySet().toArray().length];
         LegalPiecePositions = CurrentLegalMovesForWhite.keySet().toArray(LegalPiecePositions);
         Point LegalTempPositoin = LegalPiecePositions[randomTemp.nextInt(LegalPiecePositions.length)];
         CurrentSelectedPiece = Board[LegalTempPositoin.getX()][LegalTempPositoin.getY()];
         System.out.println("Random selected piece:" + CurrentSelectedPiece);
         LegalDestinations = CurrentLegalMovesForWhite.get(CurrentSelectedPiece.CurrentPosition);
     }while(LegalDestinations.length <= 0);
             makeRealMove(LegalDestinations[randomTemp.nextInt(LegalDestinations.length)]);
         }
 }
public void updateCheckStatus()
{
    InCheckView.setText(ChessActivity.getResources().getString(R.string.CurrentCheck));
    if(CurrentKing.inCheck)
    {

        InCheckView.setText(InCheckView.getText() + CurrentKing.PieceColor.toString());
    }
    else
        {
            InCheckView.setText(InCheckView.getText() + ChessActivity.getResources().getString(R.string.Nobody));

        }
}
 public boolean checkBounds(String Location)
 {
//     System.out.println("length of location:" + Location.length());
         if(Location.length()!= 2)
    {
//        System.out.println("checkBounds #1");
        return false;
    }
//         System.out.prin
//      System.out.println("checking bounds on " +'1'+ Location.charAt(0) + ":"  + ((int) Location.charAt(0)) + "and " +  Location.charAt(1)+ ":" + (int) Location.charAt(1)); 
     if(((int) Location.charAt(0)) < 97 || ((int) Location.charAt(0) > 104))
     {
//         System.out.println("checkBounds #2");
         return false;
     }
      else if(((int) Location.charAt(1)) < 49 || ((int) Location.charAt(1) > 56))
     {
//         System.out.println("checkBounds #3 " );
         return false;
     }
   
     int bound = Math.abs(((int) Location.charAt(1) % 48) - 8);
     if(bound>7 || bound<0)
     {
//         System.out.println("checkBounds #4");
         return false;
     }
     bound = (int) Location.charAt(0) % 97;
     return (bound<7 || bound>0);
 }
 public Piece getChessPiece(Point target)
 {
     return Board[target.getX()][target.getY()];
 }
 public boolean makeMove(Point DestinationLocation, String Promotion)
 {

     
//     Piece CurrentSelectedPiece = fetchPiece(CurrentLocation);
     Piece DestinationPiece = getChessPiece(DestinationLocation);
     
     if(CurrentSelectedPiece != null)
     {
         if(Promotion != null && !CurrentSelectedPiece.getName().equalsIgnoreCase("p"))
     {
         return false;
     }
//         System.out.println("null piece ");
         if(CurrentSelectedPiece.PieceColor == CurrentColor &&(DestinationPiece == null || DestinationPiece.PieceColor != CurrentColor))
         {
//             Point DestinationPoint = parseLocation(Destination);
             if( !(CurrentSelectedPiece.movePiece(Board,DestinationLocation)))
             {
//                 System.out.println("IS this running 0?");
                 return false;
             }
//             Point oldPoint  = new Point(CurrentSelectedPiece.CurrentPosition);
             Point tempCurrentPosition = new Point(CurrentSelectedPiece.CurrentPosition);
             updatePiecePosition(tempCurrentPosition, DestinationLocation);
             if( isInCheck(fetchKing(CurrentColor)))
             {
                 if(CurrentSelectedPiece.getName().equalsIgnoreCase("p"))
                 {
                     //handle empassant
                 }
//                 System.out.println(CurrentColor  + "is in check");
                 updatePiecePosition(DestinationLocation, tempCurrentPosition);
                 Board[DestinationLocation.getX()][DestinationLocation.getY()] = DestinationPiece;
                 if(DestinationPiece != null)
             {
             if(CurrentColor == Color.Black)
             {
                  WhiteContainer.add(DestinationPiece);
             }
             else if(CurrentColor == Color.White)
             {
                 BlackContainer.add(DestinationPiece);
             }
             }
//                 System.out.println("This piece is in check:" + CurrentSelectedPiece );
                 return false;
                 
             }
             
             
             if(CurrentColor == Color.Black)
             {
                 if(CurrentSelectedPiece.CurrentPosition.getX() == 7 && CurrentSelectedPiece.getName().equals("p"))
                 {
                     Piece oldPawn = Board[CurrentSelectedPiece.CurrentPosition.getX()][CurrentSelectedPiece.CurrentPosition.getY()];
                     Board[CurrentSelectedPiece.CurrentPosition.getX()][CurrentSelectedPiece.CurrentPosition.getY()]=  ((Pawn) CurrentSelectedPiece).Promote(Promotion);
                     BlackContainer.remove(oldPawn);
                     BlackContainer.add( Board[CurrentSelectedPiece.CurrentPosition.getX()][CurrentSelectedPiece.CurrentPosition.getY()]);
                 }
                 setKingCheckState(Color.White);
             }
             else
             {
                  if(CurrentSelectedPiece.CurrentPosition.getX() == 0 && CurrentSelectedPiece.getName().equals("p"))
                 {
                     Piece oldPawn = Board[CurrentSelectedPiece.CurrentPosition.getX()][CurrentSelectedPiece.CurrentPosition.getY()];
                     Board[CurrentSelectedPiece.CurrentPosition.getX()][CurrentSelectedPiece.CurrentPosition.getY()]=  ((Pawn) CurrentSelectedPiece).Promote(Promotion);
                     WhiteContainer.remove(oldPawn);
                     WhiteContainer.add(Board[CurrentSelectedPiece.CurrentPosition.getX()][CurrentSelectedPiece.CurrentPosition.getY()]);
                 }
                 setKingCheckState(Color.Black);
             }
             return true;
         }
     }
     return false;
 
 }
 public void undoMove()
 {

     if(LastPlayedPiece.moveCounter == 1)
     {
         LastPlayedPiece.FirstMove = true;
         LastPlayedPiece.moveCounter--;
     }
     System.out.println("UndoMove: LastPlayedPiece:" + LastPlayedPiece);
     System.out.println("UndoMove: LastSourcePosition:" + LastSourcePositionPlayed);
     System.out.println("UndoMove: LastAttackedPiece:" + LastAttackedPiece);
     Board[LastSourcePositionPlayed.getX()][LastSourcePositionPlayed.getY()] = LastPlayedPiece;
     LastPlayedPiece.setPosition(LastSourcePositionPlayed);
     Board[LastDestinationPlayed.getX()][LastDestinationPlayed.getY()] = LastAttackedPiece;
     setImageCell(LastSourcePositionPlayed, (int)getCell(LastDestinationPlayed).getTag());
     setImageCell(LastDestinationPlayed, 0);
     if(LastAttackedPiece != null)
     {
         LastAttackedPiece.setPosition(LastDestinationPlayed);
         setImageCell(LastAttackedPiece.CurrentPosition, LastImageViewTag );
         if(CurrentColor == Color.Black)
         {
             BlackContainer.add(LastAttackedPiece);
         }
         else
             {
                 WhiteContainer.add(LastAttackedPiece);
             }
     }
     if(LastPlayedPiece.getName().equalsIgnoreCase("K"))
     {
         if(LastPlayedPiece.PieceColor == Color.White)
         {
             if(LastPlayedPiece.CurrentPosition.getX() == 7)
             {
                 if(LastSourcePositionPlayed.getY() - LastDestinationPlayed.getY() == 2)
                 {
                     Board[LastPlayedPiece.CurrentPosition.getX()][0] = Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()-1];
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(), 0), (int)getCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()-1 )).getTag());
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()-1 ), 0);
                     Board[LastPlayedPiece.CurrentPosition.getX()][0].setPosition(LastPlayedPiece.CurrentPosition.getX(), 0);
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()-1].FirstMove = true;
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()-1] = null ;
                 }
                 else if(LastSourcePositionPlayed.getY() - LastDestinationPlayed.getY() == -2)
                 {
                     Board[LastPlayedPiece.CurrentPosition.getX()][7] = Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()+1];
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(), 7), (int)getCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()+1 )).getTag());
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()+1 ), 0);
                     Board[LastPlayedPiece.CurrentPosition.getX()][7].setPosition(LastPlayedPiece.CurrentPosition.getX(), 7);
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()+1].FirstMove = true;
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()+1] = null ;
                 }
             }
         }
         else if(LastPlayedPiece.PieceColor == Color.Black)
         {
             if(LastPlayedPiece.CurrentPosition.getX() == 0)
             {
                 if(LastSourcePositionPlayed.getY() - LastDestinationPlayed.getY() == 2)
                 {
                     Board[LastPlayedPiece.CurrentPosition.getX()][0] = Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()-1];
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(), 0), (int)getCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()-1 )).getTag());
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()-1 ), 0);
                     Board[LastPlayedPiece.CurrentPosition.getX()][0].setPosition(LastPlayedPiece.CurrentPosition.getX(), 0);
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()-1].FirstMove = true;
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()-1] = null ;
                 }
                 else if(LastSourcePositionPlayed.getY() - LastDestinationPlayed.getY() == -2)
                 {
                     Board[LastPlayedPiece.CurrentPosition.getX()][7] = Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()+1];
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(), 7), (int)getCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()+1 )).getTag());
                     setImageCell(new Point(LastPlayedPiece.CurrentPosition.getX(),LastPlayedPiece.CurrentPosition.getY()+1 ), 0);
                     Board[LastPlayedPiece.CurrentPosition.getX()][7].setPosition(LastPlayedPiece.CurrentPosition.getX(), 7);
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()+1].FirstMove = true;
                     Board[LastPlayedPiece.CurrentPosition.getX()][LastPlayedPiece.CurrentPosition.getY()+1] = null ;
                 }
             }
         }
     }
     updateUndoAble(false);
     switchCurrentColor();
 }
    public boolean simulateMove(Piece SourcePiece, Point DestinationLocation, String Promotion)
    {

//     Piece CurrentSelectedPiece = fetchPiece(CurrentLocation);
        Piece DestinationPiece = getChessPiece(DestinationLocation);
        if(SourcePiece != null)
        {
            if(Promotion != null && !SourcePiece.getName().equalsIgnoreCase("p"))
            {
                return false;
            }
//         System.out.println("null piece ");
            if((DestinationPiece == null || DestinationPiece.PieceColor != SourcePiece.PieceColor))
            {
//             Point DestinationPoint = parseLocation(Destination);
                if( !(SourcePiece.simulateMove(Board,DestinationLocation)))
                {
//                 System.out.println("IS this running 0?");
//                    System.out.println("simulate move returning false");
                    return false;
                }
//             Point oldPoint  = new Point(CurrentSelectedPiece.CurrentPosition);
                Point tempCurrentPosition = new Point(SourcePiece.CurrentPosition);
                simulateUpdatePiecePosition(tempCurrentPosition, DestinationLocation);
                if( isInCheck(fetchKing(SourcePiece.PieceColor)))
                {
//                 System.out.println(CurrentColor  + "is in check");
                    simulateUpdatePiecePosition(DestinationLocation, tempCurrentPosition);
                    Board[DestinationLocation.getX()][DestinationLocation.getY()] = DestinationPiece;
                    if(DestinationPiece != null)
                    {
                        if(SourcePiece.PieceColor == Color.Black)
                        {
                            WhiteContainer.add(DestinationPiece);
                        }
                        else if(SourcePiece.PieceColor == Color.White)
                        {
                            BlackContainer.add(DestinationPiece);
                        }
                    }
//                 System.out.println("This piece is in check:" + CurrentSelectedPiece );
                    return false;

                }
                simulateUpdatePiecePosition(DestinationLocation, tempCurrentPosition);
                Board[DestinationLocation.getX()][DestinationLocation.getY()] = DestinationPiece;
                if(DestinationPiece != null)
                {
                    if(SourcePiece.PieceColor == Color.Black)
                    {
                        WhiteContainer.add(DestinationPiece);
                    }
                    else if(SourcePiece.PieceColor == Color.White)
                    {
                        BlackContainer.add(DestinationPiece);
                    }
                }
                return true;
            }
        }
        return false;

    }
 /**
  * Move a piece on the board from oldPosition to newPosition
  * @param oldPosition the old position of the piece to be moved, in other words, its current position
  * @param newPosition the new position where the piece at oldPosition will be placed
  */
 public void updatePiecePosition(Point oldPosition, Point newPosition)
 {
    if(!oldPosition.equals(newPosition))
    {
     Piece tempPiece = Board[oldPosition.getX()][oldPosition.getY()];
     Piece tempDestinationPiece = Board[newPosition.getX()][newPosition.getY()];
     Board[oldPosition.getX()][oldPosition.getY()] = null;
     Board[newPosition.getX()][newPosition.getY()] = tempPiece;
     if(tempPiece!=null)
     Board[newPosition.getX()][newPosition.getY()].setPosition(newPosition);
     if(tempDestinationPiece != null)
     {
         if(tempDestinationPiece.PieceColor == Color.Black)
         {
             BlackContainer.remove(tempDestinationPiece);
             LastAttackedPiece = tempDestinationPiece;
//             LastImageView
         }
         else if(tempDestinationPiece.PieceColor == Color.White)
         {
             WhiteContainer.remove(tempDestinationPiece);
             LastAttackedPiece = tempDestinationPiece;

         }
//         System.out.println("Assigned this piece to LastAttacked Piece:" + tempDestinationPiece);
     }
     else
         {
             if(tempPiece != null && !tempPiece.getName().equalsIgnoreCase("p"))
             {
                 LastAttackedPiece = null;
             }
         }


        System.out.println("Assigned this piece to LastAttacked Piece:" + tempDestinationPiece);
    }

 }
    public void simulateUpdatePiecePosition(Point oldPosition, Point newPosition)
    {
        if(!oldPosition.equals(newPosition))
        {
            Piece tempPiece = Board[oldPosition.getX()][oldPosition.getY()];
            Piece tempDestinationPiece = Board[newPosition.getX()][newPosition.getY()];
            Board[oldPosition.getX()][oldPosition.getY()] = null;
            Board[newPosition.getX()][newPosition.getY()] = tempPiece;
            if(tempPiece!=null)
                Board[newPosition.getX()][newPosition.getY()].setPosition(newPosition);
            if(tempDestinationPiece != null)
            {
                if(tempDestinationPiece.PieceColor == Color.Black)
                {
                    BlackContainer.remove(tempDestinationPiece);
//                    LastAttackedPiece = tempDestinationPiece;
//             LastImageView
                }
                else if(tempDestinationPiece.PieceColor == Color.White)
                {
                    WhiteContainer.remove(tempDestinationPiece);
//                    LastAttackedPiece = tempDestinationPiece;

                }
//         System.out.println("Assigned this piece to LastAttacked Piece:" + tempDestinationPiece);
            }



//            System.out.println("Assigned this piece to LastAttacked Piece:" + tempDestinationPiece);
        }

    }
 /**
  * Pre-load into memory ALL of the positions of the board in the form of a string array.
  * This will make it faster and easier to check checkmate and stalemate
  */
 public void preloadLocations()
 {
//     String[] cols = col_label.split("  ");
     int counter = 0;
     for(int i  = 0;i<Board.length;i++)
     {
         for(int j = 0;j<Board.length;j++)
         {
             AllLocations[counter] = new Point(i,j);
//             System.out.println("Board locations: " + Arrays.toString(AllLocations));
             counter++;
         }
     }
//     System.out.println("Board locations: " + Arrays.toString(AllLocations));
 }
 public void cacheLegalMoves(Color PieceColor)
 {
     if(PieceColor == Color.Black)
     {
         CurrentLegalMovesForBlack.clear();
         for(Piece p: BlackContainer)
         {
             CurrentLegalMovesForBlack.put(p.CurrentPosition, computeLegalMoves(p));
         }
     }
     else
         {
             CurrentLegalMovesForWhite.clear();

             for(Piece p: WhiteContainer)
             {
                 CurrentLegalMovesForWhite.put(p.CurrentPosition, computeLegalMoves(p));
             }
         }
 }

 private Point[] computeLegalMoves(Piece Source)
 {
     Point[] LegalMoves  = new Point[32];
     int MoveIndex  =0 ;
     Point temp ;
     for(int i = 0;i<Board.length;i++)
     {
         for(int j =0;j<Board[i].length;j++)
         {
              temp = new Point(i,j);
//             simulateMove(Source,temp, null )
//             Source.move(Board, temp)
             if(!temp.equals(Source.CurrentPosition))
             {
                 if (simulateMove(Source, temp, null)) {
                     LegalMoves[MoveIndex] = temp;
                     MoveIndex++;
                 }
             }
         }
     }
     return Arrays.copyOf(LegalMoves, MoveIndex);
 }
 /**
  * This function attempts every possible move (if any) that the piece can make and returns true if there is a move
  * that can be made legally.
  * @param pieceToMove The piece to attempt to move .
  * @return True if there is a move that can be made, otherwise it returns false.
  */
public boolean attemptEveryMove(Piece pieceToMove)
 {
     String source  = parsePoint(pieceToMove.CurrentPosition);
     for(int i =0;i<AllLocations.length;i++)
     {

//         if(makeMove(source, AllLocations[i], null))
         {
             return true;
         }
     }
     return false;
 }
public boolean checkMate()
{
    if(CurrentColor == Color.Black)
    {
        System.out.println("checkMate test for Black: " + CurrentLegalMovesForBlack.values().isEmpty());
        System.out.println("Value left in the cache for black :" +  CurrentLegalMovesForBlack.values());
        System.out.println("Keyste size for black:" + CurrentLegalMovesForBlack.keySet().size());
        for(Piece p: BlackContainer)
        {
            Point[] temp =  CurrentLegalMovesForBlack.get(p.CurrentPosition);
            {
                System.out.println(String.format("Current legal moves for %s:%s", p.toString(), Arrays.toString(temp)));
                if(temp.length>0)
                    return false;
            }
        }
        return  true;
    }
    else
    {

        System.out.println("checkMate test for White: " + CurrentLegalMovesForWhite.values().isEmpty());
        System.out.println("Keyste size for white:" + CurrentLegalMovesForWhite.keySet().size());
        for(Piece p: WhiteContainer)
        {
            Point[] temp =  CurrentLegalMovesForWhite.get(p.CurrentPosition);
            {
                if(temp.length>0)
                    return false;
                System.out.println(String.format("Current legal moves for %s:%s", p.toString(), Arrays.toString(temp)));
            }
        }
        return true;
    }

}
 public boolean isInCheck(Piece CheckKing)
 {
     if(CheckKing.PieceColor == Color.White)
     {
         for(Piece p: BlackContainer)
         {
             if(p.attackKing(Board, CheckKing.CurrentPosition))
             {
//                 System.out.println(p + "can allegedly attack " + CheckKing);
                 return true;
             }
         }
       
     }
     else if(CheckKing.PieceColor == Color.Black)
     {
         for(Piece p: WhiteContainer)
         {
             if(p.attackKing(Board, CheckKing.CurrentPosition))
             {
                 return true;
             }
         }
       
     }
     return false;  
 }
 /**
  * Checks whether the king indicated by KingColor is in Check or not, and if it is, it sets the inCheck flag
  * in the King class to true. Otherwise, it sets the inCheck flag to false.
  * @param KingColor The color of the king to be checked.
  */
 public void setKingCheckState(Color KingColor )
 {
     King thisKing =  fetchKing(KingColor);
     if(isInCheck(thisKing))
     {
//         System.out.println("setting true for " + thisKing);
         thisKing.inCheck = true;
         return;
     }
     thisKing.inCheck = false;
 }
 public King fetchKing(Color KingColor)
 {
     if(KingColor == Color.Black)
     {
//         System.out.println("");
         for(Piece p: BlackContainer)
         {
             if(p.getName().equals("K"))
             {
//                 System.out.println("returning black king");
                 return (King) p;
             }
         }
     }
     else if(KingColor == Color.White)
     {
         for(Piece p: WhiteContainer)
         {
             if(p.getName().equals("K"))
             {
//                 System.out.println("returning white king");
                 return (King) p;
             }
         }
     }
//     System.out.println("returning null");
     return null;
 }
 public Point parseLocation(String Location)
 {
       if(Location.length()!= 2)
    {
        return null;
    }
     return new Point(Math.abs(((int) Location.charAt(1) % 49) - 7), (int) Location.charAt(0) % 97 );
 }
 public String parsePoint(Point ParsePoint)
 {
     return ""  + ((char) (ParsePoint.getY() + 97))  +  ((char) (ParsePoint.getX() + 56))   ;
 }
 public Piece fetchPiece(String Location)
 {

    if(Location.length()!= 2)
    {
        return null;
    }
//         System.out.println(String.format("coordinates to fetch piece:[%d][%d]", Math.abs(((int) Location.charAt(1) % 49) - 7), (int) Location.charAt(0) %97 ));
     return Board[ Math.abs(((int) Location.charAt(1) % 49) - 7)][((int) Location.charAt(0)) % 97];
 }

 /**
  * Updates the BoardImage with the latest data from the Board.
  * @return 
  */
    public String Render() 
    {
    BoardImage.delete(0, BoardImage.length());
    BoardImage.append("\n");
    for(int row  = 0;row<Board.length;row++)
    {
        for(int col = 0;col<Board[row].length;col++)
        {
            if(Board[row][col] != null)
            {
            BoardImage.append(Board[row][col]);
            }
            else
            {
                if(row % 2 == 0)
            {
                if(col% 2 == 0)
                {
                    BoardImage.append("  ");
                }
                else
                {
                   BoardImage.append( "##") ;
                }
            }
            else            
            {
                if(col% 2 != 0)
                {
                    BoardImage.append("  ") ;
                }
                else
                {
                    BoardImage.append( "##");
                }
            }
                
                
            }
            BoardImage.append(" ");
        }
        
        BoardImage.append(row_label[row]);
        BoardImage.append("\n");
     
    }
    BoardImage.append(col_label);
    BoardImage.append("\n");
    return BoardImage.toString();
    }
   public void broadcastLegalCells(Piece SignalPiece)
   {

       unsignalCells();
       signalLegalCells(SignalPiece);
   }
   public void signalLegalCells(Piece SignalPiece)
   {
//       System.out.println("Signal Piece passed " + SignalPiece);
//       System.out.println("legal moves:" + getLegalMoves(SignalPiece));
       if(getLegalMoves(SignalPiece) != null && SignalPiece.PieceColor == CurrentColor)
       {

           for (Point p : getLegalMoves(SignalPiece))
           {
               ((ImageView) ((TableRow) ChessBoard.getChildAt(p.getX())).getChildAt(p.getY())).setBackgroundColor(NotifyColor);
           }
           CurrentSelectedPiece = SignalPiece;
       }
   }

   public ImageView getCell(Point Position)
   {
//       System.out.println("getting this cell;" + Position);
       return (ImageView) ((TableRow)ChessBoard.getChildAt(Position.getX())).getChildAt(Position.getY());
   }

   public void unsignalCells()
   {
       if(CurrentSelectedPiece != null)
       {
           Point[] LegalPoints = getLegalMoves(CurrentSelectedPiece);
           for(Point p : LegalPoints)
           {
               int neighborX = 0;
               int neighborY = 0;
               Point NeighborLocation = null;
               int NeighborColor = 0;
               for(int i =1;i<8;i++)
               {
//                   System.out.println("Current index:" + i);
                   if(p.getX() - i >=0)
                   {
                       neighborX = p.getX() - i;
                   }
                   else if(p.getX() + i<=7)
                       {
                           neighborX = p.getX() + i;
                       }
                   if(p.getY() - i >=0 )
                   {
                       neighborY = p.getY() - i;
                   }
                   else if(p.getY() + i<=7)
                   {
                       neighborY = p.getY() + i;
                   }
//                    neighborX  = p.getX() - i >=0 ? p.getX() - i:p.getX() +i;
//                    neighborY =  p.getY() - i >=0 ? p.getY() - i:p.getY() +i;
                   NeighborLocation  = new Point(neighborX,neighborY);
                    if(((ColorDrawable)getCell(NeighborLocation).getBackground()).getColor() !=NotifyColor)
                    {
                        NeighborColor = ((ColorDrawable)getCell(NeighborLocation).getBackground()).getColor();
                        break;
                    }

               }
               ImageView LegalCell = getCell(p);
               if(Math.abs(NeighborLocation.getX() - p.getX()) == Math.abs(NeighborLocation.getY() - p.getY()))
               {
//                   System.out.println("Square Neighbor location:" + NeighborLocation);
                   LegalCell.setBackgroundColor(NeighborColor);
               }
               else
                   {
                       if(NeighborColor == DarkColor)
                       {
//                           System.out.println("Setting LightColor to Non-Square Neighbor Location:" + NeighborLocation);
                           LegalCell.setBackgroundColor(LightColor);
                       }
                       else
                           {
//                               System.out.println("Setting DarkColor to Non-Square Neighbor Location:" + NeighborLocation);
                               LegalCell.setBackgroundColor(DarkColor);
                           }
                   }
           }
       }
       CurrentSelectedPiece = null;
   }
    public void setImageCell(Point DestinationCell, int ImageResourceTag)
    {
        getCell(DestinationCell).setImageResource(ImageResourceTag);
        getCell(DestinationCell).setTag(ImageResourceTag);
    }
    public boolean makeRealMove(Point TargetDestination)
    {
        if(!simulateMove(CurrentSelectedPiece, TargetDestination, null))
        {
//            System.out.println("simulateMove is false for :" + CurrentSelectedPiece);
            return false;
        }
        LastImageViewTag =(int) getCell(TargetDestination).getTag();
        setImageCell(TargetDestination, (int)getCell(CurrentSelectedPiece.CurrentPosition ).getTag());
       setImageCell(CurrentSelectedPiece.CurrentPosition, 0);
       if(CurrentSelectedPiece.getName().equalsIgnoreCase("p"))
       {
//           System.out.println("First move for piece:" + CurrentSelectedPiece.FirstMove);
           if(CurrentSelectedPiece.CurrentPosition.getY() != TargetDestination.getY())
           {
            if(Board[TargetDestination.getX()][TargetDestination.getY()] == null)
            {
                if(CurrentSelectedPiece.PieceColor == Color.White)
                {
                    LastImageViewTag = (int)getCell(new Point(TargetDestination.getX() +1, TargetDestination.getY())).getTag();
                    setImageCell(new Point(TargetDestination.getX() +1, TargetDestination.getY()) , 0);
                }
                else
                    {
                        LastImageViewTag = (int)getCell(new Point(TargetDestination.getX() -1, TargetDestination.getY())).getTag();
                        setImageCell(new Point(TargetDestination.getX() -1, TargetDestination.getY()), 0);
                    }
            }
           }
       }
       else if(CurrentSelectedPiece.getName().equalsIgnoreCase("k"))
       {
           int YDifference =  CurrentSelectedPiece.CurrentPosition.getY() - TargetDestination.getY();
           if(YDifference == -2)
           {
               setImageCell(new Point( TargetDestination.getX() ,TargetDestination.getY() -1), (int)getCell( new Point(CurrentSelectedPiece.getCurrentPosition().getX(), CurrentSelectedPiece.getCurrentPosition().getY() + 3 )).getTag());
               setImageCell(new Point(CurrentSelectedPiece.getCurrentPosition().getX(), CurrentSelectedPiece.getCurrentPosition().getY() + 3 ), 0);
           }
           else if(YDifference == 2)
           {
               setImageCell(new Point( TargetDestination.getX() ,TargetDestination.getY() +1), (int)getCell( new Point(CurrentSelectedPiece.getCurrentPosition().getX(), CurrentSelectedPiece.getCurrentPosition().getY() - 4 )).getTag());
               setImageCell(new Point(CurrentSelectedPiece.getCurrentPosition().getX(), CurrentSelectedPiece.getCurrentPosition().getY() - 4 ), 0);
           }
       }
        LastPlayedPiece = CurrentSelectedPiece;
        LastAttackedPiece = null;
        LastDestinationPlayed = new Point(TargetDestination);
        LastSourcePositionPlayed = new Point(CurrentSelectedPiece.CurrentPosition);
        makeMove(TargetDestination, null);
        switchCurrentColor();
        updateUndoAble(true);
        return true;
    }
    public void updateUndoAble(boolean undo)
    {
        undoAble = undo;
       UndoButton.setEnabled(undo);
    }
    public void updateCurrentPlayerPrompt()
    {
        String temp = null;
        if(CurrentColor == Color.Black)
        {
//            temp =
            CurrentPlayerPrompt.setText(ChessActivity.getResources().getString(R.string.CurrentPlayer) + ChessActivity.getResources().getString(R.string.SecondPlayer));
        }
        else
            {
                CurrentPlayerPrompt.setText(ChessActivity.getResources().getString(R.string.CurrentPlayer)  + ChessActivity.getResources().getString(R.string.FirstPlayer));
            }
    }
}



