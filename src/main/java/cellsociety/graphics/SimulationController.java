package cellsociety.graphics;

import cellsociety.simulation.Simulation;
import cellsociety.simulation.SimulationFactory;
import java.io.File;
import java.util.HashMap;

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

  private double secondsPerStep = 1;

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
      graphicalCellGrid.update();
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

  private void clearState() {
    this.simulation = null;
    this.secondsPerStep = 1.0;
    this.graphicalCellGrid = null;
    this.stepEnabled = false;
  }

  public GraphicalCellRectangularGrid graphicalCellGridForCurrentSimulation() {
    assert this.graphicalCellGrid != null;
    return this.graphicalCellGrid;
  }
}
