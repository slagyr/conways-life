package life;

import java.util.Arrays;
import java.util.Collection;

public class UIState
{
  public int speed = 0;

  public Collection<Point> living;// = Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(-2, -2));

  public UIState()
  {
  }

  public UIState(int speed, Collection<Point> living)
  {
    this.speed = speed;
    this.living = living;
  }
}
