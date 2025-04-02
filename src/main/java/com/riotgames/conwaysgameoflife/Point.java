package com.riotgames.conwaysgameoflife;

/** represent a single point in 2D space, in 64-bit signed integer space */
public class Point {
  final long x;
  final long y;

  public Point(long x, long y) {
    this.x = x;
    this.y = y;
  }

  /**
   * indicates whether two Point are equivalent. this is necessary because Java's default equality
   * uses reference comparison, meaning that without the implementation of equals there would be no
   * way to verify if two independent Point instances were equivalent. this would cause issues when
   * using collections that leverage .equals() to verify uniqueness, as an example.
   *
   * <p>two Point objects are equivalent if their x,y coordinates are equal
   *
   * @param obj the reference object to compare
   * @return true if this object is the same as the argument, otherwise false
   */
  @Override
  public boolean equals(Object obj) {
    // comparing to self, always equal
    if (this == obj) {
      return true;
    }

    // comparing to null, always false
    // comparing to a different class type, always false
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    // safe to cast and compare obj to self
    final Point point = (Point) obj;
    return x == point.x && y == point.y;
  }

  /**
   * generate a hash code value for a Point
   *
   * <p>hashCode() is generated using the 64-bit coordinates x,y for a Point, multiplying X by a
   * prime number (31) to ensure an evenly distributed result for use in hash based Java
   * collections.
   *
   * <p>in this example we multiply hashX, but we could instead have multiplied hashY instead to
   * achieve the same goal of even distribution. the goal in this method is to spread out the two
   * values to reduce the potential collision opportunities in hash based collections.
   *
   * @return an integer hash code value for a Point
   */
  @Override
  public int hashCode() {
    int hashX = Long.hashCode(x);
    int hashY = Long.hashCode(y);

    return hashX * 31 + hashY;
  }
}
