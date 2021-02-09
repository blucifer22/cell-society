package cellsociety.simulation;

// May be changed to Probability rule
public class FireRule extends StateRule {
	public final static double DEFAULT_FLAMMABILITY = 0.50;
	private double flammability;

	public void FireRule() {
		this.flammability = DEFAULT_FLAMMABILITY;
	}

	public void FireRule(double flamability) {
		this.flammability = flamability;
	}

	public double getFlammability() {
		return flammability;
	}
}
