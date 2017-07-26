package org.unknown.model;

import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Author: Artem Voronov
 */
public class Msisdn implements Serializable {
  @QuerySqlField(index = true)
  private Integer ctn; //just one field for simplicity

  @QuerySqlField(index = true)
  private Integer cellId;

  private transient AffinityKey<Integer> key;

  public Msisdn() {
  }

  public Msisdn(Integer ctn, Integer cellId) {
    this.ctn = ctn;
    this.cellId = cellId;
  }

  public Integer getCtn() {
    return ctn;
  }

  public void setCtn(Integer ctn) {
    this.ctn = ctn;
  }

  public Integer getCellId() {
    return cellId;
  }

  public void setCellId(Integer cellId) {
    this.cellId = cellId;
  }

  public AffinityKey<Integer> key() {
    if (key == null)
      key = new AffinityKey<>(ctn, cellId);

    return key;
  }
}
