package cellsociety.graphics;

import cellsociety.simulation.Cell;
import cellsociety.simulation.Simulation;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * <code>CountGraph</code> is a line chart that can be created from the application by pressing
 * the "show graph" button.
 * <p>
 * Usage (from {@link SimulationController#showVisualization()}):
 * <code>
 * this.graph = new CountGraph(this.simulation, resources); Stage s = new Stage(); s.setScene(new
 * Scene(graph, GRAPH_WIDTH, GRAPH_HEIGHT)); s.show();
 * </code>
 *
 * @author David Coffman
 */
public class CountGraph extends LineChart<Number, Number> {

  private final HashMap<Integer, Series<Number, Number>> data;
  private final Simulation simulation;
  private int stepCount;

  /**
   * Instantiates a <code>CountGraph</code>. Requires a data source ({@link Simulation}) to check
   * for cells, as well as a resource bundle for localization.
   *
   * @param s         the {@link Simulation} from which to source update data
   * @param resources the localized <code>ResourceBundle</code>
   */
  public CountGraph(Simulation s, ResourceBundle resources) {
    super(new NumberAxis(), new NumberAxis());
    this.data = new HashMap<>();
    this.simulation = s;
    this.stepCount = 0;
    this.getXAxis().setLabel(resources.getString("StepNumber"));
    this.getYAxis().setLabel(resources.getString("CellCount"));
    this.setTitle(resources.getString("CellCountsByTick"));
  }

  /**
   * Notifies the <code>CountGraph</code> that a state update has occurred in the data source
   * simulation. Checks the data source simulation for current state counts and updates the graph's
   * data <code>Series</code> objects accordingly.
   */
  public void update() {
    List<Cell> cells = simulation.getCells();
    HashMap<Integer, Integer> stepCounts = new HashMap<>();

    for (Cell c : cells) {
      int cellState = c.getEncoding();
      stepCounts.putIfAbsent(cellState, 0);
      stepCounts.put(cellState, stepCounts.get(cellState) + 1);
    }
    for (int i : stepCounts.keySet()) {
      if (data.get(i) == null) {
        Series<Number, Number> s = new Series<>();
        this.getData().add(s);
        this.data.put(i, s);
      }
      this.data.get(i).getData().add(new XYChart.Data<>(stepCount, stepCounts.get(i)));
    }
    stepCount++;
  }

}
