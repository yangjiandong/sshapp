package org.springside.modules.charts.jruby;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

import org.springside.modules.charts.deprecated.BaseChartTest;

public class TrendsChartTest extends BaseChartTest {
  private static final int WIDTH = 900;
  private static final int HEIGHT = 350;

  private TrendsChart chart;

  public void testTrendsChart() throws ParseException, IOException {
    chart = new TrendsChart(WIDTH, HEIGHT, "fr", true);
    chart.initSerie(0L, "Global", false);
    chart.initSerie(6L, "Efficiency", true);
    chart.initSerie(9L, "Maintanability", false);
    chart.initSerie(3L, "Portability", false);
    chart.initSerie(24L, "Reliability", true);
    chart.initSerie(60L, "Usability", false);

    chart.addMeasure(20.0, stringToDate("12-10-07 8h30"), 0L);
    chart.addMeasure(35.0, stringToDate("12-10-07 8h30"), 6L);
    chart.addMeasure(12.4, stringToDate("12-10-07 8h30"), 9L);
    chart.addMeasure(99.99, stringToDate("12-10-07 8h30"), 3L);
    chart.addMeasure(2.3, stringToDate("12-10-07 8h30"), 24L);
    chart.addMeasure(12.5, stringToDate("12-10-07 8h30"), 60L);

    chart.addMeasure(10.0, stringToDate("12-11-07 8h30"), 0L);
    chart.addMeasure(30.0, stringToDate("12-11-07 8h30"), 6L);
    chart.addMeasure(22.4, stringToDate("12-11-07 8h30"), 9L);
    chart.addMeasure(99.99, stringToDate("12-11-07 8h30"), 3L);
    chart.addMeasure(0.3, stringToDate("12-11-07 8h30"), 24L);
    chart.addMeasure(12.5, stringToDate("12-11-07 8h30"), 60L);

    chart.addMeasure(30.0, stringToDate("12-12-07 8h30"), 0L);
    chart.addMeasure(15.0, stringToDate("12-12-07 8h30"), 6L);
    chart.addMeasure(82.4, stringToDate("12-12-07 8h30"), 9L);
    chart.addMeasure(99.99, stringToDate("12-12-07 8h30"), 3L);
    chart.addMeasure(52.3, stringToDate("12-12-07 8h30"), 24L);
    chart.addMeasure(12.5, stringToDate("12-12-07 8h30"), 60L);

    chart.addLabel(stringToDate("12-11-07 12h35"), "Label A");
    chart.addLabel(stringToDate("11-11-07 12h35"), "Label B", true);

    BufferedImage img = chart.getChartImage();
    assertChartSizeGreaterThan(img, 1000);
    saveChart(img, "trends-chart.png");
  }


  public void testSingleSerieWithoutLegends() throws ParseException, IOException {
    chart = new TrendsChart(WIDTH, HEIGHT, "fr", false);
    chart.initSerie(0L, "single", false);

    chart.addMeasure(20.0, stringToDate("12-10-07 8h30"), 0L);
    chart.addMeasure(10.0, stringToDate("12-11-07 8h30"), 0L);
    chart.addMeasure(30.0, stringToDate("12-12-07 8h30"), 0L);

    BufferedImage img = chart.getChartImage();
    assertChartSizeGreaterThan(img, 1000);
    saveChart(img, "trends-single-without-legends.png");
  }
}
