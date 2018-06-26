package com.airlenet.crawler.heritrix.modules;

/**
 * @author airlenet
 * @version 2018-03-19
 */
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.URIException;
import org.archive.crawler.frontier.QueueAssignmentPolicy;
import org.archive.modules.CrawlURI;
import org.archive.net.UURI;
import org.archive.net.UURIFactory;

/**
 * QueueAssignmentPolicy based on the hostname:port evident in the given
 * CrawlURI.
 *
 * @author gojomo
 */
public class ELFHashQueueAssignmentPolicy extends QueueAssignmentPolicy {
    private static final Logger logger = Logger
            .getLogger(ELFHashQueueAssignmentPolicy.class.getName());
    /**
     * When neat host-based class-key fails us
     */
    private static String DEFAULT_CLASS_KEY = "default...";

    private static final String DNS = "dns";

    public static long ELFHash(String str) {
        long hash = 0;
        long x = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = hash & 0xF0000000L) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return (hash & 0x7FFFFFFF);
    }


    /**
     * Get the String key (name) of the queue to which the
     * CrawlURI should be assigned.
     * <p>
     * Note that changes to the CrawlURI, or its associated
     * components (such as CrawlServer), may change its queue
     * assignment.
     *
     * @param cauri CandidateURI to calculate class key for.
     * @return the String key of the queue to assign the CrawlURI
     */
    @Override
    public String getClassKey(CrawlURI cauri) {
        String scheme = cauri.getUURI().getScheme();
        String candidate = null;
        try {
            if (scheme.equals(DNS)){
                if (cauri.getVia() != null) {
                    UURI viaUuri = UURIFactory.getInstance(cauri.flattenVia());
                    candidate = viaUuri.getAuthorityMinusUserinfo();
                    // adopt scheme of triggering URI
                    scheme = viaUuri.getScheme();
                } else {
                    candidate= cauri.getUURI().getReferencedHost();
                }
            } else {
                String uri = cauri.getUURI().toString();
                long hash = ELFHash(uri);
                //candidate =  cauri.getUURI().getAuthorityMinusUserinfo();
                candidate = Long.toString(hash % 100);
            }

            if(candidate == null || candidate.length() == 0) {
                candidate = DEFAULT_CLASS_KEY;
            }
        } catch (URIException e) {
            logger.log(Level.INFO,
                    "unable to extract class key; using default", e);
            candidate = DEFAULT_CLASS_KEY;
        }
        if (scheme != null && scheme.equals(UURIFactory.HTTPS)) {
            // If https and no port specified, add default https port to
            // distinguish https from http server without a port.
            if (!candidate.matches(".+:[0-9]+")) {
                candidate += UURIFactory.HTTPS_PORT;
            }
        }
        // Ensure classKeys are safe as filenames on NTFS
        return candidate.replace(':','#');
    }
}