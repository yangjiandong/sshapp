package org.springside.examples.showcase.report;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springside.examples.showcase.report.DummyDataFetcher.TemperatureAnomaly;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * FlashChart演示, 生成Amcharts所需 CVS/XML格式数据的Action.
 * 
 * @author calvin
 */
@Namespace("/report/flashchart")
public class ChartDataAction extends ActionSupport {

	private static final long serialVersionUID = -7651947806236556649L;

	/**
	 * 生成CSV格式的内容.
	 * 示例:
	 * 1970;-0.068;-0.108
	 * 1971;-0.120;-0.202
	 */
	public String report1() {
		TemperatureAnomaly[] temperatureAnomalyArray = DummyDataFetcher.getDummyData();
		StringBuilder output = new StringBuilder();
		for (TemperatureAnomaly data : temperatureAnomalyArray) {
			output.append(data.getYear()).append(";").append(data.getAnomaly()).append(";").append(data.getSmoothed())
					.append("\n");
		}
		Struts2Utils.render("text/csv", output.toString());
		return null;
	}

	/**
	 * 使用Dom4j生成XML格式内容.
	 * 示例:
	 * <chart>
	 * 	<series>
	 * 		<value xid="1">1950</value>
	 * 	</series>
	 * 	<graphs>
	 * 		<graph gid="1">
	 * 			<value xid="1" color="#318DBD" url="/showcase/report/flashchart/report2-drilldown.action?id=100">-0.307</value>
	 * 		</graph>
	 * 		<graph gid="2">
	 * 			<value xid="1">-0.171</value>
	 * 		</graph>
	 * 	</graphs>
	 * </chart>
	 */
	public String report2() throws IOException {
		TemperatureAnomaly[] temperatureAnomalyArray = DummyDataFetcher.getDummyData();
		String url = Struts2Utils.getRequest().getContextPath() + "/report/flashchart/report2-drilldown.action?id=";

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("chart");
		Element series = root.addElement("series");
		Element graphs = root.addElement("graphs");
		Element graphAnomaly = graphs.addElement("graph").addAttribute("gid", "1");
		Element graphSmoothed = graphs.addElement("graph").addAttribute("gid", "2");

		for (int i = 0; i < temperatureAnomalyArray.length; i++) {
			String xid = String.valueOf(i);
			TemperatureAnomaly data = temperatureAnomalyArray[i];

			series.addElement("value").addAttribute("xid", xid).addText(String.valueOf(data.getYear()));

			Element graphAnomalyValue = graphAnomaly.addElement("value").addAttribute("xid", String.valueOf(i))
					.addText(String.valueOf(data.getAnomaly()));

			graphAnomalyValue.addAttribute("url", url + i);

			if (data.getAnomaly() < 0) {
				graphAnomalyValue.addAttribute("color", "#318DBD");
			}

			graphSmoothed.addElement("value").addAttribute("xid", xid).addText(String.valueOf(data.getSmoothed()));
		}

		HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType("text/xml;charset=utf-8");

		OutputFormat format = OutputFormat.createCompactFormat();
		XMLWriter xmlWriter = new XMLWriter(response.getWriter(), format);
		xmlWriter.write(document);
		xmlWriter.close();

		return null;
	}
}
