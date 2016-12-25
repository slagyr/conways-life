package life;

public class LogController implements Controller
{

  @Override
  public void pause()
  {
    System.out.println("Pause");
  }

  @Override
  public void reset()
  {
    System.out.println("Reset");
  }

  @Override
  public void speed(int speed)
  {
    System.out.println("Speed: " + speed);
  }

  @Override
  public void toggle(int x, int y)
  {
    System.out.println("toggle: " + x + ", " + y);
  }
}
