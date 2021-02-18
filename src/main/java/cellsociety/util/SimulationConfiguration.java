package cellsociety.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationConfiguration {

  private final Map<String, Double> simulationParameters;
  private final List<int[]> initialNonDefaultCellStates;
  private String simulationName;
  private String simulationAuthor;
  private SimulationType simulationType;
  private String simulationDescription;
  private CellShape cellShape;
  private int width;
  private int height;
  private GridGenerationType generationType;

  public SimulationConfiguration() {
    this.simulationParameters = new HashMap<>();
    this.initialNonDefaultCellStates = new ArrayList<>();
  }

  public void setSimulationName(String name) {
    this.simulationName = name;
  }

  public void setSimulationAuthor(String author) {
    this.simulationAuthor = author;
  }

  public void setSimulationType(SimulationType type) {
    if (type == null) {
      throw new NullPointerException("Simulation type must be defined.");
    }
    this.simulationType = type;
  }

  public void setSimulationType(String type) {
    SimulationType t = SimulationType.fromStringEncoding(type);
    if (t == null) {
      throw new NullPointerException("Invalid simulation type.");
    }
    this.simulationType = t;
  }

  public void setSimulationDescription(String description) {
    this.simulationDescription = description;
  }

  public void setCellShape(CellShape shape) {
    if (shape == null) {
      throw new NullPointerException("Cell shape must be defined.");
    }
    this.cellShape = shape;
  }

  public void setWidth(int width) {
    if (width < 1) {
      throw new IllegalArgumentException("Grid width must be >= 1 (provided value "+width+").");
    }
    this.width = width;
  }

  public void setHeight(int height) {
    if (height < 1) {
      throw new IllegalArgumentException("Grid height must be >= 1 (provided value"+height+").");
    }
    this.height = height;
  }

  public void addInitialCellState(int[] cellState) {
    assert cellState.length == 3;
    if(cellState[0] < 0 || cellState[1] < 0 || cellState[2] < 0) {
      throw new IllegalArgumentException("Invalid cell state specified (provided cell at row = " +
          cellState[0] + ", col = " + cellState[1] + ", state = " + cellState[2] + ")");
    }
    initialNonDefaultCellStates.add(cellState);
  }
  
  public void addSimulationParameter(String parameter, Double value) {
    if (parameter == null || value == null) {
      throw new NullPointerException("Simulation parameter names and values must be defined "
          + "(error encountered on parameter"+parameter+" with given value = "+value+").");
    }
    if (simulationParameters.get(parameter) != null) {
      throw new IllegalArgumentException("Simulation parameters cannot be defined multiple times "+
          "(error encountered on parameter"+parameter+" with given value = "+value+").");
    }
    simulationParameters.put(parameter, value);
  }

  public void updateSimulationParameter(String parameter, Double value) {
    if (parameter == null || value == null) {
      throw new NullPointerException("Simulation parameter names and values must be defined "
          + "(error encountered on parameter"+parameter+" with given value = "+value+").");
    }
    if (simulationParameters.get(parameter) == null) {
      throw new IllegalArgumentException("Simulation parameters must be defined in order to be "
          + "updated.");
    }
    simulationParameters.put(parameter, value);
  }

  public void addDefaultParameters(Map<String, Double> defaultParameters) {
    for(String key: defaultParameters.keySet()) {
      this.simulationParameters.putIfAbsent(key, defaultParameters.get(key));
    }
  }

  public String getSimulationName() {
    return this.simulationName;
  }

  public String getSimulationAuthor() {
    return this.simulationAuthor;
  }

  public SimulationType getSimulationType() {
    return this.simulationType;
  }

  public String getSimulationDescription() {
    return this.simulationDescription;
  }

  public CellShape getCellShape() {
    return this.cellShape;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public Map<String, Double> getSimulationParameters() {
    return Collections.unmodifiableMap(simulationParameters);
  }

  public List<int[]> getInitialNonDefaultCellStates() {
    return Collections.unmodifiableList(initialNonDefaultCellStates);
  }

  public void clearNonDefaultCellStates() {
    this.initialNonDefaultCellStates.clear();
  }

  public void validateGeometry() throws Exception {
    for(int[] cellWithState: this.initialNonDefaultCellStates) {
      if(cellWithState[0] >= this.height || cellWithState[1] >= this.width) {
        throw new Exception("Invalid cell specified for simulation geometry; cell with "
            + "x = " + cellWithState[1] + ", y = " + cellWithState[0] + " does not exist in a "
            + "grid with " + height + " rows and " + width + "columns.");
      }
    }
  }

  public enum SimulationType {
    FIRE, CONWAY, PERCOLATION, WATOR, SEGREGATION, ROCKPAPERSCISSORS;

    public static SimulationType fromStringEncoding(String s) {
      if (s == null) {
        return null;
      }
      return SimulationType.valueOf(s.trim().toUpperCase());
    }
  }

  public enum GridGenerationType {
    NONE, NUMBER, FRACTION;
  }
}
