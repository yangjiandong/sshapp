package org.springside.modules.charts.deprecated;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

public class PieChart extends BaseChartWeb implements DeprecatedChart {

  private DefaultPieDataset dataset = null;

  public PieChart(Map<String, String> params) {
    super(params);
    jfreechart = new JFreeChart(null, TextTitle.DEFAULT_FONT, new PiePlot(), false);
  }

  @Override
  protected BufferedImage getChartImage() throws IOException {
    configure();
    return getBufferedImage(jfreechart);
  }

  private void configure() {
    configureChart(jfreechart, false);
    configureDataset();
    configurePlot();
    applyParams();
  }

  private void configureDataset() {
    dataset = new DefaultPieDataset();
  }

  private void configurePlot() {
    PiePlot plot = (PiePlot) jfreechart.getPlot();
    plot.setNoDataMessage(DEFAULT_MESSAGE_NODATA);
    plot.setInsets(RectangleInsets.ZERO_INSETS);
    plot.setDataset(dataset);
    plot.setBackgroundAlpha(0.0f);
    plot.setCircular(true);
    plot.setLabelGenerator(null);
    plot.setIgnoreNullValues(true);
    plot.setIgnoreZeroValues(true);
    plot.setShadowPaint(null);
    plot.setLabelLinkMargin(0.0);
    plot.setInteriorGap(0.02);
    plot.setMaximumLabelWidth(0.10);
  }

  private void configureColors(String colors) {
    try {
      if (colors != null && colors.length() > 0) {
        StringTokenizer stringTokenizer = new StringTokenizer(colors, ",");
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
          ((PiePlot) jfreechart.getPlot()).setSectionPaint(Integer.toString(i), Color.decode("0x" + stringTokenizer.nextToken()));
          i++;
        }
      } else {
        configureDefaultColors();
      }
    }
    catch (Exception e) {
      configureDefaultColors();
    }
  }

  private void configureDefaultColors() {
    PiePlot plot = (PiePlot) jfreechart.getPlot();
    for (int i=0 ; i<COLORS.length ; i++) {
      plot.setSectionPaint("" + i, COLORS[i]);
    }
  }

  private void applyParams() {
    applyCommonParams();

    configureColors(params.get(CHART_PARAM_COLORS));
    addMeasures(params.get(CHART_PARAM_VALUES));

    // -- Plot
    PiePlot plot = (PiePlot) jfreechart.getPlot();
    plot.setOutlineVisible(isParamValueValid(params.get(CHART_PARAM_OUTLINE_VISIBLE)) && Boolean.getBoolean(params.get(CHART_PARAM_OUTLINE_VISIBLE)));
  }

  private void addMeasures(String values) {
    if (values != null && values.length() > 0) {
      StringTokenizer st = new StringTokenizer(values, ",");
      int i = 0;
      while (st.hasMoreTokens()) {
        double measure = 0;
        try {
          measure = Double.parseDouble(st.nextToken());
        }
        catch (NumberFormatException e) {
        }
        dataset.setValue(Integer.toString(i), measure);
        i++;
      }
    }
  }

}