package io.prometheus.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class CollectorTest {
  @Test
  public void sanitizeMetricName() throws Exception {
      assertEquals("_hoge", CollectorUtils.sanitizeMetricName("0hoge"));
      assertEquals("foo_bar0", CollectorUtils.sanitizeMetricName("foo.bar0"));
  }
}
