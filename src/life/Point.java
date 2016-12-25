package life;

public class Point
{
  public int x;
  public int y;

  public Point(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public Point translate(int dx, int dy)
  {
    return new Point(x + dx, y + dy);
  }

  @Override
  public String toString()
  {
    return "[" + x + ", " + y + "]";
  }

  @Override
  public boolean equals(Object o)
  {
    if(this == o) return true;
    if(!(o instanceof Point)) return false;

    Point point = (Point) o;

    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode()
  {
    int result = x;
    result = 31 * result + y;
    return result;
  }
}
