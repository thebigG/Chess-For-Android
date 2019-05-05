/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.android.chess68_android;

import android.util.Log;

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
            Log.d(MainActivity.STATE, "White pawn");
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
                System.out.println("IS this pawn ELSE test running?");
                System.out.println("PIece in question:" + Board[CurrentPosition.getX()][Destination.getY()]  );
                System.out.println("First test -->" + Board[CurrentPosition.getX()][Destination.getY()] != null);
                if(Board[CurrentPosition.getX()][Destination.getY()] != null && Board[CurrentPosition.getX()][Destination.getY()].getName().equalsIgnoreCase("p")  )
                {
                    System.out.println("Second test-->"  + Board[CurrentPosition.getX()][Destination.getY()].getName().equalsIgnoreCase("p")  );

                    System.out.println("IS this pawn test running?");
                if( ((Pawn)Board[CurrentPosition.getX()][Destination.getY()]).Empassant && Board[CurrentPosition.getX()][Destination.getY()].PieceColor != this.PieceColor  )
                    {
                    BoardManager.getInstance().BlackContainer.remove(Board[CurrentPosition.getX()][Destination.getY()]);
                        Log.d(MainActivity.STATE,String.format("Setting Black piece on (%d,%d) to null", CurrentPosition.getX(),Destination.getY() ));
                        Board[CurrentPosition.getX()][Destination.getY()] = null;
                    return true;
                    }
                }
                return false;
            }
        }
        else if(Destination.getX()==CurrentPosition.getX()- 2  && Destination.getY() == CurrentPosition.getY() && FirstMove )
        {
            if(Board[Destination.getX()][Destination.getY()] == null && Board[Destination.getX()-1][Destination.getY()] == null)
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
              Log.d(MainActivity.STATE, "Black pawn");

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
                    BoardManager.getInstance().WhiteContainer.remove(Board[CurrentPosition.getX()][Destination.getY()]);
                        Log.d(MainActivity.STATE,String.format("Setting White piece on (%d,%d) to null", CurrentPosition.getX(),Destination.getY() ));
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
            if(Board[Destination.getX()][Destination.getY()] == null && Board[Destination.getX() + 1][Destination.getY()] == null )
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


    public boolean simulateMove(Piece[][] Board, Point Destination)
    {
        //check if the destination  is legal
//        System.out.println("checking pawn");
        if(PieceColor ==Color.White)
        {
            Log.d(MainActivity.STATE, "White pawn");
//            System.out.println("Going from " + this.CurrentPosition + "to " + Destination);
            if(Destination.getX()==CurrentPosition.getX() - 1 && Destination.getY() == CurrentPosition.getY())
            {
                if(Board[Destination.getX()][Destination.getY()] == null)
                {
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
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    System.out.println("IS this pawn ELSE test running?");
                    System.out.println("PIece in question:" + Board[CurrentPosition.getX()][Destination.getY()]  );
                    System.out.println("First test -->" + Board[CurrentPosition.getX()][Destination.getY()] != null);
                    if(Board[CurrentPosition.getX()][Destination.getY()] != null && Board[CurrentPosition.getX()][Destination.getY()].getName().equalsIgnoreCase("p")  )
                    {
                        System.out.println("Second test-->"  + Board[CurrentPosition.getX()][Destination.getY()].getName().equalsIgnoreCase("p")  );
                        System.out.println("IS this pawn test running?");
                        if( ((Pawn)Board[CurrentPosition.getX()][Destination.getY()]).Empassant && Board[CurrentPosition.getX()][Destination.getY()].PieceColor != this.PieceColor  )
                        {

                            return true;
                        }
                    }
                    return false;
                }
            }
            else if(Destination.getX()==CurrentPosition.getX()- 2  && Destination.getY() == CurrentPosition.getY() && FirstMove )
            {
                if(Board[Destination.getX()][Destination.getY()] == null &&  Board[CurrentPosition.getX()-1][Destination.getY()] == null )
                {
//                    Empassant = true;
                    return true;
                }
                System.out.println("Is thi returning false!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return false;

//            System.out.println("returning pawn 3");
            }
        }
        else if(PieceColor ==Color.Black)
        {
            Log.d(MainActivity.STATE, "Black pawn");

            if(Destination.getX()==CurrentPosition.getX() + 1 && Destination.getY() == CurrentPosition.getY())
            {
                if(Board[Destination.getX()][Destination.getY()] == null)
                {
//                    Empassant  = false;
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

                            return true;
                        }
                    }
                }
            }

//            System.out.println("returning pawn 2");
//            System.out.println("Black pawn");

            else if(Destination.getX()==CurrentPosition.getX()+ 2  && Destination.getY() == CurrentPosition.getY() && FirstMove )
            {
                if(Board[Destination.getX()][Destination.getY()] == null && Board[CurrentPosition.getX()+1][Destination.getY()] == null)
                {
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
