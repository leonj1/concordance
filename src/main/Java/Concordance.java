import org.apache.log4j.Logger;

/**
 * Created by Jose Leon on 2/7/15.
 */
public class Concordance {
    private final static Logger log = Logger.getLogger(Concordance.class);

    public static void main(String[] args) {
        String siteToParse = "http://www.gutenberg.org/cache/epub/5/pg5.txt";

        SiteDAO siteDao = new SiteDAO(siteToParse);

        ConcordanceGenerator concordance = new ConcordanceGenerator(siteToParse, siteDao);
        log.info(String.format("The Concordance\n%s", concordance.getConcordance()));
    }
}
