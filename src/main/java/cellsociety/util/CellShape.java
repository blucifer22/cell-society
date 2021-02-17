package cellsociety.util;

import java.util.HashMap;

public enum CellShape {
  RECTANGLE("RECTANGLE"),
  HEX("HEXAGON"),
  TRIANGLE("TRIANGLE");

  private final String encodingValue;
  private static final HashMap<String, CellShape> encodingMap = new HashMap<>();

  static {
    for(CellShape shape : CellShape.values()) {
      encodingMap.put(shape.encodingValue, shape);
    }
  }

  CellShape(String encodingValue) {
    this.encodingValue = encodingValue;
  }

  public static CellShape fromEncoding(String s) throws IllegalArgumentException {
    CellShape ret = encodingMap.get(s.trim().toUpperCase());
    if(ret == null) throw new IllegalArgumentException(s+" is not a valid cell shape.");
    return ret;
  }
}
