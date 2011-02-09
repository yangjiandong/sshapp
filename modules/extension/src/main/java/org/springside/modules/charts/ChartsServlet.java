package org.springside.modules.charts;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jfree.chart.encoders.KeypointPNGEncoderAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.charts.deprecated.BarChart;
import org.springside.modules.charts.deprecated.BaseChartWeb;
import org.springside.modules.charts.deprecated.CustomBarChart;
import org.springside.modules.charts.deprecated.DeprecatedChart;
import org.springside.modules.charts.deprecated.PieChart;
import org.springside.modules.charts.deprecated.SparkLinesChart;
import org.springside.modules.charts.plugin.DistributionAreaChart;
import org.springside.modules.charts.plugin.DistributionBarChart;
import org.springside.modules.charts.plugin.XradarChart;

public class ChartsServlet extends HttpServlet {

  private static final Logger LOG = LoggerFactory.getLogger(ChartsServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (isDeprecatedChart(request)) {
      deprecatedDoGet(request, response);

    } else {
      Map<String, Chart> chartsByKey = new HashMap<String, Chart>();
      chartsByKey.put("distarea", new DistributionAreaChart());
      chartsByKey.put("distbar", new DistributionBarChart());
      chartsByKey.put("xradar", new XradarChart());

      String chartName = request.getParameter("ck");
      Chart chart = chartsByKey.get(chartName);

//      ChartFactory chartFactory = Platform.getInstance().getContainer().getComponent(ChartFactory.class);
//      Chart chart = chartFactory.getChart(request.getParameter("ck"));
      if (chart != null) {
        BufferedImage image = chart.generateImage(getParams(request));
        OutputStream out = response.getOutputStream();
        try {
          response.setContentType("image/png");
          exportAsPNG(image, out);

        } catch (Exception e) {
          LOG.error("Generating chart " + chart.getClass().getName(), e);

        } finally {
          out.close();
        }
      }
    }
  }

