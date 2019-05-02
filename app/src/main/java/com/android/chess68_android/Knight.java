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
public class Knight  extends Piece
{

     public  String getName()
 {
     return "N";
 }
    
     public Knight(Point StartingLocation, Color StartingColor)
     {
         super(StartingLocation, StartingColor);
     }
 public boolean move(Piece[][] Board, Point Destination)
 {
//    if(Color.White == this.PieceColor)
    {
   if(Destination.getX() == CurrentPosition.getX() + 1)
   {
     if(Destination.getY() == CurrentPosition.getY() - 2)
     {
        return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }
     else if(Destination.getY() == CurrentPosition.getY() + 2)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }

   }
  else  if(Destination.getX() == CurrentPosition.getX() - 1)
   {
     if(Destination.getY() == CurrentPosition.getY() - 2)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }
     else if(Destination.getY() == CurrentPosition.getY() + 2)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }

   }
   if(Destination.getX() == CurrentPosition.getX() + 2)
   {
     if(Destination.getY() == CurrentPosition.getY() - 1)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }
     else if(Destination.getY() == CurrentPosition.getY() + 1)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }

   }
  else  if(Destination.getX() == CurrentPosition.getX() - 2)
   {
     if(Destination.getY() == CurrentPosition.getY() - 1)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }
     else if(Destination.getY() == CurrentPosition.getY() + 1)
     {
       return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
     }

   }
   else if(Destination.getY() == CurrentPosition.getY() - 1)
    {
      if(Destination.getX() == CurrentPosition.getX() - 2)
      {
        return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
      }
      else if(Destination.getX() == CurrentPosition.getX() + 2)
      {
        return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
      }

    }
    else if(Destination.getY() == CurrentPosition.getY() + 1)
     {
       if(Destination.getX() == CurrentPosition.getX() - 2)
       {
         return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
       }
       else if(Destination.getX() == CurrentPosition.getX() + 2)
       {
         return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
       }
      

     }
     else if(Destination.getY() == CurrentPosition.getY() - 2)
      {
        if(Destination.getX() == CurrentPosition.getX() - 1)
        {
          return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
        }
        else if(Destination.getX() == CurrentPosition.getX() + 1)
        {
          return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
        }

      }
      else if(Destination.getY() == CurrentPosition.getY() + 2)
       {
         if(Destination.getX() == CurrentPosition.getX() - 1)
         {
           return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
         }
         else if(Destination.getX() == CurrentPosition.getX() + 1)
         {
           return Board[Destination.getX()][Destination.getY()]== null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
         }

       }
    }
       return false;
 }
}
