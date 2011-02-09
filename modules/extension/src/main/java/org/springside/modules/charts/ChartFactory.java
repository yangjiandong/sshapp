package org.springside.modules.charts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ChartFactory implements ServerComponent {
  private static final Logger LOG = LoggerFactory.getLogger(ChartFactory.class);
  private Map<String, Chart> chartsByKey = new HashMap<String, Chart>();

  public ChartFactory(Chart[] charts) {
    for (Chart chart : charts) {
      if (chartsByKey.containsKey(chart.getKey())) {
        LOG.warn("Duplicated chart key:" + chart.getKey() + ". Existing chart: " + chartsByKey.get(chart.getKey()).getClass().getCanonicalName());

      } else {
        chartsByKey.put(chart.getKey(), chart);
      }
    }
  }

  public ChartFactory() {
    // DO NOT SUPPRESS : used by picocontainer if no charts
  }

  public Chart getChart(String key) {
    return chartsByKey.get(key);
  }
}
