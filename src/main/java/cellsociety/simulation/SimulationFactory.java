package cellsociety.simulation;

import cellsociety.util.CellShape;
import cellsociety.util.SimulationConfiguration;
import cellsociety.util.SimulationConfiguration.SimulationType;
import cellsociety.util.XMLParser;
import java.io.File;
import java.net.URL;
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
 * @author David Coffman
 */
public class SimulationFactory {

  private Simulation sim;

  /**
   * Creates a {@link cellsociety.simulation.Simulation} with the configurations specified from an
   * XML file.
   *
   * @param file - An XML file that will be parsed to create a new simulation.
   */
  public void loadSimulationFile(File file) throws Exception {
    this.sim = null;
    XMLParser simParser = new XMLParser(file);
    SimulationConfiguration simConfig = simParser.getSimulationConfiguration();

    SimulationConfiguration defaultConfig =
        parseDefault(simConfig.getSimulationType()).getSimulationConfiguration();
    simConfig.addDefaultParameters(defaultConfig.getSimulationParameters());
    this.sim = new Simulation(simConfig);
    initializeCells(this.sim, simConfig.getSimulationType(), simConfig.getSimulationParameters());
  }

  private XMLParser parseDefault(SimulationType type) throws Exception {
    URL url = getClass().getResource(String.format("Default%s.xml", type.name()));
    File defaultFile = new File(url.toURI());
    return new XMLParser(defaultFile);
  }

  private void initializeCells(Simulation sim, SimulationType type, Map<String, Double> params) {
    List<Cell> cells = new ArrayList<>();
    for (int i = 0; i < sim.getNumCells(); i++) {
      cells.add(createCell(type, params));
    }
    try {
      sim.initialize(cells);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid Initial State Parameter");
    }
  }

  private Cell createCell(SimulationType type, Map<String, Double> params) {
    return switch (type) {
      case FIRE -> new FireCell(params);
      case CONWAY -> new ConwayCell(params);
      case PERCOLATION -> new PercolationCell(params);
      case WATOR -> new WatorCell(params);
      case SEGREGATION -> new SegregationCell(params);
      case ROCKPAPERSCISSORS -> new RPSCell(params);
      case ANT -> new AntCell(params);
      case SUGAR -> new SugarCell(params);
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
