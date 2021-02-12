package cellsociety.simulation;

import cellsociety.graphics.GraphicalCell;
import cellsociety.util.XMLParser;
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
 * @author Marc
 */
public class SimulationController {
  private Simulation simulation;

  public SimulationController(File f) throws Exception {
    this.simulation = (new SimulationFactory()).createSimulation(f);
  }

  public void startSimulation() {}

  public void pauseSimulation() {}

  public void step() {}

  public Map<Integer, Paint> getPaintMap(){
    return Collections.unmodifiableMap(new HashMap<Integer, Paint>());
  }

  public List<GraphicalCell> getGraphicalCells() {
    return new ArrayList<GraphicalCell>();
  }
}
