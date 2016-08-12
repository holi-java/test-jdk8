package com.holi;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by selonj on 16-8-13.
 */
public class TryWithResourceBlockTest {

  @Test public void closesResourceWhenLeavingTryWithResourceBlock() throws Exception {
    AtomicBoolean closed = new AtomicBoolean(false);

    try (Closeable resource = resourceWillNotifiesWhenClosing(closed)) {
      assertTrue(!closed.get());
    } finally {
      assertTrue(closed.get());
    }
  }

  @Test public void closesResourceWhenLeavingTryWithResourcesBlockEvenIfFailsInBlock() throws Exception {
    AtomicBoolean closed = new AtomicBoolean(false);

    shouldFail(Exception.class, () -> {
      try (Closeable resource = resourceWillNotifiesWhenClosing(closed)) {
        throw new Exception();
      } finally {
        assertTrue(closed.get());
      }
    });
  }

  @Test public void throwsExceptionWhenClosingResourceFails() throws Exception {
    shouldFail(IOException.class, () -> {
      try (Closeable resource = resourceFailsOnClosing()) {
        assertTrue(true);
      }
    });
  }

  private Closeable resourceFailsOnClosing() {
    return () -> {
      throw new IOException("close failed!");
    };
  }

  private void shouldFail(Class<? extends Exception> expected, Action action) throws Exception {
    try {
      action.run();
      fail(format("should failed with %s", expected.getName()));
    } catch (Exception failed) {
      if (!expected.isInstance(failed)) throw failed;
    }
  }

  interface Action {
    void run() throws Exception;
  }

  private Closeable resourceWillNotifiesWhenClosing(AtomicBoolean closed) {
    return () -> closed.set(true);
  }
}
