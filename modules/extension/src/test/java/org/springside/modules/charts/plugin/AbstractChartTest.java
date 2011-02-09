package org.springside.modules.charts.plugin;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import static org.junit.Assert.assertTrue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractChartTest {
  protected void assertChartSizeGreaterThan(BufferedImage img, int size) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ChartUtilities.writeBufferedImageAsPNG(output, img);
    assertTrue("PNG size in bits=" + output.size(), output.size() > size);
  }

  protected void assertChartSizeLesserThan(BufferedImage img, int size) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ChartUtilities.writeBufferedImageAsPNG(output, img);
    assertTrue("PNG size in bits=" + output.size(), output.size() < size);
  }

  protected void saveChart(BufferedImage img, String name) throws IOException {
    File target = new File("target/tmp-chart", name);
    FileUtils.forceMkdir(target.getParentFile());
    ByteArrayOutputStream imgOutput = new ByteArrayOutputStream();
    ChartUtilities.writeBufferedImageAsPNG(imgOutput, img);
    OutputStream out = new FileOutputStream(target);
    out.write(imgOutput.toByteArray());
    out.close();

  }

  protected static void displayTestPanel(BufferedImage image) throws IOException {
    ApplicationFrame frame = new ApplicationFrame("testframe");
    BufferedPanel imgPanel = new BufferedPanel(image);
    frame.setContentPane(imgPanel);
    frame.pack();
    RefineryUtilities.centerFrameOnScreen(frame);
    frame.setVisible(true);
  }

  protected static Date stringToDate(String sDate) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy hh'h'mm");
    return sdf.parse(sDate);
  }

  private static class BufferedPanel extends JPanel {
    private BufferedImage chartImage;

    public BufferedPanel(BufferedImage chartImage) {
      this.chartImage = chartImage;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      graphics.drawImage(chartImage, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(chartImage.getWidth(), chartImage.getHeight());
    }
  }
}
