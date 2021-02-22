package cellsociety.graphics;

import java.util.ResourceBundle;
import cellsociety.graphics.cells.ExtraSettingsPopup;
import cellsociety.simulation.Simulation;
import cellsociety.simulation.SimulationFactory;
import java.io.File;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The controller class that creates the simulation model for the view to use.
 *
 * <p>This class contains an XML parser and can create {@link cellsociety.simulation.Simulation}
 * objects.
 *
 * @author David Coffman
 * @author Marc Chmielewski
 */
public class SimulationController {

  private static final double GRAPH_DIM = 600;

  private double secondsPerStep = 1;

  private final UIController uiController;
  private final SimulationFactory simFactory;
  private Simulation simulation;
  private GraphicalCellGrid graphicalCellGrid;
  private CountGraph graph;
  private ExtraSettingsPopup parametersPopup;
  private ResourceBundle resources;

  private double timer;
  private boolean stepEnabled;

  public SimulationController(UIController uiController, ResourceBundle resources) {
    this.uiController = uiController;
    this.simFactory = new SimulationFactory();
    this.resources = resources;
  }

  public void loadSimulation(double displayWidth, double displayHeight) {
    clearState();
    File simulationConfigurationFile = uiController.fileFromOpenDialog();
    if (simulationConfigurationFile == null) {
      return;
    }
    try {
      simFactory.loadSimulationFile(simulationConfigurationFile);
      this.simulation = simFactory.getSimulation();
      this.graphicalCellGrid = new GraphicalCellGrid(simulation.getCellShape(),
          simulation.getCells(), displayWidth, displayHeight,
          simulation.getNumRows(), simulation.getNumCols());
      graphicalCellGrid.update();
      uiController.showSimulation(this);
	  uiController.setTitle(simulation.getName());
    } catch (Exception e) {
      uiController.notifyUserOfException(e);
    }
  }

  public void startSimulation() {
    stepEnabled = true;
  }

  public void pauseSimulation() {
    stepEnabled = false;
  }

  public void step() {
    simulation.step();
    graphicalCellGrid.update();
    if(graph != null) {
      graph.update();
    }
  }

  public void exitSimulation() {
    uiController.exitSimulation();
    if(parametersPopup != null ) {
      parametersPopup.destroy();
    }
  }

  public void update(double elapsedTime) {
    if (simulation == null || graphicalCellGrid == null) {
      return;
    }
    timer = timer + elapsedTime;
    if (timer > secondsPerStep && stepEnabled) {
      timer = 0;
      step();
    }
  }

  public void speedUpSimulation() {
    secondsPerStep /= 2.0;
  }

  public void slowDownSimulation() {
    secondsPerStep *= 2.0;
  }

  public void showVisualization() {
    this.graph = new CountGraph(this.simulation, resources);
    Stage s = new Stage();
    s.setScene(new Scene(graph, GRAPH_DIM, GRAPH_DIM));
    s.show();
  }

  public void showParametersPopout() {
    this.parametersPopup = new ExtraSettingsPopup(this);
  }

  public void updateSimulationParameter(String name, String value){
    try {
      double val = Double.parseDouble(value);
      simulation.setParameter(name, val);
    } catch (NumberFormatException e) {
      uiController.notifyUserOfException(new Exception("Error: you must enter a valid number."));
    } catch (Exception e) {
      uiController.notifyUserOfException(e);
    }
  }

  public Map<String, Double> getSimulationParameters() {
    return simulation.getSimulationParameters();
  }

  private void clearState() {
    this.simulation = null;
    this.secondsPerStep = 1.0;
    this.graphicalCellGrid = null;
    this.stepEnabled = false;
  }

  public void saveSimulationToDisk() {
    try {
      simulation.writeToDisk(uiController.fileFromSaveDialog());
    } catch(Exception e) {
      uiController.notifyUserOfException(e);
    }
  }

  public void openAdditionalSimulation() {
    uiController.createNewControlledStage();
  }

  public GraphicalCellGrid graphicalCellGridForCurrentSimulation() {
    assert this.graphicalCellGrid != null;
    return this.graphicalCellGrid;
  }
}
