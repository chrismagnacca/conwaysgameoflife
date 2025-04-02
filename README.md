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

1. unzip the archive
2. build and create the JavaFX app using the gradle wrapper, if you're on Windows use gradlew.bat
```bash
./gradlew clean build jlink
```
```ps
./gradlew.bat clean build jlink
```
<ins>**expected output:**</ins>
```bash
PS C:\Users\Chris\Projects\ConwaysGameOfLife> .\gradlew.bat clean build jlink

> Configure project :
Project : => 'com.riotgames.conwaysgameoflife' Java module

> Task :jlink
Warning: The 2 argument for --compress is deprecated and may be removed in a future release

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.8/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 9s
11 actionable tasks: 10 executed, 1 up-to-date
```
3. prepare an input file in the supported format
4. run the application by providing the fully qualified file path, allowing the simulation to run.
5. after the visualization closes, `./build/image/bin/output.txt` will contain the program output.

<ins>**example command**</ins>:
```bash
./build/image/bin/app C:\Users\Chris\Documents\example.txt
```
<ins>**expected output:**</ins>
```
Output written to file: output.txt
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

