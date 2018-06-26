import org.archive.util.InetAddressUtil;

import java.util.regex.Matcher;

/**
 * @author airlenet
 * @version 2018-06-25
 */
public class Test {
    public static void main(String args[]){
        Matcher matcher = InetAddressUtil.IPV4_QUADS.matcher("www.axureux.com");
        System.out.println(matcher.matches());
    }
}
