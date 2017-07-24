package org.unknown.model;

import java.io.Serializable;

/**
 * Author: Artem Voronov
 */
public class Msisdn implements Serializable {
  private int ctn; //just one field for simplicity

  public Msisdn(int ctn) {
    this.ctn = ctn;
  }

  public int getCtn() {
    return ctn;
  }

  public void setCtn(int ctn) {
    this.ctn = ctn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Msisdn msisdn = (Msisdn) o;

    return ctn == msisdn.ctn;
  }

  @Override
  public int hashCode() {
    return ctn;
  }
}