  private ChartParameters getParams(HttpServletRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    Enumeration keys = request.getParameterNames();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = request.getParameter(key);
      map.put(key, value);
    }
    return new ChartParameters(map);
  }

  private void exportAsPNG(BufferedImage image, OutputStream out) throws IOException {
    KeypointPNGEncoderAdapter encoder = new KeypointPNGEncoderAdapter();
    encoder.setEncodingAlpha(true);
    encoder.encode(image, out);
  }

  public boolean isDeprecatedChart(HttpServletRequest request) {
    String chartType = request.getParameter(BaseChartWeb.CHART_PARAM_TYPE);
    if (BaseChartWeb.BAR_CHART_HORIZONTAL.equals(chartType) || BaseChartWeb.BAR_CHART_VERTICAL.equals(chartType) || BaseChartWeb.STACKED_BAR_CHART.equals(chartType)) {
      return true;
    }
    if (BaseChartWeb.BAR_CHART_VERTICAL_CUSTOM.equals(chartType)) {
      return true;
    }
    if (BaseChartWeb.PIE_CHART.equals(chartType)) {
      return true;
    }
    if (BaseChartWeb.SPARKLINES_CHART.equals(chartType)) {
      return true;
    }
    return false;
  }

  public void deprecatedDoGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Map<String, String> params = new HashMap<String, String>();
    params.put(BaseChartWeb.CHART_PARAM_TYPE, request.getParameter(BaseChartWeb.CHART_PARAM_TYPE));
    params.put(BaseChartWeb.CHART_PARAM_VALUES, request.getParameter(BaseChartWeb.CHART_PARAM_VALUES));
    params.put(BaseChartWeb.CHART_PARAM_COLORS, request.getParameter(BaseChartWeb.CHART_PARAM_COLORS));
    params.put(BaseChartWeb.CHART_PARAM_RANGEMAX, request.getParameter(BaseChartWeb.CHART_PARAM_RANGEMAX));
    params.put(BaseChartWeb.CHART_PARAM_TITLE, request.getParameter(BaseChartWeb.CHART_PARAM_TITLE));
    params.put(BaseChartWeb.CHART_PARAM_DIMENSIONS, request.getParameter(BaseChartWeb.CHART_PARAM_DIMENSIONS));
    params.put(BaseChartWeb.CHART_PARAM_CATEGORIES_AXISMARGIN_VISIBLE, request.getParameter(BaseChartWeb.CHART_PARAM_CATEGORIES_AXISMARGIN_VISIBLE));
    params.put(BaseChartWeb.CHART_PARAM_RANGEAXIS_VISIBLE, request.getParameter(BaseChartWeb.CHART_PARAM_RANGEAXIS_VISIBLE));
    params.put(BaseChartWeb.CHART_PARAM_SERIES, request.getParameter(BaseChartWeb.CHART_PARAM_SERIES));
    params.put(BaseChartWeb.CHART_PARAM_CATEGORIES, request.getParameter(BaseChartWeb.CHART_PARAM_CATEGORIES));
    params.put(BaseChartWeb.CHART_PARAM_CATEGORIES_AXISMARGIN_LOWER, request.getParameter(BaseChartWeb.CHART_PARAM_CATEGORIES_AXISMARGIN_LOWER));
    params.put(BaseChartWeb.CHART_PARAM_CATEGORIES_AXISMARGIN_UPPER, request.getParameter(BaseChartWeb.CHART_PARAM_CATEGORIES_AXISMARGIN_UPPER));
    params.put(BaseChartWeb.CHART_PARAM_SERIES_AXISMARGIN_LOWER, request.getParameter(BaseChartWeb.CHART_PARAM_SERIES_AXISMARGIN_LOWER));
    params.put(BaseChartWeb.CHART_PARAM_SERIES_AXISMARGIN_UPPER, request.getParameter(BaseChartWeb.CHART_PARAM_SERIES_AXISMARGIN_UPPER));
    params.put(BaseChartWeb.CHART_PARAM_SERIES_AXISMARGIN_TICKUNIT, request.getParameter(BaseChartWeb.CHART_PARAM_SERIES_AXISMARGIN_TICKUNIT));
    params.put(BaseChartWeb.CHART_PARAM_INSETS, request.getParameter(BaseChartWeb.CHART_PARAM_INSETS));
    params.put(BaseChartWeb.CHART_PARAM_OUTLINE_RANGEGRIDLINES_VISIBLE, request.getParameter(BaseChartWeb.CHART_PARAM_OUTLINE_RANGEGRIDLINES_VISIBLE));
    params.put(BaseChartWeb.CHART_PARAM_OUTLINE_VISIBLE, request.getParameter(BaseChartWeb.CHART_PARAM_OUTLINE_VISIBLE));

    String chartType = params.get(BaseChartWeb.CHART_PARAM_TYPE);

    DeprecatedChart chart = null;

    if (BaseChartWeb.BAR_CHART_HORIZONTAL.equals(chartType) || BaseChartWeb.BAR_CHART_VERTICAL.equals(chartType) || BaseChartWeb.STACKED_BAR_CHART.equals(chartType)) {
      chart = new BarChart(params);
    } else if (BaseChartWeb.BAR_CHART_VERTICAL_CUSTOM.equals(chartType)) {
      chart = new CustomBarChart(params);
    } else if (BaseChartWeb.PIE_CHART.equals(chartType)) {
      chart = new PieChart(params);
    } else if (BaseChartWeb.SPARKLINES_CHART.equals(chartType)) {
      chart = new SparkLinesChart(params);
    }

    OutputStream out = null;
    try {
      if (chart != null) {
        out = response.getOutputStream();
        response.setContentType("image/png");
        chart.exportChartAsPNG(out);
      }

    } catch (Exception e) {
      LOG.error("Generating chart " + chart.getClass().getName(), e);

    } finally {
      IOUtils.closeQuietly(out);
    }
  }

}
