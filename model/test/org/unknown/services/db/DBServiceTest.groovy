package org.unknown.services.db

import org.apache.ignite.IgniteCache

/**
 * Author: Artem Voronov
 */
class DBServiceTest extends DBTestBase {

  void testSaveAndLoad() {
    IgniteCache<Integer, String> cache = db.getOrCreateCache("CACHE_EXAMPLE")
    Integer key = 1
    String value = "TEST"

    db.vtx {
      cache.put(key, value)
    }

    def loaded = db.tx {
      cache.get(key)
    }

    assertNotNull(loaded)
    assertEquals(value, loaded)
  }

  void testIsolation() {
    IgniteCache<Integer, String> cache = db.getOrCreateCache("CACHE_EXAMPLE")
    Integer key = 1

    def loaded = db.tx {
      cache.get(key)
    }

    assertNull(loaded)
  }

  void testTransactionFails() {
    IgniteCache<Integer, String> cache = db.getOrCreateCache("CACHE_EXAMPLE")
    Integer key = 1
    String value = "TEST"

    try {
      db.vtx {
        cache.put(key, value)
        throw new RuntimeException("Alarm!")
      }
    } catch (Exception ignored) {
    }

    def loaded = db.tx {
      cache.get(key)
    }

    assertNull(loaded)
  }
}
