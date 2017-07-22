package org.unknown.services.db;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.unknown.model.Cell;
import org.unknown.model.Msisdn;
import org.unknown.model.Profile;

import java.util.Set;

/**
 * Author: Artem Voronov
 */
public class DBService {

  private final IgniteCache<Integer, Cell> cells;
  private final IgniteCache<Cell, Set<Msisdn>> msisdns;
  private final IgniteCache<Msisdn, Profile> profiles;

  public DBService() {
    Ignite ignite = Ignition.start("org/unknown/services/db/example-ignite.xml");

    //TODO: отделить кэши от службы; getCell, addCell убрать в другое место
    CacheConfiguration<Integer, Cell> cellsCacheConfig = new CacheConfiguration<>("CELLS");
    CacheConfiguration<Cell, Set<Msisdn>> phonesCacheConfig = new CacheConfiguration<>("MSISDNS");
    CacheConfiguration<Msisdn, Profile> profilesCacheConfig = new CacheConfiguration<>("PROFILES");

    cellsCacheConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    phonesCacheConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
    profilesCacheConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

    cells = ignite.getOrCreateCache(cellsCacheConfig);
    msisdns = ignite.getOrCreateCache(phonesCacheConfig);
    profiles = ignite.getOrCreateCache(profilesCacheConfig);
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

  public Cell getCell(Integer cellId) {
    return tx(() -> cells.get(cellId));
  }
  public void addCell(Cell cell) {
    vtx(() -> cells.put(cell.getCellId(), cell));
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
