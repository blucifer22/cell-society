package cellsociety.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * <code>SimulationSelectScene</code> is the initial scene inside the application, instantiated by
 * the <code>UIController</code>.
 *
 * @author David Coffman
 * @author Joshua Petitma
 */
public class SimulationSelectionScene extends Scene {

  private final UIController uiController;
  private final Group root;
  private final double width;
  private final double height;
  private ResourceBundle resources;
  private final List<String> availableThemes = List.of("Light", "Dark");
  private Button fileLoadButton;

  /**
   * Sole constructor for <code>SimulationSelectScene</code>. Called by <code>UIController</code> to
   * create the first scene in the application (for loading an XML configuration).
   *
   * @param uiController the UIController to link the <code>SimulationSelectScene</code> to
   * @param width the width of the <code>SimulationSelectScene</code>
   * @param height the height of the <code>SimulationSelectScene</code>
   */
  public SimulationSelectionScene(
      UIController uiController, double width, double height, ResourceBundle resources) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.width = width;
    this.height = height;
    this.resources = resources;
    Button fileLoadButton;
    this.uiController = uiController;
    configureScene();
  }

  // Configures the scene; sets title of window and configures the "load XML" button
  private void configureScene() {
    uiController.setTitle(resources.getString("Launch"));
    BorderPane sp = new BorderPane();
    this.fileLoadButton = new Button();
    sp.setCenter(fileLoadButton);
    sp.setTop(createSettings());
    fileLoadButton.setText(resources.getString("LoadSimulationXML"));
    fileLoadButton.setOnAction(event -> uiController.loadNewSimulation());
    sp.setPrefWidth(width);
    sp.setPrefHeight(height);
    renderNode(sp);
  }

  private Pane createSettings() {
    HBox row = new HBox(20);
    row.setAlignment(Pos.CENTER);
    Label langIcon = createIcon("icons/language.png");
    Label themeIcon = createIcon("icons/theme.png");

    ComboBox<Language> langSelect = new ComboBox<>();
    langSelect.getItems().addAll(Language.values());
    langSelect.setValue(Language.values()[0]);

    ComboBox<Theme> themeSelect = new ComboBox<>();
    themeSelect.getItems().addAll(Theme.values());
    themeSelect.setValue(Theme.values()[0]);

    langSelect.setOnAction( e -> changeLanguage(langSelect.getValue()));

    row.getChildren().addAll(langIcon, langSelect, themeIcon, themeSelect);

    return row;
  }

  private void changeLanguage(Language lang) {
    this.resources = ResourceBundle.getBundle(UIController.RESOURCE_PATH + lang);
    uiController.setLanguage(this.resources);
    referesh();
  }

  private void referesh() {
    fileLoadButton.setText(resources.getString("LoadSimulationXML"));
    uiController.setTitle(resources.getString("Launch"));
  }

  private Label createIcon(String name) {
    Label label = new Label();
    Image labelImg = new Image(getClass().getResourceAsStream(name));
    ImageView labelIcon = new ImageView(labelImg);
    label.setGraphic(labelIcon);
    return label;
  }

  // Adds the Node parameter to the root Group of the Scene
  private void renderNode(Node n) {
    root.getChildren().add(n);
  }

  public enum Language {
    ENGLISH("English"),
    FRENCH("French"),
    POLISH("Polish");

    private final String bundleName;

    Language(String s) {
      this.bundleName = s;
    }

    @Override
    public String toString() {
      return this.bundleName;
    }
  }

  public enum Theme {
    LIGHT("Light"),
    DARK("Dark");

    private final String bundleName;

    Theme(String s) {
      this.bundleName = s;
    }

    @Override
    public String toString() {
      return this.bundleName;
    }
  }
}
