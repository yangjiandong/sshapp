1.jetty.xml:
  a.connector statics to true.
  b.remove unuse setting.
  c.set connector from SelectChannelConnector to BlockingChannelConnector for graceful shutdown
  d.set gracefulshutdown from 1 seconds to 3.
2.jetty-jmx.xml: no customize yet.