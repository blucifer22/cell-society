package cellsociety.graphics.cells;

import cellsociety.graphics.SimulationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExtraSettingsPopup extends Scene {
  private final SimulationController simController;
  private final Stage stage;
  private final Group renderingGroup;

  public ExtraSettingsPopup(SimulationController s) {
    super(new Group());
    this.simController = s;
    this.stage = new Stage();
    this.renderingGroup = (Group) this.getRoot();
    stage.setScene(this);
    buildScene();
    this.stage.show();
  }

  private void buildScene() {
    VBox rows = new VBox(10);

    for(String key: simController.getSimulationParameters().keySet()) {
      HBox row = new HBox(10);
      row.setAlignment(Pos.CENTER_RIGHT);
      row.getChildren().add(new Text(key));
      row.getChildren().add(new TextField());
      rows.getChildren().add(row);
    }
    this.renderingGroup.getChildren().add(rows);
  }

  private void updateSimulationParameter(String name, String value) {
    simController.updateSimulationParameter();
  }

  public void destroy() {
    this.stage.hide();
  }

}
