package com.holi;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-13.
 */
public class DefaultMethodsInInterfaceTest {

  @Test public void inheritDefaultMethodsInInterface() throws Exception {
    Action action = new SimpleAction();

    assertThat(action.getName(), equalTo("action"));
  }

  @Test public void solvesConflictsByOverridingDefaultMethodsToCallCorrespondMethodWhenOccursSameDefaultMethodsInMultiInterfaces() throws Exception {
    Action action = new Hybrid();

    assertThat(action.getName(), equalTo("command"));
  }

  interface Action {
    default String getName() {
      return "action";
    }
  }

  interface Command {
    default String execute() {
      return "command";
    }
  }

  static class SimpleAction implements Action {
  }

  private class Hybrid implements Action, Command {
    @Override public String getName() {
      return Command.super.execute();
    }
  }
}


