

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jdom2.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by melaniejohnson on 9/29/16.
 */
public class FeedAggregator {

    public static void main(String[] args) {

        try {

            List<SyndEntry> entries = new ArrayList();

            SyndFeedInput input = new SyndFeedInput();

            SyndFeed inFeed = input.build(new XmlReader(new URL("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml")));
            entries.addAll(inFeed.getEntries());

            //inFeed = input.build(new XmlReader(new URL("http://feeds.washingtonpost.com/rss/world")));
            //entries.addAll(inFeed.getEntries());

            inFeed = input.build(new XmlReader(new URL("http://feeds.reuters.com/Reuters/domesticNews")));
            entries.addAll(inFeed.getEntries());

            inFeed = input.build(new XmlReader(new URL("http://hosted2.ap.org/atom/APDEFAULT/3d281c11a96b4ad082fe88aa0db04305")));
            entries.addAll(inFeed.getEntries());

            entries.sort((o1, o2) -> {
                if(o1.getPublishedDate() == null || o2.getPublishedDate() == null){
                    return -1;
                }

                return o2.getPublishedDate().compareTo(o1.getPublishedDate());
            });


            for(SyndEntry entry : entries){
                printEntry(entry);
            }


        } catch (FeedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printEntry(SyndEntry entry) {
        String summary = "";

        SyndEntry entryImpl = new SyndEntryImpl();
        entryImpl.setTitle("entry title 1");
        entryImpl.setUri("http://localhost/feed/item1GUID");
        entryImpl.setLink("http://localhost/feed/item1");

        summary += "Title: " + entry.getTitle() + "\n";


        List<Element> images = entry.getForeignMarkup().stream().filter(element -> element.getNamespacePrefix().equals("media") && element.getName().equals("content")).collect(Collectors.toList());

        if(images.size() > 0){
            summary += "******** Image: " + images.get(0).getAttributeValue("url") + "\n";
        }
        //if(entry.getContents().size() > 0) {
        //    summary += "******** Image: " + entry.getContents().get(0) + "\n";
        //}
        summary += "Written by: " + entry.getAuthor() + "\n";
        summary += "Abstract: " + entry.getDescription().getValue() + "\n";
        summary += "Published: " + entry.getPublishedDate() + "\n";
        summary += "Link to article: " + entry.getLink() + "\n";
        summary += "guID: " + entry.getUri() + "\n";

        System.out.println(summary);

    }
}
