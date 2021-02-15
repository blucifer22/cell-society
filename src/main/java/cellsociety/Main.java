package cellsociety;

import cellsociety.graphics.UIController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The starting point for the application
 *
 * @author David Coffman
 */
public class Main extends Application {

  private static final double FRAME_RATE = 60;
  private static final double FRAME_DELAY = 1.0 / FRAME_RATE;
  private UIController controller;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.controller = new UIController(primaryStage, "English");

    KeyFrame frame = new KeyFrame(Duration.seconds(FRAME_DELAY),
        e -> controller.refresh(FRAME_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }
}
