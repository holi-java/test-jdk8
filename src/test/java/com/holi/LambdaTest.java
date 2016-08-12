package com.holi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by selonj on 16-8-13.
 */
public class LambdaTest {
  @Test public void castsToInterfaceWithSingleAbstractMethod() throws Exception {
    AtomicBoolean called = new AtomicBoolean(false);
    Action action = () -> called.set(true);
    assertTrue(!called.get());

    action.execute();

    assertTrue(called.get());
  }

  @Test public void couldSkipParameterTypesDeclaration() {
    Function<Integer, Integer> explicit = (Integer number) -> number;
    Function<Integer, Integer> skipped = (number) -> number;
    int anything = 3;

    assertThat(skipped.apply(anything), equalTo(explicit.apply(anything)));
  }

  @Test public void couldSkipParenthesesWhenThereIsOnlyOneParameterWithImplicitTypeDeclaration() throws Exception {
    Function<Integer, Integer> explicit = (Integer number) -> number;
    Function<Integer, Integer> skipped = number -> number;
    int anything = 3;

    assertThat(skipped.apply(anything), equalTo(explicit.apply(anything)));
  }

  @Test public void addsMultiParametersSeparatedByCommaSymbol() throws Exception {
    BiFunction<Integer, Integer, Integer> plus = (augend, addend) -> augend + addend;

    assertThat(plus.apply(3, 4), equalTo(7));
  }

  interface Action {
    void execute();
  }
}
