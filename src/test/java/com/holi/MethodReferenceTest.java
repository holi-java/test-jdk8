package com.holi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-13.
 */
public class MethodReferenceTest {
  @Test public void collectsItemsFromArrayToList() throws Exception {
    int[] numbers = {1, 2, 3, 4};
    List<Integer> collector = new ArrayList<>();

    //collect items using method reference on List
    Arrays.stream(numbers).forEach(collector::add);

    assertThat(collector, equalTo(asList(1, 2, 3, 4)));
  }
}
