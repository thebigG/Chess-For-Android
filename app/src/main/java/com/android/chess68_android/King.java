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
public class King extends Piece
{
    public boolean inCheck;
    public  boolean FirstMove;
    //Call this method between every move
   
    @Override
         public  String getName()
 {
     return "K";
 }
    public King(Point StartingPosition, Color PieceColor)
    {
        super(StartingPosition, PieceColor);
        FirstMove = true;
    }
    @Override
    @SuppressWarnings("empty-statement")
    public boolean move(Piece[][] Board, Point Destination)
    {
        if( CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() + 1 == Destination.getX() )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
      }
      else if( CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() - 1 == Destination.getX() )
      {
          return (Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor );
      }

      else if( CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() + 1 == Destination.getX() )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
        
      }
      else if( CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() - 1 == Destination.getX() )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor ;
          
          
      }
      else if(CurrentPosition.getY() + 1 == Destination.getY()  && CurrentPosition.getX() ==Destination.getX() )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
       
      }
      else if(CurrentPosition.getY() - 1 == Destination.getY()  && CurrentPosition.getX() ==Destination.getX()  )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor ;
        
      }
      else if(CurrentPosition.getX() + 1 == Destination.getX() && CurrentPosition.getY() == Destination.getY() )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor ;          

        
      }
      else if(CurrentPosition.getX() - 1 == Destination.getX() && CurrentPosition.getY() == Destination.getY() )
      {
          return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor ;
          
          
      }
        /**Castling Code
        else if(CurrentPosition.getY() - 2 == Destination.getY()  && CurrentPosition.getX() ==Destination.getX()  )
        {
//            System.out.println("Trying castling");
            if(!inCheck)
            {
//                System.out.println("Not in check");
                if(FirstMove)
                {
//                    System.out.println("First move is true");
                    Point RookPosition = new Point(Destination.getX(),CurrentPosition.getY() - 4 );
//                    System.out.println("Rook's position:" + RookPosition);
//                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                   if(Board[ Destination.getX()][CurrentPosition.getY() - 4] != null)
                   {
//                       System.out.println("There is a piece on the target spot");
                       if(Board[Destination.getX()][CurrentPosition.getY() - 4].PieceColor == this.PieceColor && Board[ Destination.getX()][CurrentPosition.getY() - 4].getName().equals("R") && Board[ Destination.getX()][CurrentPosition.getY() - 4].FirstMove  )
                       {
//                           System.out.println("Entering the loop");
                           for(int i = 1;i<4 ;i++ )
                           {
                               if(Board[Destination.getX()][CurrentPosition.getY() - i] != null)
                               {
//                                   System.out.println("There is something in the way");
                                   return false;
                               }
                           }
                           for(int i =1; i<=2;i++)
                           {
                           if(i<=2)
                               {
                                   CurrentPosition.setY(CurrentPosition.getY() - 1);
                                   if( Chess.thisBoard.isInCheck(this))
                                   {
                                       CurrentPosition.setY(CurrentPosition.getY() + i);
//                                       System.out.println("Trying to castle through check");
                                       return false;
                                   }
                                   
                               }
                           }
                           
//                            System.out.println("Rook's position:" + RookPosition);

                           Chess.thisBoard.updatePiecePosition(RookPosition, new Point(Destination.getX(),CurrentPosition.getY() + 1));
                           Board[RookPosition.getX()][RookPosition.getY()].FirstMove = false;
                           Chess.thisBoard.updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() + 2), new Point(Destination.getX(), CurrentPosition.getY() ));

                           return true;
                       }
                   }
                }
                return false;
            }   
            
        }
        
        else if(CurrentPosition.getY() + 2 == Destination.getY()  && CurrentPosition.getX() ==Destination.getX()  )
        {
//            System.out.println("Trying castling");
            if(!inCheck)
            {
//                System.out.println("Not in check");
                if(FirstMove)
                {
//                    System.out.println("First move is true");
                    Point RookPosition = new Point(Destination.getX(),CurrentPosition.getY() + 3 );
//                    System.out.println("Rook's position:" + RookPosition);
//                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                   if(Board[ Destination.getX()][CurrentPosition.getY() + 3] != null)
                   {
//                       System.out.println("There is a piece on the target spot");
                       if(Board[Destination.getX()][CurrentPosition.getY() + 3].PieceColor == this.PieceColor && Board[ Destination.getX()][CurrentPosition.getY()  +3].getName().equals("R") && Board[ Destination.getX()][CurrentPosition.getY() + 3].FirstMove  )
                       {
//                           System.out.println("Entering the loop");
                           for(int i = 1;i<3 ;i++ )
                           {
                               if(Board[Destination.getX()][CurrentPosition.getY() + i] != null)
                               {
//                                   System.out.println("There is something in the way");
                                   return false;
                               }
                           }
                           for(int i =1; i<=2;i++)
                           {
                           if(i<=2)
                               {
                                   CurrentPosition.setY(CurrentPosition.getY() + 1);
                                   if( Chess.thisBoard.isInCheck(this))
                                   {
                                       CurrentPosition.setY(CurrentPosition.getY() - i);
//                                       System.out.println("Trying to castle through check");
                                       return false;
                                   }
                                   
                               }
                           }
                           
//                            System.out.println("Rook's position:" + RookPosition);
                           Chess.thisBoard.updatePiecePosition(RookPosition, new Point(Destination.getX(),CurrentPosition.getY() - 1));
                           Chess.thisBoard.updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() - 2), new Point(Destination.getX(), CurrentPosition.getY() ));
                           Board[RookPosition.getX()][CurrentPosition.getY() - 1].FirstMove = false;
                           return true;
                       }
                   }
                }
                return false;
            }   
            
        }


         **/

     return false;
    }


 
}
