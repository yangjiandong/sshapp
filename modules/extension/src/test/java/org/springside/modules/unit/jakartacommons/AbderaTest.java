package org.springside.modules.unit.jakartacommons;

import java.net.URL;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Category;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.parser.Parser;

public class AbderaTest {

    private static Abdera abdera = null;

    public static synchronized Abdera getInstance() {
        if (abdera == null) {
            abdera = new Abdera();
        }
        return abdera;
    }

    public static void main(String[] args) {
        
        Parser parser = getInstance().getParser();

        try {
            URL url = new URL("http://feeds2.feedburner.com/IntrepidStudiosExcerpts");
            Document<Feed> doc = parser.parse(url.openStream(), url.toString());
            Feed feed = doc.getRoot();
            // Get the feed title
            System.out.println("Feed Title: " + feed.getTitle());

            // Get the entry items...
            for (Entry entry : feed.getEntries()) {
                System.out.println("Title: " + entry.getTitle());
                System.out.println("Unique Identifier: " + entry.getId().toString());
                System.out.println("Updated Date: " + entry.getUpdated().toString());
                System.out.println("Published Date: " + entry.getPublished());
                System.out.println("Content: " + entry.getContent());

                // Get the links
                for (Link link : (List<Link>) entry.getLinks()) {
                    System.out.println("Link: " + link.getHref());
                }

                // Get the categories
                for (Category category : (List<Category>) entry.getCategories()) {
                    System.out.println("Category: " + category.getTerm());
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}