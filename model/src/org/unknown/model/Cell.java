package org.unknown.model;

import java.io.Serializable;

/**
 * Author: Artem Voronov
 */
public class Cell implements Serializable {
  private int id;

  public Cell(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Cell cell = (Cell) o;

    return id == cell.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
