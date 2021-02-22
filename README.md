Cell Society
====

This project implements a cellular automata simulator.

Names:

In order of those who speak Polish the best[^1] (but who also didn't realize that Polish diacritics
don' t always play nice with UTF-8):

- Marc Chmielewski (msc68)
- David Coffman (djc70)
- Joshua Petitma (jmp157)

### Timeline

Start Date:

February 8, 2021

Finish Date:

February 21, 2021

Hours Spent:

According to the `git estimate` command

```
commits by joshuapetitma@yahoo.com
=== 13.85 days (110.78 hours)

commits by joshua.petitma@duke.edu
=== 9.08 days (72.62 hours)

commits by msc68@duke.edu
=== 10.26 days (82.09 hours)

commits by marc.chmielewski@duke.edu
=== 4.72 days (37.77 hours)

commits by david.coffman@duke.edu
=== 9.59 days (76.69 hours)

commits by jad111@duke.edu
=== 0.25 days (2.01 hours)

commits by rcd@cs.duke.edu
=== 0.54 days (4.30 hours)
```

A lot of time. (~80-150 hours apiece, and ~300-350 cumulatively) These times are not 100% accurate
but demonstrate that collectively we all spent a lot of time working on the code.

### Primary Roles

- Marc Chmielewski (msc68)
    - Back-End/Model, Simulation Implementations, and Tests
- David Coffman (djc70)
    - Front-End, Parser, and Configurations
- Joshua Petitma (jmp157)
    - General Architecture, Middleware, and Utility Code

### Resources Used

* **JUnit** - To create automated unit tests and verify that changes did not break code.
* [Factory Design Pattern](https://en.wikipedia.org/wiki/Factory_(object-oriented_programming))
* [Strategy Design Pattern](https://en.wikipedia.org/wiki/Strategy_pattern)
* Our wonderful staff of UTAs

### Running the Program

Main class: `cellsociety.Main`

Data files needed: To load a simulation the configurations can be found within `/data/`. All other
files needed to run are nested within the `/sr/main/resources/` directory, though the user should
not need to explicitly edit or even view these.

Features implemented:

* **EIGHT** different Cellular Automata simulations
    * Conway's Game of Life
    * Fire Spreading
    * Percolation
    * Schelling's Model of Segregation
    * Wa-Tor World
    * Rock-Paper-Scissors
    * SugarScape
    * Foraging Ants


* **THREE** different shapes of cell, with specialized neighbor behavior:
    * Rectangular
    * Hexagonal
    * Triangular


* **THREE** different neighborhood types and **TWO** different boundary conditions:
    * Neighborhoods:
        * SMALL
        * MEDIUM
        * LARGE
    * Boundary Conditions:
        * "Standard"
        * Toroidal


* Robust error handling for input XML files (see `XMLParser`).
    * // TODO: Explain error handling details

* Several visualization options for the simulations, including:
    * Multiple simulation CellGrids at once
    * Real-time graphs of simulations with respect to cell count over time
    * Concurrent views of the graph and the CellGrid


* A basic, GUI-based simulation-editor:
    * Allows the user to generate their own simulations by "poking"
      cells to change their state.
        * Saves to an XML file that can be loaded back at a later time (see `simulation writer`).
    * The ability to create random simulation configurations is also implemented.
    * The ability to dynamically change simulation parameters with text-fields

* Localization for:
    * English
    * Spanish
    * French
    * Polish


* Theming (in CSS 3!):
    * Light Mode (default)
    * Dark Mode
    * Fire Mode

### Notes/Assumptions

Assumptions or Simplifications:

* **Foraging Ants**
    * `ANT`s are unable to co-habitate cells.
        * This is a simplification from the original paper, designed to make the simulation easier
          to visualize
    * Ants will prioritize high pheromone count cells *they have not yet been to* on this excursion
      over ones that they have.


* **Conway's Game of Life**
    * None


* **Fire**
    * None


* **Percolation**
    * None


* **RPS**
    * None

* **Segregation**
    * None

* **SugarScape**
    * Sugar starts off uniformly distributed across all `PATCH`es
    * Sugar is randomly distributed among all `AGENT`s
    * `AGENT`s cannot co-habitate cells

Interesting data files:

* **Foraging Ants**
    * `random_ant_test_1.xml`
        * A toroidal file with random configuration that tends to accent emergent behavior. Note
          that `ANT`s may seem like they get stuck, but this is generally just a function of being
          concurrently adjacent to a food supply and nest.
    * `toroidal_ant_test_2.xml`
        * A toroidal file with lots of emergent behavior. The `ANT`s really seem intelligent here!
    * `ant_test_7.xml`
        * Converges to the shortest path with enough time!

* **Conway's Game of Life**
    * None


* **Fire**
    * None


* **Percolation**
    * None


* **RPS**
    * None

* **Segregation**
    * None

* **SugarScape**
    * Sugar starts off uniformly distributed across all `PATCH`es
    * Sugar is randomly distributed among all `AGENT`s
    * `AGENT`s cannot co-habitate cells

Known Bugs:

* Presently, there are two text-fields displayed for editing parameters in Foraging Ants. The
  specific etiology of this is under investigation.

Extra credit:

### Impressions

[^1]: Purely coincidentally, this order is also alphabetical :)