package org.unknown.services.db;

import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

/**
 * Author: Artem Voronov
 */
public class NodeStartup {
  //TODO: run as service at InitListener
  public static void main(String[] args) throws IgniteException {
    Ignition.start("org/unknown/services/db/example-ignite.xml");
  }
}
