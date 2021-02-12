package cellsociety.simulation;

import cellsociety.graphics.GraphicalCell;
import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SimulationManager {
  private XMLParser parser;
  private List<Simulation> simulationList;
  private Map<CellState, Color> colorMap;

  public SimulationManager(File f) throws Exception {
    this.parser = new XMLParser(f);
  }

  public Map<Integer, Paint> getColorMap() {
    return null;
  }

  public List<GraphicalCell> getGraphicalCells() {
    return null;
  }

  public void startSimulation() {

  }
}
