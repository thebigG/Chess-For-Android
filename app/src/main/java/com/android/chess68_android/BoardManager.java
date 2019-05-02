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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
private String[] AllLocations = null ; // All of the locations of the board to be pre-loaded before the math begin
private static BoardManager Instance;
private StringBuilder BoardImage; //used for representing the board as a simple, mutable string
public  Piece[][] Board;
public King CurrentKing;
private HashMap<Point, Point[]> CurrentLegalMoves;
public Color CurrentColor = Color.White;
private TableLayout ChessBoard;
 /*
These containers allow us to optimize iteration over all of the pieces, without 
having to iterate over the entire board, which contains 64 empty sqaures--
 four times more than the actual number of pieces.
 Note that the cost of these containers is minimal as they are merely references that point to the actual objects.
*/
public  ArrayList<Piece> BlackContainer; 
 public  ArrayList<Piece> WhiteContainer;
 private Activity ChessActivity;
 private Piece CurrentPiece;
 public static int NotifyColor;
 public static int NotifyColorId;
 public static int DarkColor;
 public static int LightColor;
 private BoardManager(Activity ChessActivity)
 {
     this.ChessActivity = ChessActivity;
     ChessBoard = (TableLayout) ChessActivity.findViewById(R.id.Board);
     Board = new Piece[8][8];
     AllLocations = new String[64];
     BoardImage  = new StringBuilder(128);
     BlackContainer = new ArrayList<>(16);
     WhiteContainer = new ArrayList<>(16);
     CurrentKing = fetchKing(Color.White);
     CurrentLegalMoves = new HashMap<>();
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
 public Point[] getLegalMoves(Piece Source)
 {
     if(Source != null) {
         return CurrentLegalMoves.get(Source.CurrentPosition);
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
             if(row == 0)
             {
                 if(col == 0 || col == 7)
                 {
                     Board[row][col] = new Rook(new Point(row, col), Color.Black); 
                     BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                     BlackContainerCounter++;
                 }
                 else if(col == 1 || col == 6)
                 {
                     Board[row][col] = new Knight(new Point(row, col), Color.Black); 
                     BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                     BlackContainerCounter++;
                 }
                else if(col == 2 || col == 5)
                 {
                     Board[row][col] = new Bishop(new Point(row, col), Color.Black); 
                     BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                     BlackContainerCounter++;
                 }
                else if(col ==  3)
                {
                    Board[row][col] = new Queen(new Point(row, col), Color.Black); 
                    BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                    BlackContainerCounter++;
                }
                  else if(col ==  4)
                {
                    Board[row][col] = new King(new Point(row, col), Color.Black); 
                    BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                    BlackContainerCounter++;
                }
             }
             else if(row == 1)
             {
                 Board[row][col] = new Pawn(new Point(row, col), Color.Black);
                  BlackContainer.add(BlackContainerCounter, Board[row][col]) ;
                 Log.d("row:", ("Black pawn" + " for col: " + col) + "," +"row::" + row);
                  BlackContainerCounter++;
             }
             else if(row == 6)
             {
                 Board[row][col] = new Pawn(new Point(row, col), Color.White);
                  WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                  WhiteContainerCounter++;
             }
             else if(row == 7)
             {
                 if(col == 0 || col == 7)
                 {
                     Board[row][col] = new Rook(new Point(row, col), Color.White);
                     WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                     
                 }
                 else if(col == 1 || col == 6)
                 {
                     Board[row][col] = new Knight(new Point(row, col), Color.White); 
                     WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                 }
                else if(col == 2 || col == 5)
                 {
                     Board[row][col] = new Bishop(new Point(row, col), Color.White); 
                     WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                 }
                else if(col ==  3)
                {
                    Board[row][col] = new Queen(new Point(row, col), Color.White); 
                    WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
                }
                  else if(col ==  4)
                {
                    Board[row][col] = new King(new Point(row, col), Color.White);
                    WhiteContainer.add(WhiteContainerCounter, Board[row][col]) ;
                     WhiteContainerCounter++;
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
         return;
     }
   
     CurrentColor = Color.Black;
     CurrentKing = fetchKing(CurrentColor);
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
 public boolean makeMove(String CurrentLocation, String Destination, String Promotion)
 {
     if(!checkBounds(CurrentLocation) || !checkBounds(Destination))
     {
//         System.out.println("out of bounds");
         return false;
     }
     
     Piece CurrentPiece = fetchPiece(CurrentLocation);
     Piece DestinationPiece = fetchPiece(Destination);
     
     if(CurrentPiece != null)
     {
         if(Promotion != null && !CurrentPiece.getName().equalsIgnoreCase("p"))
     {
         return false;
     }
//         System.out.println("null piece ");
         if(CurrentPiece.PieceColor == CurrentColor &&(DestinationPiece == null || DestinationPiece.PieceColor != CurrentColor))
         {
             Point DestinationPoint = parseLocation(Destination);
             if( !(CurrentPiece.movePiece(Board,DestinationPoint)))
             {
//                 System.out.println("IS this running 0?");
                 return false;
             }
//             Point oldPoint  = new Point(CurrentPiece.CurrentPosition);
             Point tempCurrentPosition = new Point(CurrentPiece.CurrentPosition);
             updatePiecePosition(tempCurrentPosition, DestinationPoint);
             if( isInCheck(fetchKing(CurrentColor)))
             {
                 
//                 System.out.println(CurrentColor  + "is in check");
                 updatePiecePosition(DestinationPoint, tempCurrentPosition);
                 Board[DestinationPoint.getX()][DestinationPoint.getY()] = DestinationPiece;
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
//                 System.out.println("This piece is in check:" + CurrentPiece );
                 return false;
                 
             }
             
             
             if(CurrentColor == Color.Black)
             {
                 if(CurrentPiece.CurrentPosition.getX() == 7 && CurrentPiece.getName().equals("p"))
                 {
                     Piece oldPawn = Board[CurrentPiece.CurrentPosition.getX()][CurrentPiece.CurrentPosition.getY()];
                     Board[CurrentPiece.CurrentPosition.getX()][CurrentPiece.CurrentPosition.getY()]=  ((Pawn) CurrentPiece).Promote(Promotion);
                     BlackContainer.remove(oldPawn);
                     BlackContainer.add( Board[CurrentPiece.CurrentPosition.getX()][CurrentPiece.CurrentPosition.getY()]);
                 }
                 setKingCheckState(Color.White);
             }
             else
             {
                  if(CurrentPiece.CurrentPosition.getX() == 0 && CurrentPiece.getName().equals("p"))
                 {
                     Piece oldPawn = Board[CurrentPiece.CurrentPosition.getX()][CurrentPiece.CurrentPosition.getY()];
                     Board[CurrentPiece.CurrentPosition.getX()][CurrentPiece.CurrentPosition.getY()]=  ((Pawn) CurrentPiece).Promote(Promotion);
                     WhiteContainer.remove(oldPawn);
                     WhiteContainer.add(Board[CurrentPiece.CurrentPosition.getX()][CurrentPiece.CurrentPosition.getY()]);
                 }
                 setKingCheckState(Color.Black);
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
     Board[newPosition.getX()][newPosition.getY()].setPosition(newPosition);
     if(tempDestinationPiece != null)
     {
         if(tempDestinationPiece.PieceColor == Color.Black)
         {
             BlackContainer.remove(tempDestinationPiece);
         }
         else if(tempDestinationPiece.PieceColor == Color.White)
         {
             WhiteContainer.remove(tempDestinationPiece);

         }
     }
    }
 }
 /**
  * Pre-load into memory ALL of the positions of the board in the form of a string array.
  * This will make it faster and easier to check checkmate and stalemate
  */
 public void preloadLocations()
 {
     String[] cols = col_label.split("  ");
     int counter = 0;
     for(int i  = 0;i<cols.length;i++)
     {
         for(int j = 0;j<row_label.length;j++)
         {
             AllLocations[counter] = cols[i] + row_label[j];
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
         for(Piece p: BlackContainer)
         {
             CurrentLegalMoves.put(p.CurrentPosition, computeLegalMoves(p));
         }
     }
     else
         {
             for(Piece p: WhiteContainer)
             {
                 CurrentLegalMoves.put(p.CurrentPosition, computeLegalMoves(p));
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
             if(Source.move(Board,temp ))
             {
                 LegalMoves[MoveIndex] = temp;
                 MoveIndex++;
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
         if(source.equalsIgnoreCase(AllLocations[i]))
         {
             continue;
         }
         if(makeMove(source, AllLocations[i], null))
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
        for(Piece p: BlackContainer)
        {
            if(attemptEveryMove(p))
            {
                return false;
            }
        }
    }
    else
    {
     for(Piece p: WhiteContainer)
        {
            if(attemptEveryMove(p))
            {
                return false;
            }
        }   
    }
    return true;
    
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
   public void signalLegalCells(Piece SignalPiece)
   {

       unsignalCells();
       for(Point p: getLegalMoves(SignalPiece))
       {
           ((ImageView) ((TableRow)ChessBoard.getChildAt(p.getX())).getChildAt(p.getY())).setBackgroundColor(NotifyColor);
       }
       CurrentPiece = SignalPiece;

   }

   public ImageView getCell(Point Position)
   {
       System.out.println("getting this cell;" + Position);
       return (ImageView) ((TableRow)ChessBoard.getChildAt(Position.getX())).getChildAt(Position.getY());
   }

   public void unsignalCells()
   {
       if(CurrentPiece != null)
       {
           Point[] LegalPoints = getLegalMoves(CurrentPiece);
           for(Point p : LegalPoints)
           {
               int neighborX = 0;
               int neighborY = 0;
               Point NeighborLocation = null;
               int NeighborColor = 0;
               for(int i =1;i<8;i++)
               {
                   System.out.println("Current index:" + i);
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
                   System.out.println("Square Neighbor location:" + NeighborLocation);
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
   }
    
}


