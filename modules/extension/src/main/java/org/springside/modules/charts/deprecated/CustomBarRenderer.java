package org.springside.modules.charts.deprecated;

import org.jfree.chart.renderer.category.BarRenderer;

import java.awt.*;

public class CustomBarRenderer extends BarRenderer {

  public static final Paint[] COLORS = new Paint[]{Color.red, Color.blue, Color.green,
      Color.yellow, Color.orange, Color.cyan,
      Color.magenta, Color.blue};

  /**
   * The colors.
   */
  private Paint[] colors;

  /**
   * Creates a new renderer.
   *
   * @param colors the colors.
   */
  public CustomBarRenderer(final Paint[] colors) {
    this.colors = colors;
  }

  /**
   * Returns the paint for an item.  Overrides the default behaviour inherited from
   * AbstractSeriesRenderer.
   *
   * @param row    the series.
   * @param column the category.
   * @return The item color.
   */
  @Override
  public Paint getItemPaint(final int row, final int column) {
    return this.colors[column % this.colors.length];
  }

  public Paint[] getColors() {
    return colors;
  }

  public void setColors(Paint[] colors) {
    this.colors = colors;
  }

}
