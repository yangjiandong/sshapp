package org.springside.modules.utils.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManifestAdapterContextLoadListener implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(ManifestAdapterContextLoadListener.class);

    /**
    * (non-Javadoc)
    * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
    */
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Map<String, Object> manifest = new HashMap<String, Object>();
            InputStream is = sce.getServletContext().getResourceAsStream("META-INF/MANIFEST.MF");
            Properties props = new Properties();
            props.load(is);
            Set<Entry<Object, Object>> set = props.entrySet();

            for (Entry<Object, Object> entry : set) {
                manifest.put(entry.getKey().toString(), entry.getValue());
            }
            sce.getServletContext().setAttribute("manifest", manifest);

        } catch (Exception e) {
            logger.error("Unable to poulate the Manifest Map.", e);
        }

    }

    /**
    * (non-Javadoc)
    * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
    */
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
