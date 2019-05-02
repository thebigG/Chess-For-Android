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
public class Rook extends Piece
{

 @Override
 public  String getName()
 {
     return "R";
 }


    public Rook(Point StartingLocation, Color PieceColor) {
        super(StartingLocation, PieceColor);
    }
 public boolean move(Piece[][] Board, Point Destination)
 {
   if(Destination.getX() != CurrentPosition.getX() && Destination.getY() == CurrentPosition.getY())
   {
       if(CurrentPosition.getX()<Destination.getX())
       {
           for(int row = CurrentPosition.getX() + 1;row<=Destination.getX() ;row++)
           {
               if(row>=Board.length)
               {
                   return false;
               }
               if(row<Destination.getX())
               {
                   if(Board[row][Destination.getY()] != null)
                   {
                       return false;
                   }
               }
               else
               {
                   return Board[row][Destination.getY()]== null ||  Board[row][Destination.getY()].PieceColor != this.PieceColor;
               }
               
           }
       }
       else if(CurrentPosition.getX()>Destination.getX())
       {
           for(int row = CurrentPosition.getX() - 1;row>=Destination.getX() ;row--)
           {
               if(row<0)
               {
                   return false;
               }
               if(row>Destination.getX())
               {
                   if(Board[row][Destination.getY()] != null)
                   {
                       return false;
                   }
               }
               else
               {
                   return Board[row][Destination.getY()]== null || Board[row][Destination.getY()].PieceColor != this.PieceColor;
               }
               
           }
       }
     return true;
   }
  else if(Destination.getY() != CurrentPosition.getY() && Destination.getX() == CurrentPosition.getX())
   {
     if(CurrentPosition.getY()<Destination.getY())
       {
           for(int col = CurrentPosition.getY() + 1;col<=Destination.getY() ;col++)
           {
               if(col>=Board[Destination.getX()].length)
               {
                   return false;
               }
               if(col<Destination.getY())
               {
                   if(Board[ Destination.getX()][col] != null)
                   {
                       return false;
                   }
               }
               else
               {
                   return  Board[Destination.getX()][col] == null ||  Board[Destination.getX()][col].PieceColor != this.PieceColor;
               }
               
           }
       }
       else if(CurrentPosition.getY()>Destination.getY())
       {
           for(int col = CurrentPosition.getY() - 1;col>=CurrentPosition.getY() ;col--)
           {
               if(col<0)
               {
                   return false;
               }
               if(col>Destination.getY())
               {
                   if(Board[Destination.getX()][col] != null)
                   {
                       return false;
                   }
               }
               else
               {
                   return  Board[Destination.getX()][col]== null || Board[Destination.getX()][col].PieceColor != this.PieceColor;
               }
               
           }
       }
   }
   return false;
 }
}
