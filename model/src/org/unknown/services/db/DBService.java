package org.unknown.services.db;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Author: Artem Voronov
 */
public class DBService {

  //TODO: clean
  public static void main(String[] args) {
    Ignition.setClientMode(true);

    try(Ignite ignite = Ignition.start("org/unknown/services/db/example-ignite.xml")) {
      CacheConfiguration<Integer, String> cfg = new CacheConfiguration<>();
      cfg.setName("Democache");
      cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

      IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg);

      cache.put(1, "cache item 1");
      cache.put(2, "cache item 2");

      System.out.println(cache.get(1));
      System.out.println(cache.get(2));
    }
  }

  public DBService(int nodesCount) {
    //TODO: start apache ignite nodes
  }

  public void shutdown() {
    //TODO: stop apache ignite nodes
  }
}
