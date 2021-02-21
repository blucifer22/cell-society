package cellsociety.util;

public enum CellShape {
  RECTANGLE, HEXAGON, TRIANGLE;

  public static CellShape fromStringEncoding(String s) {
    if (s == null) {
      return null;
    }
    return CellShape.valueOf(s.trim().toUpperCase());
  }
}
