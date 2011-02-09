package org.springside.modules.charts.deprecated;

import java.util.Iterator;
import java.util.Map;

public abstract class BaseChartWebTest extends BaseChartTest {

  protected String generateUrl(Map<String, String> params) {
    StringBuilder servletUrl = new StringBuilder("chart?");
    for (Iterator<String> keyIt = params.keySet().iterator(); keyIt.hasNext();) {
      String key = keyIt.next();
      servletUrl.append("&").append(key).append("=").append(params.get(key));
    }
    return servletUrl.toString();
  }

}