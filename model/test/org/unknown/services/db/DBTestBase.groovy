package org.unknown.services.db

import org.junit.Ignore

/**
 * Author: Artem Voronov
 */
@Ignore
class DBTestBase extends GroovyTestCase {
  //TODO: уменьшить время запуска тестов, скажем, создать бд 1 раз и потом просто чистить кэши в ноде
  protected DBService db

  @Override
  protected void setUp() {
    super.setUp()

    db = new DBService()
  }

  @Override
  protected void tearDown() {
    super.tearDown()

    db?.shutdown()
  }
}
