import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.FilterHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.servlet.GzipFilter;

// 以开发方式启动
public class StartDevMode {
    public static void main(String[] args) {
        setupParameters();
        startJettyServer();
    }

    private static void startJettyServer() {
        //并发
        //ab -k -c 900 -n 1000 http://localhost:8081/sp/userlist.htm
        // k 持继连接
        //模拟900个用户并发1000次

        Server server = new Server();
        SocketConnector connector = new SocketConnector();
        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(8081);
        server.setConnectors(new Connector[] { connector });

        WebAppContext appContext = new WebAppContext();
        appContext.setServer(server);
        appContext.setContextPath("/we");
        appContext.setWar("src/main/webapp/");

        //compression
        appContext.addFilter(new FilterHolder(new GzipFilter()), "/*",
                org.mortbay.jetty.Handler.ALL);

        server.addHandler(appContext);
        try {
            server.start();
            while (System.in.available() == 0) {
                Thread.sleep(5000);
            }
            server.stop();
            server.join();
        } catch (Exception e) {
            System.exit(100);
        }
    }

    private static void setupParameters() {

        // 设置SYS_PROP,home
    }
}