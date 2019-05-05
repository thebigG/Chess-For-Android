/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.android.chess68_android;

/**
 *
 * @author lorenzogomez
 */
public class Bishop extends Piece
{

    /**
     *
     * @return
     */
    public  String getName()
 {
     return "B";
 }
    
    public Bishop(Point StartingLocation, Color PieceColor) 
    {
        super(StartingLocation, PieceColor);
    }
         @Override
 public  boolean move(Piece[][] Board, Point Destination)
 {
//     System.out.println("Bishop move");
     if( Destination.getY() -  CurrentPosition.getY()  ==  Destination.getX() -  CurrentPosition.getX() )
     {
//         System.out.println("moving bishop #1");
         if(Destination.getX()>CurrentPosition.getX())
         {
//             System.out.println("moving bishop #2");
             for(int row  = CurrentPosition.getX() + 1, col = CurrentPosition.getY() + 1;row <=Destination.getX();row++,col++)
             {
                 if(row>=Board.length)
               {
                   return false;
               }
                 if(row<Destination.getX())
               {
                   if(Board[row][col] != null)
                   {
                       return false;
                   }
               }
                 else
               {
                   return Board[row][col]== null || Board[row][col].PieceColor != this.PieceColor;
               }
                 
             }
         }
         else if(CurrentPosition.getX()>Destination.getX())
       {
//           System.out.println("moving bishop #3");
           for(int row = CurrentPosition.getX() -1, col = CurrentPosition.getY()-1;row>=Destination.getX() ;row--, col--)
           {
//               System.out.println("moving bishop #4");
               if(row<0)
               {
                   return false;
               }
               if(row>Destination.getX())
               {
                   if(Board[row][col] != null)
                   {
                       return false;
                   }
               }
               else
               {
                   return Board[row][col]== null || Board[row][col].PieceColor != this.PieceColor;
               }
               
           }
       }
         
     }
     else if(Math.abs(Destination.getY() - CurrentPosition.getY() ) == Math.abs(Destination.getX() - CurrentPosition.getX() ))
     {
     if(Destination.getX()>CurrentPosition.getX())
     {
//         System.out.println("moving bishop #5");
         for(int row  = CurrentPosition.getX() + 1, col = CurrentPosition.getY() -1;row <=Destination.getX();row++,col--)
             {
                 if(row>=Board.length)
               {
                   return false;
               }
                 if(row<Destination.getX())
               {
                   if(Board[row][col] != null)
                   {
                       return false;
                   }
               }
                 else
               {
                   return Board[row][col]== null || Board[row][col].PieceColor != this.PieceColor;
               }
                 
             }
     }
     else if(Destination.getX()<CurrentPosition.getX())
     {
//         System.out.println("moving bishop #6");
         for(int row  = CurrentPosition.getX() -1, col = CurrentPosition.getY() +1;row >=Destination.getX();row--,col++)
             {
                 if(row<0)
               {
                   return false;
               }
                 if(row>Destination.getX())
               {
                   if(Board[row][col] != null)
                   {
                       return false;
                   }
               }
                 else
               {
                   return Board[row][col]== null || Board[row][col].PieceColor != this.PieceColor;
               }
                 
             }
     }
     }
   return false;
 }

    @Override
    public boolean simulateMove(Piece[][] Board, Point Destination) {
        return move(Board, Destination);
    }

}