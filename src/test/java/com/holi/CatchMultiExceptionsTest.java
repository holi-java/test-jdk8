package com.holi;

import java.io.IOException;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by selonj on 16-8-13.
 */
public class CatchMultiExceptionsTest {

  @Test public void couldCaughtMultiExceptions() throws Exception {
    assertExceptionCouldBeCaught(new IllegalArgumentException());
    assertExceptionCouldBeCaught(new ArithmeticException());

    assertExceptionMissingCaught(new IOException());
  }

  private void assertExceptionMissingCaught(Exception expected) throws Exception {
    try {
      assertExceptionCouldBeCaught(expected);
      fail(String.format("should %s missing caught!", expected.getClass().getName()));
    } catch (Exception actual) {
      if (expected != actual) throw actual;
    }
  }

  private void assertExceptionCouldBeCaught(Exception expected) throws Exception {
    try {
      throw expected;
    } catch (IllegalArgumentException | ArithmeticException actual) {
      assertThat(actual, is(sameInstance(expected)));
    }
  }
}
