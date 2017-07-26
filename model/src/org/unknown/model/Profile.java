package org.unknown.model;

import java.io.Serializable;

/**
 * Author: Artem Voronov
 */
public class Profile implements Serializable {
  private String name;
  private String email;
  private String activateDate;

  public Profile() {
  }

  public Profile(String name, String email, String activateDate) {
    this.name = name;
    this.email = email;
    this.activateDate = activateDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getActivateDate() {
    return activateDate;
  }

  public void setActivateDate(String activateDate) {
    this.activateDate = activateDate;
  }
}
