package org.springside.modules.charts.plugin;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;
import org.springside.modules.charts.ChartParameters;

public class XradarChartTest extends AbstractChartTest {

  @Test
  public void shouldGenerateXradar() throws IOException {
    String url = "w=200&h=200&l=Usa.,Eff.,Rel.,Main.,Por.&g=0.25&m=100&v=90,80,70,60,50|40,30,20,10,10&c=CAE3F2|F8A036";
    XradarChart radar = new XradarChart();
    BufferedImage img = radar.generateImage(new ChartParameters(url));
    saveChart(img, "shouldGenerateXradar.png");
    assertChartSizeGreaterThan(img, 50);
  }

  @Test
  public void negativeValuesAreNotDisplayed() throws IOException {
    String url = "w=200&h=200&l=Usa.,Eff.,Rel.,Main.,Por.&g=0.3&m=100&v=-90,-80,70,60,50&c=CAE3F2";
    XradarChart radar = new XradarChart();
    BufferedImage img = radar.generateImage(new ChartParameters(url));
    saveChart(img, "negativeValuesAreNotDisplayed.png");

    // you have to check visually that it does not work ! Min value is 0. This is a limitation of JFreeChart.
    assertChartSizeGreaterThan(img, 50);
  }

  @Test
  public void otherXradarChart() throws IOException {
    String url = "w=140&g=0.25&v=100.0,98.0,100.0,93.799999999999997,99.5&c=777777|F8A036&ck=xradar&l=质量.,Mai.,Por.,Rel.,Usa.&m=100&h=110";
    XradarChart radar = new XradarChart();
    BufferedImage img = radar.generateImage(new ChartParameters(url));
    saveChart(img, "otherXradarChart.png");

    // you have to check visually that it does not work ! Min value is 0. This is a limitation of JFreeChart.
    //assertChartSizeGreaterThan(img, 50);
  }

  //Generating chart: {w=140, g=0.25, v=100.0,98.0,100.0,93.799999999999997,99.5, c=777777|F8A036, ck=xradar, l=Eff.,Mai.,Por.,Rel.,Usa., m=100, h=110}
}