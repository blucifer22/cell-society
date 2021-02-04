# Simulation Lab Discussion

## Breakout with Inheritance

## Marc Chmielewski (msc68), Joshua Petitma (jmp157), David Coffman (djc70)


### Block

This superclass's purpose as an abstraction:
```java
 public class Block {
     public void collided(); // Handle collisions in the generic case
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavioral differences from the superclass:
```java
 public class PowerUpBlock extends Block {
     public PowerUpType dropPowerUp(); // Drop the type of Power-Up in the Block
 }
```



#### Affect on Game class (the Closed part)

Allows for less repetition and more generics.

### Others?

Same gig for Enemy and Boss.