package com.riotgames.conwaysgameoflife;

import static com.riotgames.conwaysgameoflife.Simulation.output;
import static com.riotgames.conwaysgameoflife.Simulation.simulate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;

public class Visualization extends Application {
  private static final int GENERATIONS = 10;    // number of generations to simulate
  private static final int VIEW_WIDTH = 1920;   // canvas width
  private static final int VIEW_HEIGHT = 1080;  // canvas height
  private static final int CELL_SIZE = 5;       // pixel size of each cell

  private static Set<Point> ALIVE = new HashSet<>();

  @Override
  public void start(Stage stage) {
    // set the drawing canvas and graphics context
    final Canvas canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
    final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    final Scene scene = new Scene(new javafx.scene.Group(canvas));

    // configure then display the window
    stage.setScene(scene);
    stage.setTitle("Conway's Game of Life");
    stage.show();

    // run simulate for the set number of generations
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), (event) -> {
      graphicsContext.setFill(Color.WHITE);
      graphicsContext.fillRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
      graphicsContext.setFill(Color.BLACK);

      if(ALIVE.isEmpty()) return; // nothing alive, no need to continue on the timeline

      // center the grid such that 0,0 appears in the center of the window
      final long offsetX = -VIEW_WIDTH / 2 / CELL_SIZE;
      final long offsetY = -VIEW_HEIGHT / 2 / CELL_SIZE;

      for(Point p : ALIVE) {
        int dx = (int) (p.x - offsetX);
        int dy = (int) (p.y - offsetY);

        if(dx >= 0 && dx < (VIEW_WIDTH/CELL_SIZE) && dy >= 0 && dy < (VIEW_HEIGHT/CELL_SIZE)) {
          graphicsContext.fillRect(dx * CELL_SIZE, dy * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
      }

      ALIVE = simulate(ALIVE);
    }));

    timeline.setCycleCount(GENERATIONS);

    // pause for 3 seconds after the simulations complete prior to closing the Canvas
    timeline.setOnFinished((timelineOnFinishedEvent) -> {
      output(ALIVE, "output.txt");

      final PauseTransition delay = new PauseTransition(Duration.seconds(2));
      delay.setOnFinished((delayOnFinishedEvent) -> {
        Platform.exit();
      });
      delay.play();
    });

    timeline.play();
  }

  public static void main(String[] args) {
    if(args.length == 0) {
      System.err.println("Usage: app <full path to input file>");
      System.exit(1);
    }

    try {
      final File inputFile = new File(args[0]);

      if(!inputFile.exists()) {
        System.err.println("File not found: " + inputFile.getAbsolutePath());
        Platform.exit();
        return;
      }

      final BufferedReader reader = new BufferedReader(new FileReader(args[0]));
      String line;

      while((line = reader.readLine()) != null) {
        line = line.trim(); // remove any trailing whitespace

        if(line.isBlank() || line.startsWith("#")) continue;

        final String[] coordinates = line.split("\\s+"); // split coordinates on whitespace

        final long x = Long.parseLong(coordinates[0]);
        final long y = Long.parseLong(coordinates[1]);

        ALIVE.add(new Point(x, y)); // add alive cell to Set of all alive cells
      }

    } catch (IOException e) {
      System.err.println("An error occurred processing input file: " + e.getMessage());
    }

    launch(args); // start JavaFX application
  }
}
