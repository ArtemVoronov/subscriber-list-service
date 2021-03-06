package org.unknown.services.db;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;

/**
 * Author: Artem Voronov
 */
public class DBService {

  private final Ignite ignite;

  public DBService() {
    ignite = Ignition.start("org/unknown/services/db/example-ignite.xml");
  }

  public <T> T tx(DBAction<T> action) {
    T value = null;
    try (Transaction tx = Ignition.ignite().transactions().txStart()) {
      value = action.action();
      tx.commit();
    }
    return value;
  }

  public void vtx(DBVoidAction action) {
    try (Transaction tx = Ignition.ignite().transactions().txStart()) {
      action.action();
      tx.commit();
    }
  }

  public <K, V> IgniteCache<K, V> getOrCreateCache(String cacheName, Class indexTypeKey, Class indexTypeValue){
    CacheConfiguration<K, V> cacheConfig = new CacheConfiguration<>(cacheName);
    cacheConfig.setCacheMode(CacheMode.PARTITIONED);
    cacheConfig.setIndexedTypes(indexTypeKey, indexTypeValue);
    cacheConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    return ignite.getOrCreateCache(cacheConfig);
  }

  public <K, V> IgniteCache<K, V> getOrCreateCache(String cacheName){
    CacheConfiguration<K, V> cacheConfig = new CacheConfiguration<>(cacheName);
    cacheConfig.setCacheMode(CacheMode.PARTITIONED);
    cacheConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    return ignite.getOrCreateCache(cacheConfig);
  }

  public void shutdown() {
    Ignition.stopAll(false);
  }

  public interface DBAction<T> {
    T action();
  }

  public interface DBVoidAction {
    void action();
  }
}
