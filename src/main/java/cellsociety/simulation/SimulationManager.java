package cellsociety.simulation;

import cellsociety.graphics.GraphicalCell;
import cellsociety.graphics.GraphicalCellGrid;
import cellsociety.util.XMLParser;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

public class SimulationManager {
  private XMLParser parser;
  private List<Simulation> simulationList;
  private GraphicalCellGrid graphicalCellGrid;
  private Map<CellState, Color> colorMap;
}
