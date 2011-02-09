package org.springside.modules.charts.plugin;

import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springside.modules.charts.AbstractChart;
import org.springside.modules.charts.ChartParameters;

public class DistributionAreaChart extends AbstractChart {
  private static final String PARAM_COLORS = "c";

  public String getKey() {
    return "distarea";
  }

  @Override
  protected Plot getPlot(ChartParameters params) {
    DefaultCategoryDataset dataset = createDataset(params);

    CategoryAxis domainAxis = new CategoryAxis();
    domainAxis.setCategoryMargin(0.0);
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);

    NumberAxis rangeAxis = new NumberAxis();
    rangeAxis.setNumberFormatOverride(NumberFormat.getIntegerInstance(params.getLocale()));
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    AreaRenderer renderer = new AreaRenderer();
    CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer);
    plot.setForegroundAlpha(0.5f);
    plot.setDomainGridlinesVisible(true);
    configureColors(dataset, plot, params.getValues(PARAM_COLORS, ","));
    return plot;
  }

  private DefaultCategoryDataset createDataset(ChartParameters params) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String[] series = params.getValues("v", "|", true);
    int index = 0;
    while (index < series.length) {
      String[] pairs = StringUtils.split(series[index], ";");
      if (pairs.length == 0) {
        dataset.addValue((Number)0.0, index, "0");

      } else {
        for (String pair : pairs) {
          String[] keyValue = StringUtils.split(pair, "=");
          double val = Double.parseDouble(keyValue[1]);
          dataset.addValue((Number) val, index, keyValue[0]);
        }
      }
      index++;
    }
    return dataset;
  }
}
