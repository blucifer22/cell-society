package cellsociety.simulation;

import cellsociety.util.CellShape;
import cellsociety.util.XMLParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class creates and configures simulations.
 *
 * <p>This class creates simulations based on a (properly) formatted XML file.
 *
 * @author Joshua Petitma
 */
public class SimulationFactory {

  private Simulation sim;
  public static final String FIRE = "Fire";
  public static final String CONWAY = "Conway";
  public static final String PERC = "Percolation";
  public static final String WATOR = "Wator";
  public static final String SEG = "Segregation";
  public static final String RPS = "RockPaperScissors";
  public static final Set<String> supportedSimulations =
      Set.of(FIRE, CONWAY, PERC, WATOR, SEG, RPS);

  /**
   * Creates a {@link cellsociety.simulation.Simulation} with the configurations specified from an
   * XML file.
   *
   * @param file - An XML file that will be parsed to create a new simulation.
   */
  public void loadSimulationFile(File file) throws Exception {
    this.sim = null;

    XMLParser parser = new XMLParser(file);
    Map<String, String> metadata = parser.getSimulationMetadata();

    Map<String, Double> config = parser.getSimulationParameters();
    List<int[]> nonDefaultStates = parser.getInitialNonDefaultStates();
    String type = metadata.get("Type");

    if (!supportedSimulations.contains(type)) {
      throw new Exception("Invalid Simulation");
    }
    verifyData(config, nonDefaultStates);

    CellShape shape = parser.getCellShape();
    Simulation simulation = new Simulation(metadata, config, nonDefaultStates, shape);
    initializeRule(config, type);
    initializeCells(simulation, type, config);
    this.sim = simulation;
  }

  private void verifyData(Map<String, Double> config, List<int[]> nonDefaultStates)
      throws IllegalArgumentException {
    config.forEach(
        (String key, Double value) -> {
          if (value < 0) {
            throw new IllegalArgumentException(
                String.format("Invalid Parameter \n\"%s\" must be nonegative", key));
          }
        });
  }

  private void initializeCells(Simulation sim, String type, Map<String, Double> rules) {
    List<Cell> cells = new ArrayList<>();
    for (int i = 0; i < sim.getNumCells(); i++) {
      cells.add(createCell(type, rules));
    }
    try {
      sim.initialize(cells);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(String.format("Invalid Initial State Parameter"));
    }
  }

  private void initializeRule(Map<String, Double> rule, String type) {}

  private Cell createCell(String type, Map<String, Double> rules) {
    return switch (type) {
      case FIRE -> new FireCell(rules);
      case CONWAY -> new ConwayCell(rules);
      case PERC -> new PercolationCell(rules);
      case WATOR -> new WatorCell(rules);
      case SEG -> new SegregationCell(rules);
      case RPS -> new RPSCell(rules);
      default -> null;
    };
  }

  /**
   * Returns a successfully constructed {@link cellsociety.simulation.Simulation}.
   *
   * @return the factory's constructed {@link cellsociety.simulation.Simulation}
   */
  public Simulation getSimulation() {
    assert this.sim != null;
    return this.sim;
  }
}
