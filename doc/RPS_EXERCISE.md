# Simulation Lab Discussion

## Rock Paper Scissors

## Names and NetIDs

Marc Chmielewski (msc68), David Coffman (djc70), Joshua Petitma (jmp157)

### High Level Design Ideas

*   Abstract GameWeapon class
*   Subclasses for each RPS
*   Abstract "Strike" method for gameweapon
*   Abstract "Struck" method for gameweapons - includes type
*   Weapon class has set of things that kill it with enum values

### CRC Card Classes

This class's purpose or value is to manage something:

```java
public class GameManager {

	public void start();
	public void reset();
	public void step();
	public void addPlayer(Player player);
	public void removePlayer(Player player);
}
```

This class is cool:
```java
 protected abstract class GameWeapon {
 	protected TYPE type;
 	protected Set<TYPE> dangerous;
 	enum TYPE {
		ROCK, PAPER, SCISSORS
	}

	public void getType() {
		return this.type;
	}
	public abstract void strike();
	// Returns if it should **PERISH**
	public abstract boolean struck(GameWeapon.TYPE type);
 }

```

This class is vital:
```java

public class Player {
	public int getHealth();
	public int getScore();
	public int decrementScore(int amount);
	public int incrementScore(int amount);
	public GameWeapon chooseWeapon();
}
```

This class's purpose or value is to be useful:

```java
 public class Scissor extends GameWeapon {
	public boolean struck(GameWeapon.TYPE wtype) {}
 }
```

### Use Cases

*   A new game is started with five players, their scores are reset to 0.

```java
GameManger manager = new GameManager();
manager.reset();
for (5 times)
	Player player = new Player();
	manager.addPlayer(player);
manager.start()
```

*   A player chooses his RPS "weapon" with which he wants to play for this round.

```java

for each player
GameWeapon wep = player.chooseWeapon();
set.add(wep.getType());
```

*   Given three players' choices, one player wins the round, and their scores are updated.

```java
for 3 players
if (!player.isKilledBy(set)) {
	player.incrementScore(1);
}
```

*   A new choice is added to an existing game and its relationship to all the other choices is updated.

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

*   A new game is added to the system, with its own relationships for its all its "weapons".

```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```
