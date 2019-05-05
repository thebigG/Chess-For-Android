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
public abstract class Piece
{

    protected  Color PieceColor;
    public boolean FirstMove;
    protected Point CurrentPosition;
    
    public abstract boolean move(Piece[][] Board, Point Destination);
    public abstract boolean simulateMove(Piece[][] Board, Point Destination);

    public Piece(Point StartingLocation, Color PieceColor)
    {
      CurrentPosition = StartingLocation;
      this.PieceColor = PieceColor;
      FirstMove  = true;
    }
//    public Piece()
//    {
//    }
     public abstract  String getName();
   
    public Point getCurrentPosition()
    {
      return CurrentPosition;
    }
      public String toString()
     {
         return PieceColor.toString().toLowerCase().charAt(0) + this.getName();
     }
    public void setPosition(int x, int y)
    {
      CurrentPosition.setX(x);
      CurrentPosition.setY(y);
    }
     public void setPosition(Point NewPosition)
    {
      CurrentPosition.setX(NewPosition.getX());
      CurrentPosition.setY(NewPosition.getY());
    }
/*
This function simulates an attack on the king.
@return if the attack was suceccesful; false otherwise
*/
public boolean attackKing(Piece[][] Board, Point KingLocation)
{
  return move(Board, KingLocation);
}
public boolean movePiece(Piece[][] Board, Point Destination)
{
    if(move(Board, Destination))
    {
        FirstMove = false;
        return true;
    }
    return false;
}
//public abstract boolean attackKing(Piece[][] Board, Piece Target);
    

}
// public get

