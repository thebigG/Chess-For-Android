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
public class King extends Piece {
    public boolean inCheck;
    public boolean FirstMove;
    //Call this method between every move

    @Override
    public String getName() {
        return "K";
    }

    public King(Point StartingPosition, Color PieceColor) {
        super(StartingPosition, PieceColor);
        FirstMove = true;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public boolean move(Piece[][] Board, Point Destination) {
        if (CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() + 1 == Destination.getX()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
        } else if (CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() - 1 == Destination.getX()) {
            return (Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor);
        } else if (CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() + 1 == Destination.getX()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;

        } else if (CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() - 1 == Destination.getX()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;


        } else if (CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() == Destination.getX()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;

        } else if (CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() == Destination.getX()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;

        } else if (CurrentPosition.getX() + 1 == Destination.getX() && CurrentPosition.getY() == Destination.getY()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;


        } else if (CurrentPosition.getX() - 1 == Destination.getX() && CurrentPosition.getY() == Destination.getY()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;


        }
        if(PieceColor == Color.White) {
            if (CurrentPosition.getY() - 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 7) {
//            System.out.println("Trying castling");
                if (!inCheck) {
//                System.out.println("Not in check");
                    if (FirstMove) {
//                    System.out.println("First move is true");
                        Point RookPosition = new Point(Destination.getX(), CurrentPosition.getY() - 4);
//                    System.out.println("Rook's position:" + RookPosition);
//                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                        if (Board[Destination.getX()][CurrentPosition.getY() - 4] != null) {
//                       System.out.println("There is a piece on the target spot");
                            if (Board[Destination.getX()][CurrentPosition.getY() - 4].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() - 4].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() - 4].FirstMove) {
//                           System.out.println("Entering the loop");
                                for (int i = 1; i < 4; i++) {
                                    if (Board[Destination.getX()][CurrentPosition.getY() - i] != null) {
//                                   System.out.println("There is something in the way");
                                        return false;
                                    }
                                }
                                for (int i = 1; i <= 2; i++) {
                                    if (i <= 2) {
                                        CurrentPosition.setY(CurrentPosition.getY() - 1);
                                        if (BoardManager.getInstance().isInCheck(this)) {
                                            CurrentPosition.setY(CurrentPosition.getY() + i);
//                                       System.out.println("Trying to castle through check");
                                            return false;
                                        }

                                    }
                                }

//                            System.out.println("Rook's position:" + RookPosition);

                                BoardManager.getInstance().updatePiecePosition(RookPosition, new Point(Destination.getX(), CurrentPosition.getY() + 1));
                                Board[RookPosition.getX()][CurrentPosition.getY() + 1].FirstMove = false;
                                BoardManager.getInstance().updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() + 2), new Point(Destination.getX(), CurrentPosition.getY()));

                                return true;
                            }
                        }
                    }
                    return false;
                }

            } else if (CurrentPosition.getY() + 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 7) {
//            System.out.println("Trying castling");
                if (!inCheck) {
//                System.out.println("Not in check");
                    if (FirstMove) {
//                    System.out.println("First move is true");
                        Point RookPosition = new Point(Destination.getX(), CurrentPosition.getY() + 3);
//                    System.out.println("Rook's position:" + RookPosition);
//                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                        if (Board[Destination.getX()][CurrentPosition.getY() + 3] != null) {
//                       System.out.println("There is a piece on the target spot");
                            if (Board[Destination.getX()][CurrentPosition.getY() + 3].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() + 3].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() + 3].FirstMove) {
//                           System.out.println("Entering the loop");
                                for (int i = 1; i < 3; i++) {
                                    if (Board[Destination.getX()][CurrentPosition.getY() + i] != null) {
//                                   System.out.println("There is something in the way");
                                        return false;
                                    }
                                }
                                for (int i = 1; i <= 2; i++) {
                                    if (i <= 2) {
                                        CurrentPosition.setY(CurrentPosition.getY() + 1);
                                        if (BoardManager.getInstance().isInCheck(this)) {
                                            CurrentPosition.setY(CurrentPosition.getY() - i);
//                                       System.out.println("Trying to castle through check");
                                            return false;
                                        }

                                    }
                                }

//                            System.out.println("Rook's position:" + RookPosition);
                                BoardManager.getInstance().updatePiecePosition(RookPosition, new Point(Destination.getX(), CurrentPosition.getY() - 1));
                                BoardManager.getInstance().updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() - 2), new Point(Destination.getX(), CurrentPosition.getY()));
                                Board[RookPosition.getX()][CurrentPosition.getY() - 1].FirstMove = false;
                                return true;
                            }
                        }
                    }
                    return false;
                }

            }
        }
        else if(PieceColor == Color.Black)
        {
            if (CurrentPosition.getY() - 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 0) {
//            System.out.println("Trying castling");
                if (!inCheck) {
//                System.out.println("Not in check");
                    if (FirstMove) {
//                    System.out.println("First move is true");
                        Point RookPosition = new Point(Destination.getX(), CurrentPosition.getY() - 4);
//                    System.out.println("Rook's position:" + RookPosition);
//                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                        if (Board[Destination.getX()][CurrentPosition.getY() - 4] != null) {
//                       System.out.println("There is a piece on the target spot");
                            if (Board[Destination.getX()][CurrentPosition.getY() - 4].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() - 4].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() - 4].FirstMove) {
//                           System.out.println("Entering the loop");
                                for (int i = 1; i < 4; i++) {
                                    if (Board[Destination.getX()][CurrentPosition.getY() - i] != null) {
//                                   System.out.println("There is something in the way");
                                        return false;
                                    }
                                }
                                for (int i = 1; i <= 2; i++) {
                                    if (i <= 2) {
                                        CurrentPosition.setY(CurrentPosition.getY() - 1);
                                        if (BoardManager.getInstance().isInCheck(this)) {
                                            CurrentPosition.setY(CurrentPosition.getY() + i);
//                                       System.out.println("Trying to castle through check");
                                            return false;
                                        }

                                    }
                                }

//                            System.out.println("Rook's position:" + RookPosition);

                                BoardManager.getInstance().updatePiecePosition(RookPosition, new Point(Destination.getX(), CurrentPosition.getY() + 1));
                                Board[RookPosition.getX()][CurrentPosition.getY() + 1].FirstMove = false;
                                BoardManager.getInstance().updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() + 2), new Point(Destination.getX(), CurrentPosition.getY()));

                                return true;
                            }
                        }
                    }
                    return false;
                }

            } else if (CurrentPosition.getY() + 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 0) {
//            System.out.println("Trying castling");
                if (!inCheck) {
//                System.out.println("Not in check");
                    if (FirstMove) {
//                    System.out.println("First move is true");
                        Point RookPosition = new Point(Destination.getX(), CurrentPosition.getY() + 3);
//                    System.out.println("Rook's position:" + RookPosition);
//                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                        if (Board[Destination.getX()][CurrentPosition.getY() + 3] != null) {
//                       System.out.println("There is a piece on the target spot");
                            if (Board[Destination.getX()][CurrentPosition.getY() + 3].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() + 3].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() + 3].FirstMove) {
//                           System.out.println("Entering the loop");
                                for (int i = 1; i < 3; i++) {
                                    if (Board[Destination.getX()][CurrentPosition.getY() + i] != null) {
//                                   System.out.println("There is something in the way");
                                        return false;
                                    }
                                }
                                for (int i = 1; i <= 2; i++) {
                                    if (i <= 2) {
                                        CurrentPosition.setY(CurrentPosition.getY() + 1);
                                        if (BoardManager.getInstance().isInCheck(this)) {
                                            CurrentPosition.setY(CurrentPosition.getY() - i);
//                                       System.out.println("Trying to castle through check");
                                            return false;
                                        }

                                    }
                                }

//                            System.out.println("Rook's position:" + RookPosition);
                                BoardManager.getInstance().updatePiecePosition(RookPosition, new Point(Destination.getX(), CurrentPosition.getY() - 1));
                                BoardManager.getInstance().updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() - 2), new Point(Destination.getX(), CurrentPosition.getY()));
                                Board[RookPosition.getX()][CurrentPosition.getY() - 1].FirstMove = false;
                                return true;
                            }
                        }
                    }
                    return false;
                }

            }

        }


        return false;
    }

    public boolean simulateMove(Piece[][] Board, Point Destination) {
        if (CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() + 1 == Destination.getX())
        {
            System.out.println("True for king 1:" + Destination);
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;
        } else if (CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() - 1 == Destination.getX()) {
            System.out.println("True for king 2:" + Destination);
            return (Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor);
        }
        else if (CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() + 1 == Destination.getX())
        {
            System.out.println(String.format("True for king 3:%s, for  this CurrentLocation:%s", Destination, CurrentPosition));
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;

        } else if (CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() - 1 == Destination.getX()) {
            System.out.println("True for king 4:" + Destination);
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;


        } else if (CurrentPosition.getY() + 1 == Destination.getY() && CurrentPosition.getX() == Destination.getX()) {
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;

        } else if (CurrentPosition.getY() - 1 == Destination.getY() && CurrentPosition.getX() == Destination.getX()) {
            System.out.println("True for king 5:" + Destination);
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;

        } else if (CurrentPosition.getX() + 1 == Destination.getX() && CurrentPosition.getY() == Destination.getY()) {
            System.out.println("True for king 6:" + Destination);
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;


        } else if (CurrentPosition.getX() - 1 == Destination.getX() && CurrentPosition.getY() == Destination.getY()) {
            System.out.println("True for king 7:" + Destination);
            return Board[Destination.getX()][Destination.getY()] == null || Board[Destination.getX()][Destination.getY()].PieceColor != this.PieceColor;


        }
//        /**Castling Code
        else if (PieceColor == Color.White)
        {
            System.out.println("Tyring castling on white king");
            if (CurrentPosition.getY() - 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 7) {
                //            System.out.println("Trying castling");
                System.out.println(String.format("True for white1 on destination:%s on this piece's location:%s" , Destination, CurrentPosition));
                if (!inCheck) {
                    //                System.out.println("Not in check");
                    if (FirstMove) {
                        //                    System.out.println("First move is true");

                        //                    System.out.println("Rook's position:" + RookPosition);
                        //                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                        if (Board[Destination.getX()][CurrentPosition.getY() - 4] != null) {
                            //                       System.out.println("There is a piece on the target spot");
                            if (Board[Destination.getX()][CurrentPosition.getY() - 4].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() - 4].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() - 4].FirstMove) {
                                //                           System.out.println("Entering the loop");
                                for (int i = 1; i < 4; i++) {
                                    if (Board[Destination.getX()][CurrentPosition.getY() - i] != null) {
                                        //                                   System.out.println("There is something in the way");
                                        return false;
                                    }
                                }
                                int temp_i =0;
                                for (int i = 1; i <= 2; i++)
                                {
                                    temp_i=  i;
                                    if (i <= 2) {
                                        CurrentPosition.setY(CurrentPosition.getY() - 1);
                                        if (BoardManager.getInstance().isInCheck(this)) {
                                            CurrentPosition.setY(CurrentPosition.getY() + i);
                                            System.out.println("False for king 20:" + Destination);
                                            //                                       System.out.println("Trying to castle through check");
                                            return false;
                                        }

                                    }
                                }
                                CurrentPosition.setY(CurrentPosition.getY() + temp_i);

                                //                            System.out.println("Rook's position:" + RookPosition);

                                //         BoardManager.getInstance().updatePiecePosition(RookPosition, new Point(Destination.getX(),CurrentPosition.getY() + 1));
                                //         Board[RookPosition.getX()][RookPosition.getY()].FirstMove = false;
                                //             BoardManager.getInstance().updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() + 2), new Point(Destination.getX(), CurrentPosition.getY() ));
                                System.out.println("True for king 8:" + Destination);

                                return true;
                            }
                        }
                    }
                    System.out.println("False for king 8:" + Destination);
                    return false;
                }

            }
             else if ((CurrentPosition.getY() + 2 == Destination.getY()) && (CurrentPosition.getX() == Destination.getX()) && (CurrentPosition.getX() == 7)) {
                    //            System.out.println("Trying castling");
                    System.out.println("Trying castling #1");
                    if (!inCheck) {
                        //                System.out.println("Not in check");
                        if (FirstMove) {
                            //                    System.out.println("First move is true");
                            //                    System.out.println("Rook's position:" + RookPosition);
                            //                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                            if (Board[Destination.getX()][CurrentPosition.getY() + 3] != null) {
                                //                       System.out.println("There is a piece on the target spot");
                                if (Board[Destination.getX()][CurrentPosition.getY() + 3].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() + 3].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() + 3].FirstMove) {
                                    //                           System.out.println("Entering the loop");
                                    for (int i = 1; i < 3; i++) {
                                        if (Board[Destination.getX()][CurrentPosition.getY() + i] != null) {
                                            //                                   System.out.println("There is something in the way");
                                            return false;
                                        }
                                    }
                                    int temp_i = 0;
                                    for (int i = 1; i <= 2; i++)
                                    {
                                        temp_i = i;

                                        if (i <= 2)
                                        {
                                            CurrentPosition.setY(CurrentPosition.getY() + 1);
                                            if (BoardManager.getInstance().isInCheck(this))
                                            {
                                                System.out.println("False for king 10:" + Destination);

                                                CurrentPosition.setY(CurrentPosition.getY() - i);
                                                //                                       System.out.println("Trying to castle through check");
                                                return false;
                                            }

                                        }
                                    }

                                    //                            System.out.println("Rook's position:" + RookPosition);
                                    System.out.println("True for king 10:" + Destination);
                                    CurrentPosition.setY(CurrentPosition.getY() - temp_i);
                                    return true;
                                }
                            }
                        }
                        System.out.println("False for king 11:" + Destination);
                        return false;
                    }

                }

        }
        else if(PieceColor == Color.Black)
        {
            if (CurrentPosition.getY() - 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 0)
            {
                System.out.println(String.format("True for black1 on destination:%s on this piece's location:%s" , Destination, CurrentPosition));
                //            System.out.println("Trying castling");
                if (!inCheck) {
                    //                System.out.println("Not in check");
                    if (FirstMove) {
                        //                    System.out.println("First move is true");

                        //                    System.out.println("Rook's position:" + RookPosition);
                        //                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                        if (Board[Destination.getX()][CurrentPosition.getY() - 4] != null) {
                            //                       System.out.println("There is a piece on the target spot");
                            if (Board[Destination.getX()][CurrentPosition.getY() - 4].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() - 4].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() - 4].FirstMove) {
                                //                           System.out.println("Entering the loop");
                                for (int i = 1; i < 4; i++) {
                                    if (Board[Destination.getX()][CurrentPosition.getY() - i] != null) {
                                        //                                   System.out.println("There is something in the way");
                                        return false;
                                    }
                                }
                                int temp_i = 0;
                                for (int i = 1; i <= 2; i++) {
                                     temp_i  = i;
                                    if (i <= 2) {
                                        CurrentPosition.setY(CurrentPosition.getY() - 1);
                                        if (BoardManager.getInstance().isInCheck(this)) {
                                            CurrentPosition.setY(CurrentPosition.getY() + i);
                                            //                                       System.out.println("Trying to castle through check");
                                            return false;
                                        }

                                    }
                                }

                                //                            System.out.println("Rook's position:" + RookPosition);

                                //         BoardManager.getInstance().updatePiecePosition(RookPosition, new Point(Destination.getX(),CurrentPosition.getY() + 1));
                                //         Board[RookPosition.getX()][RookPosition.getY()].FirstMove = false;
                                //             BoardManager.getInstance().updatePiecePosition(new Point(Destination.getX(), CurrentPosition.getY() + 2), new Point(Destination.getX(), CurrentPosition.getY() ));
                                System.out.println("True for king:" + Destination);
                                CurrentPosition.setY(CurrentPosition.getY() + temp_i);
                                return true;
                            }
                        }
                    }
                    return false;
                }

            }

                else if (CurrentPosition.getY() + 2 == Destination.getY() && CurrentPosition.getX() == Destination.getX() && CurrentPosition.getX() == 0)
                {
                    //            System.out.println("Trying castling");
                    if (!inCheck) {
                        //                System.out.println("Not in check");
                        if (FirstMove) {
                            //                    System.out.println("First move is true");
                            Point RookPosition = new Point(Destination.getX(), CurrentPosition.getY() + 3);
                            //                    System.out.println("Rook's position:" + RookPosition);
                            //                    System.out.println("Rook in question:" + Board[RookPosition.getX()][RookPosition.getY()]);
                            if (Board[Destination.getX()][CurrentPosition.getY() + 3] != null) {
                                //                       System.out.println("There is a piece on the target spot");
                                if (Board[Destination.getX()][CurrentPosition.getY() + 3].PieceColor == this.PieceColor && Board[Destination.getX()][CurrentPosition.getY() + 3].getName().equals("R") && Board[Destination.getX()][CurrentPosition.getY() + 3].FirstMove) {
                                    //                           System.out.println("Entering the loop");
                                    for (int i = 1; i < 3; i++) {
                                        if (Board[Destination.getX()][CurrentPosition.getY() + i] != null) {
                                            //                                   System.out.println("There is something in the way");
                                            return false;
                                        }
                                    }
                                    int temp_i = 0;
                                    for (int i = 1; i <= 2; i++) {
                                        temp_i = i;
                                        if (i <= 2) {
                                            CurrentPosition.setY(CurrentPosition.getY() + 1);
                                            if (BoardManager.getInstance().isInCheck(this)) {
                                                CurrentPosition.setY(CurrentPosition.getY() - i);
                                                //                                       System.out.println("Trying to castle through check");
                                                return false;
                                            }

                                        }
                                    }
                                    CurrentPosition.setY(CurrentPosition.getY() - temp_i);
                                    //                            System.out.println("Rook's position:" + RookPosition);
                                    System.out.println("True for king:" + Destination);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }

                }
            }
        return false;

    }




        }



