package com.riotgames.conwaysgameoflife;

import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * simulates one generation of Conway's Game of Life on a 2D grid
 * in 64-bit signed integer space.
 * <p>
 *   the implementation is using a sparse representation, tracking only
 *   the alive cells. the simulate method only evaluates live cells and their
 *   neighbors are evaluated.
 * </p>
 */
public class Simulation {

  // all 8 neighboring offsets for a single point on a 2D grid
  // spaced out and structured to illustrate the values intended use
  private static final long[][] OFFSETS = {
      {-1, -1}, {-1, 0}, {-1, 1},
      { 0, -1},          { 0, 1},
      { 1, -1}, { 1, 0}, { 1, 1}
  };


  public static Set<Point> simulate(Set<Point> aliveCells) {
    Map<Point, Integer> neighbors = new HashMap<>();

    for(Point aliveCell : aliveCells) { // O(N) -> N is the number of alive cells
      for(long[] offset : OFFSETS) {    // O(8) -> effectively Constant time
        long dx = aliveCell.x + offset[0];
        long dy = aliveCell.y + offset[1];

        final Point neighbor = new Point(dx, dy);
        neighbors.put(neighbor, neighbors.getOrDefault(neighbor, 0) + 1);
      }
    }

    Set<Point> newAliveCells = new HashSet<>();

    for(Map.Entry<Point, Integer> entry : neighbors.entrySet()) { // O(8*N) worst case -> O(N)
      final Point cell = entry.getKey();
      final int count = entry.getValue();

      if(count == 3 || (count == 2 && aliveCells.contains(cell))) {
        newAliveCells.add(cell);
      }
    }

    return newAliveCells;
  }

  public static void output(Set<Point> aliveCells, String outputFilePath) {
    try(PrintWriter writer = new PrintWriter(outputFilePath)) {
      writer.println("#Life 1.06");
      aliveCells.forEach((aliveCell) -> writer.println(aliveCell.x + " " + aliveCell.y));
      System.out.println("Output written to file: " + outputFilePath);
    } catch (IOException ex) {
      System.err.println("Failed to write output to file: " + outputFilePath);
    }
  }
}
