# CS3105 Practical 1 - 220010065 #

# Overview #
This is a specification accurate submission of CS3105 Practical 1. There are four search algorithms
which can be run using the commands below. All the requirements have been
implemented, along with all the additional options. The program has been tested with Stacscheck
and manual testing to ensure a valid submission.

# Setup #
Before trying to run, make sure code has been compiled.
```console
cd src/
javac *.java
```

# Running #
To run the project, use the following commands:
```console
java P1main.java <BestF|AStar|Alt|BestFOpt|AStarOpt> <D> <r,c> <coverage> [<verbose>]
```

To run the comparison program, which tests various dimensions/coverages use:
```console
java Comparison.java
```

# Tests #
To run the tests, use the following command:
```console
./test.sh
```