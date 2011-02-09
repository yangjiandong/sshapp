package org.springside.modules.charts.deprecated;

import java.io.IOException;
import java.io.OutputStream;

public interface DeprecatedChart {

  void exportChartAsPNG(OutputStream out) throws IOException;

}
