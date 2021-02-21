package cellsociety.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationConfiguration {

  private final Map<String, Double> simulationParameters;
  private final Map<Integer, Double> randomInitialStates;
  private final List<int[]> initialNonDefaultCellStates;
  private String simulationName;
  private String simulationAuthor;
  private String simulationDescription;
  private SimulationType simulationType;
  private RandomGridGenerationType randomGridGenerationType;
  private SimulationEdgeType edgeType;
  private CellNeighborhoodSize neighborhoodSize;
  private CellShape cellShape;
  private int width;
  private int height;

  public SimulationConfiguration() {
    this.simulationParameters = new HashMap<>();
    this.randomInitialStates = new HashMap<>();
    this.initialNonDefaultCellStates = new ArrayList<>();
    this.cellShape = CellShape.RECTANGLE;
    this.randomGridGenerationType = RandomGridGenerationType.NONE;
    this.edgeType = SimulationEdgeType.NORMAL;
    this.neighborhoodSize = CellNeighborhoodSize.MEDIUM;
  }

  public void addInitialCellState(int[] cellState) {
    assert cellState.length == 3;
    if (cellState[0] < 0 || cellState[1] < 0 || cellState[2] < 0) {
      throw new IllegalArgumentException("Invalid cell state specified (provided cell at row = " +
          cellState[0] + ", col = " + cellState[1] + ", state = " + cellState[2] + ")");
    }
    initialNonDefaultCellStates.add(cellState);
  }

  public void addSimulationParameter(String parameter, Double value) {
    if (parameter == null || value == null) {
      throw new NullPointerException("Simulation parameter names and values must be defined "
          + "(error encountered on parameter" + parameter + " with given value = " + value + ").");
    }
    if (simulationParameters.get(parameter) != null) {
      throw new IllegalArgumentException("Simulation parameters cannot be defined multiple times " +
          "(error encountered on parameter" + parameter + " with given value = " + value + ").");
    }
    simulationParameters.put(parameter, value);
  }

  public void updateSimulationParameter(String parameter, Double value) {
    if (parameter == null || value == null) {
      throw new NullPointerException("Simulation parameter names and values must be defined "
          + "(error encountered on parameter" + parameter + " with given value = " + value + ").");
    }
    if (simulationParameters.get(parameter) == null) {
      throw new IllegalArgumentException("Simulation parameters must be defined in order to be "
          + "updated.");
    }
    simulationParameters.put(parameter, value);
  }

  public void addDefaultParameters(Map<String, Double> defaultParameters) {
    for (String key : defaultParameters.keySet()) {
      this.simulationParameters.putIfAbsent(key, defaultParameters.get(key));
    }
  }

  public RandomGridGenerationType getRandomGridGenerationType() {
    return this.randomGridGenerationType;
  }

  public void setRandomGridGenerationType(RandomGridGenerationType type) {
    if (type == null) {
      throw new NullPointerException("A <Method> must be declared if <RandomInitialStates> are in"
          + " use.");
    }
    this.randomGridGenerationType = type;
  }

  public SimulationEdgeType getEdgeType() {
    return this.edgeType;
  }

  public void setEdgeType(SimulationEdgeType type) {
    if (type == null) {
      throw new NullPointerException(
          "An edge type must be declared when using the <EdgeType> tag.");
    }
    this.edgeType = type;
  }

  public CellNeighborhoodSize getNeighborhodSize() {
    return this.neighborhoodSize;
  }

  public void setNeighborhoodSize(CellNeighborhoodSize size) {
    if(size == null) {
      throw new NullPointerException(
          "A size must be specified when using the <CellNeighborhoodSize> tag.");
    }
    this.neighborhoodSize = size;
  }

  public void addInitialStateFrequency(Integer state, Double frequency) {
    if (state == null || frequency == null) {
      throw new NullPointerException(
          "An initial random state frequency (or count) must be specified "
              + "if you declare a state under the <RandomInitialStates> tag.");
    }
    if (randomInitialStates.putIfAbsent(state, frequency) != null) {
      throw new IllegalArgumentException("States cannot be redefined under the "
          + "<RandomInitialStates> tag.");
    }
  }

  public String getSimulationName() {
    return this.simulationName;
  }

  public void setSimulationName(String name) {
    this.simulationName = name;
  }

  public String getSimulationAuthor() {
    return this.simulationAuthor;
  }

  public void setSimulationAuthor(String author) {
    this.simulationAuthor = author;
  }

  public SimulationType getSimulationType() {
    return this.simulationType;
  }

  public void setSimulationType(String type) {
    SimulationType t = SimulationType.fromStringEncoding(type);
    if (t == null) {
      throw new NullPointerException("Invalid simulation type.");
    }
    this.simulationType = t;
  }

  public String getSimulationDescription() {
    return this.simulationDescription;
  }

  public void setSimulationDescription(String description) {
    this.simulationDescription = description;
  }

  public CellShape getCellShape() {
    return this.cellShape;
  }

  public void setCellShape(CellShape shape) {
    if (shape == null) {
      throw new NullPointerException("Cell shape must be defined.");
    }
    this.cellShape = shape;
  }

  public int getWidth() {
    return this.width;
  }

  public void setWidth(int width) {
    if (width < 1) {
      throw new IllegalArgumentException("Grid width must be >= 1 (provided value " + width + ").");
    }
    this.width = width;
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(int height) {
    if (height < 1) {
      throw new IllegalArgumentException(
          "Grid height must be >= 1 (provided value" + height + ").");
    }
    this.height = height;
  }

  public Map<String, Double> getSimulationParameters() {
    return Collections.unmodifiableMap(simulationParameters);
  }

  public Map<Integer, Double> getRandomInitialStates() {
    if (this.randomGridGenerationType == RandomGridGenerationType.NONE) {
      throw new UnsupportedOperationException("Random initial states cannot be accessed when the "
          + "grid is not generated randomly.");
    }
    return Collections.unmodifiableMap(randomInitialStates);
  }

  public List<int[]> getInitialNonDefaultCellStates() {
    if (this.randomGridGenerationType != RandomGridGenerationType.NONE) {
      throw new UnsupportedOperationException("Explicitly specified initial states cannot be "
          + "accessed when the grid is generated randomly.");
    }
    return Collections.unmodifiableList(initialNonDefaultCellStates);
  }

  public void validateConfiguration() throws Exception {
    for (int[] cellWithState : this.initialNonDefaultCellStates) {
      if (cellWithState[0] >= this.height || cellWithState[1] >= this.width) {
        throw new Exception("Invalid cell specified for simulation geometry; cell with "
            + "x = " + cellWithState[1] + ", y = " + cellWithState[0] + " does not exist in a "
            + "grid with " + height + " rows and " + width + "columns.");
      }
    }
    if (this.simulationType == null) {
      throw new Exception("Simulation type must be specified.");
    }
  }

  public List<Integer> getInactiveNeighbors() {
    return null;
  }

  public enum SimulationType {
    FIRE, CONWAY, PERCOLATION, WATOR, SEGREGATION, ROCKPAPERSCISSORS, ANT, SUGAR;

    public static SimulationType fromStringEncoding(String s) {
      if (s == null) {
        return null;
      }
      return SimulationType.valueOf(s.trim().toUpperCase());
    }
  }

  public enum RandomGridGenerationType {
    // these are actually used, but only accessed through the method below
    NONE, COUNT, FRACTION;

    public static RandomGridGenerationType fromStringEncoding(String s) {
      if (s == null) {
        return null;
      }
      return RandomGridGenerationType.valueOf(s.trim().toUpperCase());
    }
  }

  public enum SimulationEdgeType {
    NORMAL, INFINITE, TOROIDAL;

    public static SimulationEdgeType fromStringEncoding(String s) {
      if (s == null) {
        return null;
      }
      return SimulationEdgeType.valueOf(s.trim().toUpperCase());
    }
  }

  public enum CellNeighborhoodSize {
    SMALL, MEDIUM, LARGE;

    public static CellNeighborhoodSize fromStringEncoding(String s) {
      if (s == null) {
        return null;
      }
      return CellNeighborhoodSize.valueOf(s.trim().toUpperCase());
    }
  }
}
