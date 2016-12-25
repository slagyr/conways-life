package life;

import javax.swing.*;

public class UI implements LifeUI
{
  private JPanel mainPane;
  private JButton pauseButton;
  private JButton resetButton;
  private JSlider zoomSlider;
  private WorldPanel worldPanel;
  private JSlider speedSlider;
  private UIState state = new UIState();

  private void createUIComponents()
  {
    // TODO: place custom component creation code here
  }

  public static void main(String[] args)
  {
    final UI ui = UI.create(new LogController());
    ui.update(new UIState());
    ui.show();
  }

  public void show()
  {
    JFrame frame = new JFrame("Life");
    frame.setContentPane(this.mainPane);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static UI create(Controller controller)
  {
    UI ui = new UI();
    ui.init(controller);
    return ui;
  }

  public void init(Controller controller)
  {
    wire(controller);
    zoomSlider.setValue(50);
    worldPanel.updateZoom(50);
  }

  private void wire(Controller controller)
  {
    worldPanel.wire(controller);
    pauseButton.addActionListener(e -> controller.pause());
    speedSlider.addChangeListener(e -> controller.speed(speedSlider.getValue()));
    resetButton.addActionListener(e -> controller.reset());
    zoomSlider.addChangeListener(e -> worldPanel.updateZoom(zoomSlider.getValue()));
  }

  public void update(UIState state)
  {
    this.state = state;
    speedSlider.setValue(state.speed);
    worldPanel.updateLiving(state.living);
  }


}
