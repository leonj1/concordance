import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * Essentially the Controller in the MVC pattern
 * Created by jose on 2/7/15.
 */
class ConcordanceGenerator {
    private final static Logger log = Logger.getLogger(ConcordanceGenerator.class);

    private String website;
    private SiteDAO siteDao;
    private ConcordanceModel model;

    public ConcordanceGenerator(String webSite, SiteDAO siteDAO) {
        Preconditions.checkArgument(webSite != null, "'website' cannot be null");
        Preconditions.checkArgument(siteDAO != null, "'siteDAO' cannot be empty");
        Preconditions.checkArgument(!webSite.isEmpty(), "'website' cannot be empty");
        this.website = webSite;
        this.siteDao = siteDAO;
        this.model = new ConcordanceModel();
    }

    /**
     * Return the Concordance in the format specified.
     * Typically this would return a json and some View class would handle the formatting.
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getConcordance() {
        siteDao = new SiteDAO(website);
        try {
            log.info(String.format("Getting website contents of %s", website));
            siteDao.populateModel(model);
        } catch (Exception e) {
            log.error(String.format("Unable to retrieve website contents from: %s due to %s",website, e.getMessage()));
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        Map<String, Integer> frequencyMap = model.getWordFrequency();
        Map<String, Set<String>> locationMap = model.getWordLocation();

        for(String word : frequencyMap.keySet()) {
            Set set = locationMap.get(word);
            String lines = StringUtils.join(set, ",");
            sb.append(String.format("%s\t{%s:%s}\n", word, frequencyMap.get(word), lines));
        }

        return sb.toString();
    }
}
