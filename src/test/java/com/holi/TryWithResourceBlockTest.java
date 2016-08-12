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

  @Test public void closesResourceWhenLeavingTryWithResourcesBlockEvenIfFailsInBlock() throws Throwable {
    AtomicBoolean closed = new AtomicBoolean(false);

    shouldFail(Exception.class, () -> {
      try (Closeable resource = resourceWillNotifiesWhenClosing(closed)) {
        throw new Exception();
      } finally {
        assertTrue(closed.get());
      }
    });
  }

  @Test public void throwsExceptionWhenClosingResourceFails() throws Throwable {
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

  private void shouldFail(Class<? extends Throwable> thrown, Action action) throws Throwable {
    try {
      action.run();
    } catch (Throwable failed) {
      if (thrown.isInstance(failed)) return;
      throw failed;
    }
    fail(format("should failed with %s", thrown.getName()));
  }

  interface Action {
    void run() throws Throwable;
  }

  private Closeable resourceWillNotifiesWhenClosing(AtomicBoolean closed) {
    return () -> closed.set(true);
  }
}
