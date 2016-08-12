package com.holi;

import java.util.Locale;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-13.
 */
public class StaticMethodsInInterfaceTest {

  @Test public void callsStaticMethodInInterfaceAsClass() throws Exception {
    assertThat(Clock.getLocal(), equalTo(Locale.getDefault()));
  }

  interface Clock {
    static Locale getLocal() {
      return Locale.getDefault();
    }
  }
}
