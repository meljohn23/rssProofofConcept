import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by melaniejohnson on 9/29/16.
 */
public class FeedAggregator {

    public static void main(String[] args) {
        boolean ok = false;
        if (args.length>=2) try {
            String outputType = args[0];

            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType(outputType);

            feed.setTitle("NYTimes Home Page");
            feed.setDescription("NY Times National News Feed");
            feed.setAuthor("New York Times");
            feed.setLink("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml");

            List entries = (List) new ArrayList();
            feed.setEntries((java.util.List) entries);

            for (int i = 1; i < args.length; i++) {
                URL inputUrl = new URL(args[i]);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed inFeed = input.build(new XmlReader(new URL("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml")));

                ((java.util.List) entries).addAll(inFeed.getEntries());

            }

            SyndFeedOutput output = new SyndFeedOutput();
            output.output(feed, new PrintWriter(System.out));

            ok = true;
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        if (!ok) {
            System.out.println();
            System.out.println("FeedAggregator aggregates different feeds into a single one.");
            System.out.println("The first parameter must be the feed type for the aggregated feed.");
            System.out.println(" [valid values are: rss_0.9, rss_0.91, rss_0.92, rss_0.93, ]");
            System.out.println(" [                  rss_0.94, rss_1.0, rss_2.0 & atom_0.3  ]");
            System.out.println("The second to last parameters are the URLs of feeds to aggregate.");
            System.out.println();
        }
    }
}
