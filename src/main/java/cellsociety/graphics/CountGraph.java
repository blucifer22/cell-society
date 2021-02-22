package cellsociety.graphics;

import java.util.ResourceBundle;
import cellsociety.simulation.Cell;
import cellsociety.simulation.Simulation;
import java.util.HashMap;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CountGraph extends LineChart<Number, Number> {

  private final HashMap<Integer, Series<Number, Number>> data;
  private final Simulation simulation;
  private int stepCount;

  public CountGraph(Simulation s, ResourceBundle resources) {
    super(new NumberAxis(), new NumberAxis());
    this.data = new HashMap<>();
    this.simulation = s;
    this.stepCount = 0;
    this.getXAxis().setLabel(resources.getString("StepNumber"));
    this.getYAxis().setLabel(resources.getString("CellCount"));
    this.setTitle(resources.getString("CellCountsByTick"));
  }

  public void update() {
    List<Cell> cells = simulation.getCells();
    HashMap<Integer, Integer> stepCounts = new HashMap<>();

    for(Cell c: cells) {
      int cellState = c.getEncoding();
      stepCounts.putIfAbsent(cellState, 0);
      stepCounts.put(cellState, stepCounts.get(cellState)+1);
    }
    for(int i: stepCounts.keySet()) {
      if(data.get(i) == null) {
        Series<Number, Number> s = new Series<>();
        this.getData().add(s);
        this.data.put(i, s);
      }
      this.data.get(i).getData().add(new XYChart.Data<>(stepCount, stepCounts.get(i)));
    }
    stepCount++;
  }

}
