package org.unknown.web.controllers.subscriber.model;

/**
 * Author: Artem Voronov
 */
public class SubscriberProfile {
  private int ctn;
  private String name;
  private String email;
  private String activateDate; //formatted as 2017-02-24 12:45:00

  public SubscriberProfile() {
  }

  public SubscriberProfile(int ctn, String name, String email, String activateDate) {
    this.ctn = ctn;
    this.name = name;
    this.email = email;
    this.activateDate = activateDate;
  }

  public int getCtn() {
    return ctn;
  }

  public void setCtn(int ctn) {
    this.ctn = ctn;
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
