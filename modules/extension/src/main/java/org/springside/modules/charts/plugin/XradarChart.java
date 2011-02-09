package org.springside.modules.charts.plugin;

import java.awt.BasicStroke;
import java.awt.Color;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springside.modules.charts.AbstractChart;
import org.springside.modules.charts.ChartParameters;


public class XradarChart extends AbstractChart {

  /**
   * see an example of complete URL in XradarChartTest
   */

  public static final String PARAM_COLOR = "c";
  public static final String PARAM_MAX_VALUE = "m";
  public static final String PARAM_INTERIOR_GAP = "g";
  public static final String PARAM_LABELS = "l";
  public static final String PARAM_VALUES = "v";

  public String getKey() {
    return "xradar";
  }

  @Override
  protected Plot getPlot(ChartParameters params) {
    SpiderWebPlot plot = new SpiderWebPlot(createDataset(params));
    plot.setStartAngle(0D);
    plot.setOutlineVisible(false);
    plot.setAxisLinePaint(Color.decode("0xCCCCCC"));
    plot.setSeriesOutlineStroke(new BasicStroke(2f));

    if (params.getValue(PARAM_INTERIOR_GAP) != null) {
      plot.setInteriorGap(Double.parseDouble(params.getValue(PARAM_INTERIOR_GAP, "0.4", false)));
    }
    if (params.getValue(PARAM_MAX_VALUE) != null) {
      plot.setMaxValue(Double.parseDouble(params.getValue(PARAM_MAX_VALUE, "100", false)));
    }
    configureColors(plot, params);
    return plot;
  }

  private void configureColors(SpiderWebPlot plot, ChartParameters params) {
    String[] colors = params.getValues(PARAM_COLOR, "|");
    for (int i = 0; i < colors.length; i++) {
      plot.setSeriesPaint(i, Color.decode("0x" + colors[i]));
    }
  }

  private CategoryDataset createDataset(ChartParameters params) {
    String[] labels = params.getValues(PARAM_LABELS, ",");
    String[] values = params.getValues(PARAM_VALUES, "|");

    DefaultCategoryDataset set = new DefaultCategoryDataset();
    for (int indexValues = 0; indexValues < values.length; indexValues++) {
      String[] fields = StringUtils.split(values[indexValues], ",");
      for (int i = 0; i < fields.length; i++) {
        set.addValue(Double.parseDouble(fields[i]), "" + indexValues, labels[i]);
      }
    }
    return set;
  }
}