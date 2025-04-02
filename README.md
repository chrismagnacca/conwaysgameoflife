# Conway's Game of Life
***

## Built With & On
***

* [Amazon Corretto 21](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html)
* [JavaFX](https://openjfx.io/)

## Features
***

* supports 64-bit signed integer coordinates
* reads input provided in [Life 1.06 format](https://conwaylife.com/wiki/Life_1.06)
* visualizes the simulation using JavaFX on a centered 1920x1080 grid; only nearby cells are displayed
* no installation required, run using the build/image/app portable

## How to Run
***

1. if downloaded from the release zip, unzip the archive, otherwise skip to 2
2. build and create the JavaFX app using the gradle wrapper 
```bash
./gradlew clean build jlink
```
```ps
./gradlew.bat clean build jlink
```
3. prepare an input file in the supported format
4. run the application by providing the fully qualified file path, allowing the simulation to run.
5. after the visualization closes, `./build/image/bin/output.txt` will contain the program output.

<ins>example</ins>:
```bash
./build/image/bin/app C:\Users\Chris\Documents\example.txt
```

<ins>**NOTE**</ins>: the visualization shows a 1920x1080 centered canvas, which provides -/+ 960 , +/- 540 as a
way to provide some insight into the simulation taking place. I opted away from scaling the canvas
as I built the visualization as a way to confirm and watch the simulation as I was working.


## Project Structure
***

The project is organized as illustrated below. I kept the tree depth here purposefully low to 
focus on the implementation for the project over going into all the individual build directories
that are present when compiling. 

The solution comprises 3 main classes: Point, Visualization, and Simulation. The Point class
serves as the implementation for a single Point in 2D space, using 64-bit signed integers (longs)
to represent the x,y coordinates. The Visualization class holds all the drawing functionality for
the JavaFX visualization when the file is being processed. Lastly, the Simulation class contains
the implementation of Conway's Game of Life. The number of generations is set to 10 in the 
Visualization class as requested for the total number of runs.

```console
├── README.md
├── build
│     ├── image/...
├── build.gradle
├── example.txt
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    └── main
        └── java
            ├── com
            │     └── riotgames
            │         └── conwaysgameoflife
            │             ├── Point.java
            │             ├── Simulation.java
            │             └── Visualization.java
            └── module-info.java
```

