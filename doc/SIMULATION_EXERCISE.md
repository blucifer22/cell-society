# Simulation Lab Discussion

## Cell Society

### Names and NetIDs

*   Marc Chmielewski (msc68)
*   David Coffman (djc70)
*   Joshua Petitma (jmp157)

### High Level Design Ideas

*   2D array (rectangular grid) containing Node objects
*   Nodes have getters/setters for state (used for visualization)
*   Nodes have hook/notification/update-type methods to be informed of the state of surrounding Nodes.
*   Nodes should be responsible for -- and hide, as much as possible -- their own state.
*   To synchronize, tell each Node to compute its next state, then tell each Node to shift state.

### CRC Card Classes

This class's purpose or value is to be a node:

```java
public abstract class Node<T extends State> {
  private HashSet<Node> neighbors;
  private T myState;
  private T nextState;

  public void addNeighbor(Node n);
  public void determineNextState();
  public void advanceState();
  public SomeAspectOfState getSomePublicAspectOfStateSoYouCanDoSomethingWithIt();
 }
```

This class keeps track of a Node's state, if it were to be burning for example:

```java
public class State {
  private double burnTimeRemaining;
  private boolean isBurning;
  public boolean isBurning();
}
```

This class manages How

This class manages How Nodes are laid out
and neighbors or identified:

```java
public class NodeManager<T extends Node> {
	protected ArrayList<T> nodes;
	protected StateModifier modifier;
	public void addNode(T node);

	public void updateNodes();
}
```

This manages rules:

```java
public class StateModifier<T extends State> {
	private int rule; //arbitrary fields
	private int changeChance; 

	public void updateState(T state);
	public void updateState(ArrayList<T> states);
}
```

### Use Cases

*   Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

*   Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

*   Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

*   Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML file

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

*   Switch simulations: use the GUI to change the current simulation from Game of Life to Wa-Tor

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```
