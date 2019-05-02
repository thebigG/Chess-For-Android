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
public class Pawn extends Piece 
{
    
public boolean Empassant;
public Pawn(Point StartingLocation, Color PieceColor)
{
    super(StartingLocation, PieceColor);
    this.PieceColor = PieceColor;
    this.CurrentPosition = StartingLocation;
    Empassant = false;
}



@Override
    public boolean move(Piece[][] Board, Point Destination)
    {
        //check if the destination  is legal
//        System.out.println("checking pawn");
        if(PieceColor ==Color.White)
        {
//            System.out.println("White pawn");
//            System.out.println("Going from " + this.CurrentPosition + "to " + Destination);
        if(Destination.getX()==CurrentPosition.getX() - 1 && Destination.getY() == CurrentPosition.getY())
        {
            if(Board[Destination.getX()][Destination.getY()] == null)
            {
            Empassant = false;
            return true;
            }
            return false;
            
            
//           System.out.println("returning pawn 1");
          
        }
        else if((Destination.getX() == CurrentPosition.getX() - 1 && Destination.getY()== CurrentPosition.getY()+1) || (Destination.getX() == CurrentPosition.getX() -1 ) &&  (Destination.getY()==CurrentPosition.getY() - 1))
        {
           if(Board[Destination.getX()][Destination.getY()] != null )
            {
                if(Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor )
                {
                    Empassant  = false;
                    return true;
                }
                else
                {
                    return false;
                }
             }
            else
            {
                 if(Board[CurrentPosition.getX()][Destination.getY()] != null && Board[CurrentPosition.getX()][Destination.getY()].getName().equalsIgnoreCase("p")  )
                {
                if( ((Pawn)Board[CurrentPosition.getX()][Destination.getY()]).Empassant && Board[CurrentPosition.getX()][Destination.getY()].PieceColor != this.PieceColor  )
                    {
                    //Chess.thisBoard.BlackContainer.remove(Board[CurrentPosition.getX()][Destination.getY()]);
                    Board[CurrentPosition.getX()][Destination.getY()] = null;
                    return true;
                    }
                }
                return false;
            }
        }
        else if(Destination.getX()==CurrentPosition.getX()- 2  && Destination.getY() == CurrentPosition.getY() && FirstMove )
        {
            if(Board[Destination.getX()][Destination.getY()] == null)
            {
                Empassant = true;
                return true;
            }
             return false;
            
//            System.out.println("returning pawn 3");
        }
    }
         else if(PieceColor ==Color.Black)
          {
        if(Destination.getX()==CurrentPosition.getX() + 1 && Destination.getY() == CurrentPosition.getY())
        {
            if(Board[Destination.getX()][Destination.getY()] == null)
            {
                Empassant  = false;
                return true;
            }
            return false;
           
        }
        else if((Destination.getX() == CurrentPosition.getX() + 1 && Destination.getY()== CurrentPosition.getY()+1) || (Destination.getX() == CurrentPosition.getX() +1 ) &&  (Destination.getY()==CurrentPosition.getY() - 1))
        {
            if(Board[Destination.getX()][Destination.getY()] != null )
            {
                if(Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor )
                {
                    Empassant  = false;
                    return true;
                }
                else
                {
                    return false;
                }
             }
            else
            {

                if(Board[CurrentPosition.getX()][Destination.getY()] != null && Board[CurrentPosition.getX()][Destination.getY()].getName().equalsIgnoreCase("p")  )
                {
                if( ((Pawn)Board[CurrentPosition.getX()][Destination.getY()]).Empassant &&  Board[CurrentPosition.getX()][Destination.getY()].PieceColor != this.PieceColor  )
                    {
                  //  Chess.thisBoard.BlackContainer.remove(Board[CurrentPosition.getX()][Destination.getY()]);
                    Board[CurrentPosition.getX()][Destination.getY()] = null;
                    return true;
                    }
                }
            }
        }
            
//            System.out.println("returning pawn 2");
//            System.out.println("Black pawn");
        
        else if(Destination.getX()==CurrentPosition.getX()+ 2  && Destination.getY() == CurrentPosition.getY() && FirstMove )
        {
            if(Board[Destination.getX()][Destination.getY()] == null)
            {
                Empassant = true;
                return true;
            }
             return false;
        }
      }
         
//        System.out.print("Returning false from pawn");
       return false;
    }
         public  String getName()
 {
     return "p";
 }

  
public  Piece Promote(String PieceName)
{   
    if(PieceName == null)
    {
        return new Queen(CurrentPosition, PieceColor);
    }
    if(PieceName.equalsIgnoreCase("N"))
    {
        return  new Knight(CurrentPosition, PieceColor) ;
    }
      else if(PieceName.equalsIgnoreCase("R"))
    {
        return  new Rook(CurrentPosition, PieceColor) ;

    }
      else if(PieceName.equalsIgnoreCase("B"))
    {
        return  new Bishop(CurrentPosition, PieceColor) ;

    }
    return new Queen(CurrentPosition, PieceColor);
}

//public void setLocationpublic
/**
 * public static void move(Piece piece)
 * {
 *
 * }
 */


}
