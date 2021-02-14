package cellsociety.simulation;

import cellsociety.util.XMLParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimulationFactory {

  private Simulation sim;
  public static final String FIRE = "Fire";
  public static final String CONWAY = "Conway";
  public static final String PERC = "Percolation";
  public static final String WATOR = "Wator";
  public static final String SEG = "Segregation";
  public static final Set<String> supportedSimulations = Set.of(FIRE, CONWAY, PERC, WATOR, SEG);

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
      throw new Exception("Invalid simulation type specified.");
    }

    Simulation simulation = new Simulation(metadata, config, nonDefaultStates);
    initializeRule(config, type);
    initializeCells(simulation, type);
    this.sim = simulation;
  }

  private void initializeCells(Simulation sim, String type) {
    List<Cell> cells = new ArrayList<>();
    for (int i = 0; i < sim.getNumCells(); i++) {
      cells.add(createCell(type));
    }
    sim.initialize(cells);
  }

  private void initializeRule(Map<String, Double> rule, String type) {
    switch (type) {
      case FIRE -> FireCell.rule = new FireRule(rule);
      case CONWAY -> ConwayCell.rule = new ConwayRule(rule);
      case PERC -> PercolationCell.rule = new PercolationRule(rule);
      case WATOR -> WatorCell.rule = new WatorRule(rule);
      case SEG -> SegregationCell.rule = new SegregationRule(rule);
      default -> {
      }
    }
  }

  private Cell createCell(String type) {
    return switch (type) {
      case FIRE -> new FireCell();
      case CONWAY -> new ConwayCell();
      case PERC -> new PercolationCell();
	  case WATOR -> new WatorCell();
	  case SEG -> new SegregationCell();
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
