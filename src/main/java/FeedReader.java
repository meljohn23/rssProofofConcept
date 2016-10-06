
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class FeedReader {

    public static void main(String[] args) throws IOException, FeedException {
        boolean ok = false;
        if (args.length == 1) {
            try {
                URL feedUrl = new URL(args[0]);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(new URL("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml")));
                ArrayList<SyndFeed> feedList = new ArrayList<SyndFeed>();
                feedList.add(input.build(new XmlReader(new URL("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"))));
                feedList.add(input.build(new XmlReader(new URL("http://feeds.washingtonpost.com/rss/world"))));
                feedList.add(input.build(new XmlReader(new URL("http://feeds.reuters.com/Reuters/domesticNews"))));
                feedList.add(input.build(new XmlReader(new URL("http://hosted2.ap.org/atom/APDEFAULT/3d281c11a96b4ad082fe88aa0db04305"))));


//                System.out.println(feed);
                for (SyndFeed f : feedList) {
                    int numEntries = f.getEntries().size();
                    System.out.println("Number of articles: " + numEntries);
                    for (int i = 0; i < numEntries; i++) {
                        System.out.println(getEntry(f, i));
                    }
                }

                ok = true;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }

    private static String getEntry(SyndFeed feed, int e) {
        String summary = "";

        SyndEntry entry = (SyndEntry) feed.getEntries().get(e);
        SyndEntry entryImpl = new SyndEntryImpl();
        entryImpl.setTitle("entry title 1");
        entryImpl.setUri("http://localhost/feed/item1GUID");
        entryImpl.setLink("http://localhost/feed/item1");

        summary += "Title: " + entry.getTitle() + "\n";
        summary += "Written by: " + entry.getAuthor() + "\n";
        summary += "Abstract: " + entry.getDescription().getValue() + "\n";
        summary += "Published: " + entry.getPublishedDate() + "\n";
        summary += "Link to article: " + entry.getLink() + "\n";
        summary += "guID: " + entry.getUri() + "\n";


        return summary;
    }
}