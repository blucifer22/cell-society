package cellsociety.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>SimulationConfiguration</code> is a data storage and error checking class. It is
 * configured by an {@link XMLParser} and made publicly accessible to the main
 * simulation package classes.
 *
 * After configuration, <code>SimulationConfiguration</code>s can be accessed through the
 * {@link XMLParser#getSimulationConfiguration()} method, after which any aspect of a simulation
 * can be retrieved with an appropriate getter method call.
 *
 * @author David Coffman
 */
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

  // SimulationConfiguration constructor; protected as it should only be called by the XMLParser.
  protected SimulationConfiguration() {
    this.simulationParameters = new HashMap<>();
    this.randomInitialStates = new HashMap<>();
    this.initialNonDefaultCellStates = new ArrayList<>();
    this.cellShape = CellShape.RECTANGLE;
    this.randomGridGenerationType = RandomGridGenerationType.NONE;
    this.edgeType = SimulationEdgeType.NORMAL;
    this.neighborhoodSize = CellNeighborhoodSize.MEDIUM;
    this.width = 5;
    this.height = 5;
  }

  // Validates, then adds, an initial cell state in its compacted integer array format.
  protected void addInitialCellState(int[] cellState) {
    assert cellState.length == 3;
    if (cellState[0] < 0 || cellState[1] < 0 || cellState[2] < 0) {
      throw new IllegalArgumentException("Invalid cell state specified (provided cell at row = " +
          cellState[0] + ", col = " + cellState[1] + ", state = " + cellState[2] + ")");
    }
    initialNonDefaultCellStates.add(cellState);
  }

  // Validates, then adds, a simulation parameter to the simulation parameters map. Throws an
  // exception if a simulation parameter has been added twice, or if the parameter is null (as
  // this would result in removal of the parameter).
  protected void addSimulationParameter(String parameter, Double value) {
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

  /**
   * Updates a defined simulation parameter. Throws an exception if an undefined parameter if an
   * attempt to update an undefined parameter is made.
   *
   * @param parameter the name of the parameter to be updated
   * @param value the value to update the parameter to
   */
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

  /**
   * Incorporates a map of default parameters into the parameter map. Adds map parameters only if
   * they are not already defined.
   *
   * @param defaultParameters the map of default parameters to incorporate
   */
  public void addDefaultParameters(Map<String, Double> defaultParameters) {
    for (String key : defaultParameters.keySet()) {
      this.simulationParameters.putIfAbsent(key, defaultParameters.get(key));
    }
  }

  /**
   * Returns the {@link RandomGridGenerationType} assigned by the {@link XMLParser}.
   *
   * @return the {@link RandomGridGenerationType} assigned by the {@link XMLParser}
   */
  public RandomGridGenerationType getRandomGridGenerationType() {
    return this.randomGridGenerationType;
  }

  // Validates and sets the RandomGridGenerationType. Used only by the XMLParser.
  protected void setRandomGridGenerationType(RandomGridGenerationType type) {
    if (type == null) {
      throw new NullPointerException("A <Method> must be declared if <RandomInitialStates> are in"
          + " use.");
    }
    this.randomGridGenerationType = type;
  }

  /**
   * Returns the {@link SimulationEdgeType} assigned by the {@link XMLParser}.
   *
   * @return the {@link SimulationEdgeType} assigned by the {@link XMLParser}
   */
  public SimulationEdgeType getEdgeType() {
    return this.edgeType;
  }

  // Validates and sets the SimulationEdgeType. Used only by the XMLParser.
  protected void setEdgeType(SimulationEdgeType type) {
    if (type == null) {
      throw new NullPointerException(
          "An edge type must be declared when using the <EdgeType> tag.");
    }
    this.edgeType = type;
  }

  /**
   * Returns the {@link CellNeighborhoodSize} assigned by the {@link XMLParser}.
   *
   * @return the {@link CellNeighborhoodSize} assigned by the {@link XMLParser}
   */
  public CellNeighborhoodSize getNeighborhodSize() {
    return this.neighborhoodSize;
  }

  // Validates and sets the CellNeighborhoodSize. Used only by the XMLParser.
  protected void setNeighborhoodSize(CellNeighborhoodSize size) {
    if(size == null) {
      throw new NullPointerException(
          "A size must be specified when using the <CellNeighborhoodSize> tag.");
    }
    this.neighborhoodSize = size;
  }

  // Adds an initial state frequency map entry. Used when RandomGridGenerationType is not NONE.
  protected void addInitialStateFrequency(Integer state, Double frequency) {
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

  /**
   * Returns the name of the simulation.
   *
   * @return the name of the simulation.
   */
  public String getSimulationName() {
    return this.simulationName;
  }

  // Sets the name of the simulation. Used only by the XMLParser.
  protected void setSimulationName(String name) {
    this.simulationName = name;
  }

  /**
   * Returns the author of the simulation.
   *
   * @return the author of the simulation.
   */
  public String getSimulationAuthor() {
    return this.simulationAuthor;
  }

  // Sets the author of the simulation. Used only by the XMLParser.
  protected void setSimulationAuthor(String author) {
    this.simulationAuthor = author;
  }

  /**
   * Returns the {@link SimulationType} of the simulation.
   *
   * @return the {@link SimulationType} of the simulation
   */
  public SimulationType getSimulationType() {
    return this.simulationType;
  }

  // Validates and sets the SimulationType of the simulation. Used only by the XMLParser.
  protected void setSimulationType(SimulationType t) {
    if (t == null) {
      throw new NullPointerException("Invalid simulation type.");
    }
    this.simulationType = t;
  }

  /**
   * Returns a description of the simulation.
   *
   * @return a description of the simulation
   */
  public String getSimulationDescription() {
    return this.simulationDescription;
  }

  // Validates and sets the description for the simulation. Used only by the XMLParser.
  protected void setSimulationDescription(String description) {
    this.simulationDescription = description;
  }

  /**
   * Returns the {@link CellShape} for the simulation.
   *
   * @return the {@link CellShape} for the simulation
   */
  public CellShape getCellShape() {
    return this.cellShape;
  }

  // Validates and sets the CellShape for the simulation. Used only by the XMLParser.
  protected void setCellShape(CellShape shape) {
    if (shape == null) {
      throw new NullPointerException("Cell shape must be defined.");
    }
    this.cellShape = shape;
  }

  /**
   * Returns the grid width (in cells) of the simulation.
   *
   * @return the grid width (in cells) of the simulation
   */
  public int getWidth() {
    return this.width;
  }

  // Validates and sets the grid width (in cells) of the simulation's cell grid. Used only by the
  // XMLParser.
  protected void setWidth(int width) {
    if (width < 1) {
      throw new IllegalArgumentException("Grid width must be >= 1 (provided value " + width + ").");
    }
    this.width = width;
  }

  /**
   * Returns the grid height (in cells) of the simulation.
   *
   * @return the grid height (in cells) of the simulation
   */
  public int getHeight() {
    return this.height;
  }

  // Validates and sets the grid height (in cells) of the simulation's cell grid. Used only by the
  // XMLParser.
  protected void setHeight(int height) {
    if (height < 1) {
      throw new IllegalArgumentException(
          "Grid height must be >= 1 (provided value" + height + ").");
    }
    this.height = height;
  }

  /**
   * Returns an <b>unmodifiable</b> view on the simulation parameter map. Updates to the map must
   * be performed through other methods; the returned map cannot be updated directly.
   *
   * @return an <b>unmodifiable</b> view on the simulation parameter map
   */
  public Map<String, Double> getSimulationParameters() {
    return Collections.unmodifiableMap(simulationParameters);
  }

  /**
   * Returns an <b>unmodifiable</b> view on the initial random state parameter map. This map cannot
   * be updated through any <code>public</code> method.
   *
   * @return an <b>unmodifiable</b> view on the initial random state parameter map
   */
  public Map<Integer, Double> getRandomInitialStates() {
    if (this.randomGridGenerationType == RandomGridGenerationType.NONE) {
      throw new UnsupportedOperationException("Random initial states cannot be accessed when the "
          + "grid is not generated randomly.");
    }
    return Collections.unmodifiableMap(randomInitialStates);
  }

  /**
   * Returns an <b>unmodifiable</b> view on the initial non-default states list. This list cannot
   * be updated through any <code>public</code> method.
   *
   * @return an <b>unmodifiable</b> view on the initial non-default states list
   */
  public List<int[]> getInitialNonDefaultCellStates() {
    if (this.randomGridGenerationType != RandomGridGenerationType.NONE) {
      throw new UnsupportedOperationException("Explicitly specified initial states cannot be "
          + "accessed when the grid is generated randomly.");
    }
    return Collections.unmodifiableList(initialNonDefaultCellStates);
  }

  // Validates the entire simulation configuration. Verifies that a type has been set and that
  // all defined initial non-default states are defined for cells that actually exist.
  protected void validateConfiguration() throws Exception {
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

  /**
   * Enumerates all simulation types.
   */
  public enum SimulationType {
    FIRE, CONWAY, PERCOLATION, WATOR, SEGREGATION, ROCKPAPERSCISSORS, ANT, SUGAR;

    /**
     * An extension of the conventional {@link Enum#name()} method that allows for excess
     * whitespace and . Note that this method is replicated for other package <code>Enum</code>s
     * because {@link Enum} is <code>final</code>, and could not be extracted to an interface
     * because {@link Enum#valueOf(Class, String)} is <code>static</code>, and generics cannot
     * be referenced from a <code>static</code> context.
     *
     * @param s the <code>String</code>
     * @return the {@link SimulationType} corresponding to the <code>String</code> parameter
     */
    public static SimulationType fromStringEncoding(String s) {
      try {
        return SimulationType.valueOf(s.trim().toUpperCase());
      } catch (Exception e) {
        throw new IllegalArgumentException(s+" is not a valid simulation type.");
      }
    }
  }

  /**
   * Enumerates all random grid generation methods.
   *
   * <ul>
   *   <li>{@link RandomGridGenerationType#NONE} designates that states should be loaded from
   *   the {@link SimulationConfiguration#getInitialNonDefaultCellStates()} method rather than
   *   randomly.
   *   </li>
   *   <li>{@link RandomGridGenerationType#COUNT} designates that the values of the map returned
   *   by {@link SimulationConfiguration#getRandomInitialStates()} should be interpreted as
   *   discrete cell counts.
   *   </li>
   *   <li>{@link RandomGridGenerationType#FRACTION} designates that the values of the map
   *   returned by @link SimulationConfiguration#getRandomInitialStates()} should be interpreted
   *   as proportions of the total number of cells.
   *   </li>
   * </ul>
   */
  public enum RandomGridGenerationType {
    // these are actually used, but only accessed through the method below
    NONE, COUNT, FRACTION;

    /**
     * An extension of the conventional {@link Enum#name()} method that allows for excess
     * whitespace and . Note that this method is replicated for other package <code>Enum</code>s
     * because {@link Enum} is <code>final</code>, and could not be extracted to an interface
     * because {@link Enum#valueOf(Class, String)} is <code>static</code>, and generics cannot
     * be referenced from a <code>static</code> context.
     *
     * @param s the <code>String</code>
     * @return the {@link RandomGridGenerationType} corresponding to the <code>String</code>
     * parameter
     */
    public static RandomGridGenerationType fromStringEncoding(String s) {
      try {
        return RandomGridGenerationType.valueOf(s.trim().toUpperCase());
      } catch (Exception e) {
        throw new IllegalArgumentException(s+" is not a valid random grid generation type.");
      }
    }
  }

  /**
   * Enumerates all valid simulation edge types.
   *
   * <ul>
   *   <li>{@link SimulationEdgeType#NORMAL} designates that edge cells should have
   *   reduced-size neighborhoods rather than including other or creating new cells.
   *   </li>
   *   <li>{@link SimulationEdgeType#INFINITE} designates that the grid should expand as edge
   *   cells become active.
   *   </li>
   *   <li>{@link SimulationEdgeType#TOROIDAL} designates that the cells on the grid edges
   *   should include cells on the opposite edge as neighbors.
   *   </li>
   * </ul>
   */
  public enum SimulationEdgeType {
    NORMAL, INFINITE, TOROIDAL;

    /**
     * An extension of the conventional {@link Enum#name()} method that allows for excess
     * whitespace and . Note that this method is replicated for other package <code>Enum</code>s
     * because {@link Enum} is <code>final</code>, and could not be extracted to an interface
     * because {@link Enum#valueOf(Class, String)} is <code>static</code>, and generics cannot
     * be referenced from a <code>static</code> context.
     *
     * @param s the <code>String</code>
     * @return the {@link SimulationEdgeType} corresponding to the <code>String</code> parameter
     */
    public static SimulationEdgeType fromStringEncoding(String s) {
      try {
        return SimulationEdgeType.valueOf(s.trim().toUpperCase());
      } catch (Exception e) {
        throw new IllegalArgumentException(s+" is not a valid simulation edge type.");
      }
    }
  }

  /**
   * Enumerates all cell neighborhood sizes. The meaning of this enumeration changes semantically
   * based on the value of {@link CellShape}; see {@link cellsociety.simulation.CellGrid} for
   * implementation details.
   */
  public enum CellNeighborhoodSize {
    SMALL, MEDIUM, LARGE;

    /**
     * An extension of the conventional {@link Enum#name()} method that allows for excess
     * whitespace and . Note that this method is replicated for other package <code>Enum</code>s
     * because {@link Enum} is <code>final</code>, and could not be extracted to an interface
     * because {@link Enum#valueOf(Class, String)} is <code>static</code>, and generics cannot
     * be referenced from a <code>static</code> context.
     *
     * @param s the <code>String</code>
     * @return the {@link CellNeighborhoodSize} corresponding to the <code>String</code> parameter
     */
    public static CellNeighborhoodSize fromStringEncoding(String s) {
      try {
        return CellNeighborhoodSize.valueOf(s.trim().toUpperCase());
      } catch (Exception e) {
        throw new IllegalArgumentException(s+" is not a valid cell neighborhood size.");
      }
    }
  }

  /**
   * Enumerates all cell shapes. Used for both neighborhood determination and rendering.
   */
  public enum CellShape {
    RECTANGLE, HEXAGON, TRIANGLE;

    /**
     * An extension of the conventional {@link Enum#name()} method that allows for excess
     * whitespace and . Note that this method is replicated for other package <code>Enum</code>s
     * because {@link Enum} is <code>final</code>, and could not be extracted to an interface
     * because {@link Enum#valueOf(Class, String)} is <code>static</code>, and generics cannot
     * be referenced from a <code>static</code> context.
     *
     * @param s the <code>String</code>
     * @return the {@link CellShape} corresponding to the <code>String</code> parameter
     */
    public static CellShape fromStringEncoding(String s) {
      try {
        return CellShape.valueOf(s.trim().toUpperCase());
      } catch (Exception e) {
        throw new IllegalArgumentException(s+" is not a valid cell shape.");
      }
    }
  }
}
