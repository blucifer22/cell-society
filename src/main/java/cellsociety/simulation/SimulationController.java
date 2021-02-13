package cellsociety.simulation;

import cellsociety.graphics.GraphicalCellRectangularGrid;
import cellsociety.graphics.UIController;
import java.io.File;
import java.util.HashMap;

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
  private static final double SECONDS_PER_STEP = 1;

  private final UIController uiController;
  private final SimulationFactory simFactory;
  private Simulation simulation;
  private GraphicalCellRectangularGrid graphicalCellGrid;

  private double timer;
  private boolean stepEnabled;

  public SimulationController(UIController uiController) {
    this.uiController = uiController;
    this.simFactory = new SimulationFactory();
  }

  public void loadSimulation(double displayWidth, double displayHeight) {
    clearState();
    File simulationConfigurationFile = uiController.selectSimulationFile();
    System.out.println(simulationConfigurationFile);
    if (simulationConfigurationFile == null) {
      return;
    }
    try {
      simFactory.loadSimulationFile(simulationConfigurationFile);
      this.simulation = simFactory.getSimulation();
      this.graphicalCellGrid = new GraphicalCellRectangularGrid(simulation.getCells(),
          new HashMap<>(), displayWidth, displayHeight, simulation.getNumRows(),
          simulation.getNumCols());
      uiController.showSimulation(this);
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
  }

  public void exitSimulation() {
    uiController.exitSimulation();
  }

  public void update(double elapsedTime) {
    if(simulation == null || graphicalCellGrid == null) {
      return;
    }
    timer = timer + elapsedTime;
    if(timer > SECONDS_PER_STEP && stepEnabled) {
      timer = 0;
      step();
    }
  }

  private void clearState() {
    this.simulation = null;
    this.graphicalCellGrid = null;
    this.stepEnabled = false;
  }

  public GraphicalCellRectangularGrid graphicalCellGridForCurrentSimulation() {
    assert this.graphicalCellGrid != null;
    return this.graphicalCellGrid;
  }
}
