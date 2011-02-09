package org.springside.modules.charts.plugin;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;
import org.springside.modules.charts.ChartParameters;

public class DistributionAreaChartTest extends AbstractChartTest {

  @Test
  public void oneSerie() throws IOException {
    DistributionAreaChart chart = new DistributionAreaChart();
    BufferedImage image = chart.generateImage(new ChartParameters("v=0%3D5%3B1%3D22%3B2%3D2"));
    assertChartSizeGreaterThan(image, 1000);
    saveChart(image, "DistributionAreaChartTest/oneSerie.png");
  }

  @Test
  public void manySeries() throws IOException {
    DistributionAreaChart chart = new DistributionAreaChart();
    BufferedImage image = chart.generateImage(new ChartParameters("v=0%3D5%3B1%3D22%3B2%3D2|0%3D7%3B1%3D15%3B2%3D4"));
    assertChartSizeGreaterThan(image, 1000);
    saveChart(image, "DistributionAreaChartTest/manySeries.png");
  }

  @Test
  public void manySeriesWithDifferentCategories() throws IOException {
    DistributionAreaChart chart = new DistributionAreaChart();
    BufferedImage image = chart.generateImage(new ChartParameters("v=0%3D5%3B1%3D22%3B2%3D2|2%3D7%3B4%3D15%3B9%3D4"));
    assertChartSizeGreaterThan(image, 1000);
    saveChart(image, "DistributionAreaChartTest/manySeriesWithDifferentCategories.png");
  }

  @Test
  public void manySeriesIncludingAnEmptySerie() throws IOException {
    // the third serie should not have the second default color, but the third one !
    DistributionAreaChart chart = new DistributionAreaChart();
    BufferedImage image = chart.generateImage(new ChartParameters("v=0%3D5%3B1%3D22%3B2%3D2||2%3D7%3B4%3D15%3B9%3D4"));
    assertChartSizeGreaterThan(image, 1000);
    saveChart(image, "DistributionAreaChartTest/manySeriesIncludingAnEmptySerie.png");
  }
}