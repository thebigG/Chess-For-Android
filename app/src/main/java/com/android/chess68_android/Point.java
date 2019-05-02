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
public class Point
{
private int x;
private int y;
public Point(int x, int y)
{
    this.x = x;
    this.y = y;
}

    public Point(Point CurrentPosition) 
    {
    this.x = CurrentPosition.getX();
    this.y = CurrentPosition.getY();
    }
public void setY(int y)
{
    this.y = y;
}
public int getY()
{
    return y;
}
public void setX(int x)
{
    this.x = x;
}
public int getX()
{
    return x;
}
public String toString()
{
    return "(" + x +"," + y + ")";
}
@Override
public boolean equals(Object p2)
{

  if (!(p2 instanceof Point))
  {
    return false;
  }
  return (((Point)p2).getY() == this.getY()) && ( ((Point)p2).getX() == this.getX());
}
public boolean isSquare()
{
    return Math.abs(x)== Math.abs(y);
}
}
