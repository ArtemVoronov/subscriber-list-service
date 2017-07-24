package org.unknown.web.controllers.subscriber.model;

import java.util.List;

/**
 * Author: Artem Voronov
 */
public class SubscriberList {
  private int total;
  private List<SubscriberProfile> result;

  public SubscriberList(List<SubscriberProfile> result) {
    this.result = result;
    this.total = result.size();
  }

  public int getTotal() {
    return total;
  }

  public List<SubscriberProfile> getResult() {
    return result;
  }

}
