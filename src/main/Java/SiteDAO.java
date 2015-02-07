import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Our DAO to get text from a website and populates our model
 * Created by jose on 2/7/15.
 */
class SiteDAO {
    private final static Logger log = Logger.getLogger(SiteDAO.class);

    private String website;

    public SiteDAO(String website) {
        Preconditions.checkArgument(website != null, "'website' cannot be null");
        Preconditions.checkArgument(!website.isEmpty(), "'website' cannot be empty");
        this.website = website;
    }

    /**
     * Populate model with contents from a website
     * @param model         our model class of the Concordance
     * @throws Exception    is there's a problem getting contents from the website
     */
    public void populateModel(ConcordanceModel model) throws Exception {
        URL oracle = new URL(website);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        int lineNumber = 1;

        /**
         * Process each line at a time since the size of the contents could not be determined at the time,
         * therefore prevent JVM from running out of memory
         */
        while ((inputLine = in.readLine()) != null) {
            model.processLine(inputLine, Integer.toString(lineNumber++));
        }
        in.close();
    }
}