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
  private final UIController uiController;
  private final SimulationFactory simFactory;
  private Simulation simulation;
  private GraphicalCellRectangularGrid graphicalCellGrid;

  public SimulationController(UIController uiController) {
    this.uiController = uiController;
    this.simFactory = new SimulationFactory();
  }

  public void loadSimulation() {
    File simulationConfigurationFile = uiController.selectSimulationFile();
    System.out.println(simulationConfigurationFile);
    if (simulationConfigurationFile == null) {
      return;
    }
    try {
      simFactory.loadSimulationFile(simulationConfigurationFile);
      this.simulation = simFactory.getSimulation();
      uiController.showSimulation(this);
    } catch (Exception e) {
      uiController.notifyUserOfException(e);
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

  public GraphicalCellRectangularGrid graphicalCellGridFactory(double width, double height) {
    return new GraphicalCellRectangularGrid(simulation.getCells(), new HashMap<>(), width, height,
        simulation.getNumRows(), simulation.getNumCols());
  }
}
