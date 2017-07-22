package org.unknown.model;

import java.time.LocalDateTime;

/**
 * Author: Artem Voronov
 */
public class Profile {
  private String name;
  private String email;
  private LocalDateTime activateDate;

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

  public LocalDateTime getActivateDate() {
    return activateDate;
  }

  public void setActivateDate(LocalDateTime activateDate) {
    this.activateDate = activateDate;
  }
}
