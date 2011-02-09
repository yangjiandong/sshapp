package org.springside.modules.charts;

import java.awt.image.BufferedImage;


/**
 * An Extension to create charts
 *
 * @since 1.10
 */
public interface Chart extends ServerExtension {
  String getKey();

  /**
   * The method to implement to generate the chart
   *
   * @param params the chart parameters
   * @return the image generated
   */
  BufferedImage generateImage(ChartParameters params);
}
