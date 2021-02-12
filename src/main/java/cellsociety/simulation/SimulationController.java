package cellsociety.simulation;

import cellsociety.graphics.GraphicalCell;
import cellsociety.graphics.UIController;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Paint;

/**
 * The controller class that creates the simulation model for the view to use.
 *
 * <p>This class contains an XML parser and can create {@link cellsociety.simulation.Simulation}
 * objects.
 *
 * @author Joshua Petitma
 * @author David Coffman
 * @author Marc Chmielewski
 */
public class SimulationController {
  private Simulation simulation;
  private UIController uiController;

  public SimulationController(UIController uiController) {
    this.uiController = uiController;
  }

  public void loadSimulation() {
    File simulationConfigurationFile = uiController.selectSimulationFile();
    if (simulationConfigurationFile == null) {
      return;
    }
    try {
      this.simulation = (new SimulationFactory()).createSimulation(simulationConfigurationFile);
      uiController.showSimulation(this);
    } catch (Exception e) {
      uiController.error(e);
    }
  }

  public void startSimulation() {
    // TODO: Implement this!
  }

  public void pauseSimulation() {
    // TODO: Implement this!
  }

  public void step() {
    // TODO: Implement this!
  }

  public Map<Integer, Paint> getPaintMap() {
    return Collections.unmodifiableMap(new HashMap<Integer, Paint>());
  }

  public List<GraphicalCell> getGraphicalCells() {
    return new ArrayList<GraphicalCell>();
  }
}
