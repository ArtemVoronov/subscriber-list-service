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

  //TODO: clean and add cached for model entities
  private static final String CACHE_NAME = "Democache";
  private final IgniteCache<Integer, String> cache;

  public DBService() {
    Ignite ignite = Ignition.start("org/unknown/services/db/example-ignite.xml");

    CacheConfiguration<Integer, String> cfg = new CacheConfiguration<>();
    cfg.setName(CACHE_NAME);
    cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

    cache = ignite.getOrCreateCache(cfg);
  }

  public void addCacheItem(Integer key, String value) {
    cache.put(key, value);
  }

  public String getCacheItem(Integer key) {
    return cache.get(key);
  }

  public void shutdown() {
    Ignition.stopAll(false);
  }
}
