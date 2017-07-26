package org.unknown.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Author: Artem Voronov
 */
public class Cell implements Serializable {
  @QuerySqlField(index = true)
  private Integer id;

  public Cell() {
  }

  public Cell(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
