## Refactoring Lab Discussion
### Cell Society: Team 2
### Names
Marc Chmielewski
Joshua Petitma
David Coffman

### Issues in Current Code

#### XMLParser
* Failure to use generalized Collections classes on LHS of statements. (Ex. Used HashMap instead of Map)

* Certain methods were a bit verbose for their own good.

#### CellGrid
* Could benefit from arrow-syntax for switch statements -> too verbose currently

* Some methods were a bit long. This was mostly for readability's sake, so we've opted to leave it in.


### Refactoring Plan

* What are the code's biggest issues?
    * Some methods were a bit too verbose for their own good, this will be rectified in the near future.
    * There are some unused imports/other vestigial dead code that needs to be removed.

* Which issues are easy to fix and which are hard?
    * Most of the issues we experienced are relatively easy to fix.
    * There are a few architectural decisions we may wish to reconsider, but at this point in the project it would require a re-write we simply do not have time for.

* What are good ways to implement the changes "in place"?
    * Remove dead code.
    * Transition to general Collections classes.
    * Possibly refactor certain longer methods into shorter sub-methods.
        * However, this may not be done if it comes at the cost of readability.

### Refactoring Work

* General Collections: Fix and Alternatives
    * We replaced LHS use of specific Collections classes with their general counterparts.
        * Specifically, we replaced uses of ArrayList with List and HashMap with Map.
    * This allows us to better leverage polymorphism and is in general a Java best-practice.


* Dead Code Removal: Fix and Alternatives
    * Pretty much what it says on the tin ==> remove dead code and prosper :)