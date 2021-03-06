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


* Robust error handling for input XML files (see `XMLParser`). Errors are handled primarily by `SimulationConfiguration` and `XMLParser`; in each of the following cases, an user-friendly error message is displayed to the user in an `Error` box:
    * A `DocumentBuilder` fails to parse an input file (which happens when the file is empty or non-XML).
    * A `<Type>` (simulation type) is not defined
    * A `<SimulationParameter>` is defined multiple times
    * An initial state is defined for a cell outside of the bounds of the grid (defined width and height)
    * A negative width or height is defined
    * A simulation parameter element is declared but no value is given (i.e. `<ParameterName></ParameterName>`)
    * A file indicates that random initial states should be used, but no counts or frequencies for random state generations are provided
    * An invalid simulation type is provided
    * An invalid cell shape is provided


* Several visualization options for the simulations, including:
    * Multiple simulation CellGrids at once
    * Real-time graphs of simulations with respect to cell count over time
    * Concurrent views of the graph and the CellGrid


* A basic, GUI-based simulation-editor:
    * Allows the user to generate their own simulations by "poking"
      cells to change their state.
        * Saves to an XML file that can be loaded back at a later time (see `SimulationWriter`).
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
    * NONE


* **Fire**
    * NONE


* **Percolation**
    * NONE


* **RPS**
    * NONE

* **Segregation**
    * NONE

* **SugarScape**
    * Sugar starts off uniformly distributed across all `PATCH`es
    * Sugar is randomly distributed among all `AGENT`s
    * `AGENT`s cannot co-habitate cells

* **Wa-Tor**
    * NONE

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
    * `conways_test_5.xml`
        * The classic Cambridge Pulsar
    * `conways_edge_test_1.xml`
        * A cool glider that wraps around the edges in a toroidal fashion.
    * `conways_edge_test_2.xml`
        * A Cambridge Pulsar that's about to go supernova

* **Fire**
    * `random_fire_test.xml`
        * A random fire test, best experienced with the `FIRE` style
    * `absolute_medium_fire_test.xml`
        * A fire test that demonstrates medium neighborhoods
    * `fire_test_4.xml`
        * A very large fire test that looks super cool with the `FIRE` theme


* **Percolation**
    * `percolation_test_5.xml` and `toroidal_percolation_test_1.xml`
        * Two apparently identical percolation setups that demonstrate the difference between normal
          and toroidal edge behavior.


* **RPS**
    * `RPS_test_7.xml` and `toroidal_RPS_test_1.xml`
        * Two identical configurations that generate very different types of spiral as a result of
          different edge behavior.
    * `random_RPS_test_1.xml`
        * Interesting emergent spirals!

* **Segregation**
    * `random_segregation_test_2.xml`
        * Tends to show interesting clumping behaviors. Tweaking from hexagons to rectangles
          generally converges a bit faster.

* **SugarScape**
    * `random_sugar_test_1.xml`
        * Tends to show an initial high-death rate, followed by an equilibrium


* **Wa-Tor**
    * `random_wator_test_2.xml`
        * Demonstrates the equilibrium boom-bust cycle expected in the Lotka???Volterra equations.
          Very similar behavior as the Isle Royale population study

Known Bugs:

* Presently, there are two text-fields displayed for editing parameters in Foraging Ants. The
  specific etiology of this is under investigation.

Extra credit:

### Impressions

* Marc Chmielewski (msc68)
    * Overall, this assignment was an interesting way to introduce a number of different software
      design patterns and practice working in a distributed (and remote!) team environment. In
      working primarily on the back-end, establishing APIs early, and communicating them to the
      group was key to ensuring that everything would be able to talk to each other in the end. CA
      are a particularly interesting case-study as they lend themselves to a number of OOP
      design-patterns, so choosing between them was a bit of a trial-and-error process. We spent a
      good bit of time working through a number of architectural revisions, which meant that we all
      got a lot of practice refactoring each-others code. That being said, it was very satisfying to
      see everything come together in the end.

* David Coffman (djc70)
  * Whew. That was a long one (though I expected that after Breakout)! To Marc's point about trial-and-error -- I think we didn't fully understand the implications of some of the design choices we made in the planning phase, so we ended up having to, in many ways, both simplify but also make more complicated our approach as we progressed. At one point, we had nearly every "major" Java feature in our project: interfaces, generics, inheritance hierarchies, lambdas, nested classes....... I think all we were missing was a good batch of thread abstractions (though JFX does this behind-the scenes I suppose).  Creating working APIs early is critical, and we didn't necessarily do that... more on that in the analysis, I'm sure. 

* Joshua Petitma (jmp157)
    * I enjoyed seeing the thing finally work. What really hurt us early on was not having a clear set of APIs
    defined, it definitely made it hard for everyone to know exactly what they would get out of another part of
    the project. Going forward I think we all will take planning a lot more strictly so that it can be easier
    for everyone to have a clear understanding of exactly how the project works. DESPITE this however, we were
    able to generally have a steady progression from beginning to end. Refactoring when needed only took 10
    minutesand the design of the model and view were extensible to the point where we didn't have to make
    any real design changes unless we thought it best to for the sake of making everything look nice.
    Besides the planning if I were to do anything differently it'd likely be more pair programming. I did that
    a few times a few times during this project and the benefits are immense; but I kept a list of things I
    need to improve and things I need to really nail down so y'all aren't ready for what's next ????.

[^1]: Purely coincidentally, this order is also alphabetical by last name :)
