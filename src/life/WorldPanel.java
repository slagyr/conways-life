package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.Objects;

public class WorldPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener
{

  private static final Color highlightColor = new Color(0, 100, 255, 125);
  private static final Color cellColor = new Color(150, 200, 150);
  private static final Color cellOutlineColor = new Color(50, 100, 50);

  private Controller controller;
  private Point highlightedCell;
  private Collection<Point> living;
  private int zoom;
  private Point center;
  private Point origin;

  public WorldPanel()
  {
    setBackground(Color.darkGray);
  }

  public void wire(Controller controller)
  {
    this.controller = controller;
    addMouseListener(this);
    addMouseMotionListener(this);
    addComponentListener(this);
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    paintLiving(g);
    paintHighlightedCell(g);
  }

  private void paintLiving(Graphics g)
  {
    if(living == null)
      return;
    g.setColor(cellColor);
    for(Point cell : living)
    {
      Point o = cellToPixel(cell);

      g.setColor(cellColor);
      g.fillRect(o.x, o.y, zoom, zoom);
      g.setColor(cellOutlineColor);
      g.drawRect(o.x, o.y, zoom, zoom);
    }
  }

  private void paintHighlightedCell(Graphics g)
  {
    if(highlightedCell == null)
      return;
    Point hilite = cellToPixel(highlightedCell);
    g.setColor(highlightColor);
    g.fillRect(hilite.x, hilite.y, zoom, zoom);
  }


  private void resize()
  {
    int centerX = (getWidth() + 1) / 2;
    int centerY = (getHeight() + 1) / 2;
    center = new Point(centerX, centerY);

    final int halfDim = (zoom + 1) / -2;
    origin = center.translate(halfDim, halfDim);

    repaint();
  }

  private Point cellToPixel(Point point)
  {
    int x = origin.x + (zoom * point.x);
    int y = origin.y + (zoom * point.y);
    return new Point(x, y);
  }

  private Point pixelToCell(Point point)
  {
    int halfZoom = (zoom + 1) / 2;
    int cellX = (point.x - center.x);
    cellX += cellX < 0 ? -halfZoom : halfZoom;
    cellX /= zoom;

    int cellY = (point.y - center.y);
    cellY += cellY < 0 ? -halfZoom : halfZoom;
    cellY /= zoom;

    return new Point(cellX, cellY);
  }

  public void updateZoom(int zoom)
  {
    this.zoom = zoom;
    resize();
  }

  private void updateHighlighted(Point cell)
  {
    if(!Objects.equals(cell, highlightedCell))
    {
      highlightedCell = cell;
      repaint();
    }
  }

  public void updateLiving(Collection<Point> living)
  {
    this.living = living;
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e)
  {

  }

  @Override
  public void mouseMoved(MouseEvent e)
  {
    final Point cell = pixelToCell(new Point(e.getX(), e.getY()));
    updateHighlighted(cell);
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
    final Point cell = pixelToCell(new Point(e.getX(), e.getY()));
    controller.toggle(cell.x, cell.y);
  }

  @Override
  public void mousePressed(MouseEvent e)
  {

  }

  @Override
  public void mouseReleased(MouseEvent e)
  {

  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
  }

  @Override
  public void mouseExited(MouseEvent e)
  {
    updateHighlighted(null);
  }

  @Override
  public void componentResized(ComponentEvent e)
  {
    resize();
  }

  @Override
  public void componentMoved(ComponentEvent e)
  {

  }

  @Override
  public void componentShown(ComponentEvent e)
  {

  }

  @Override
  public void componentHidden(ComponentEvent e)
  {

  }
}
