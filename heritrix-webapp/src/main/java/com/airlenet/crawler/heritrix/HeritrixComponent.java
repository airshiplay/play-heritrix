package com.airlenet.crawler.heritrix;

import com.airlenet.config.StaticConfigSupplier;
import org.archive.crawler.framework.Engine;
import org.archive.crawler.restlet.EngineApplication;
import org.restlet.Component;
import org.restlet.data.Protocol;

import java.io.File;

/**
 * @author airlenet
 * @version 2017-12-31
 */
public class HeritrixComponent extends Component {

    public HeritrixComponent() {
        String jobsDir = StaticConfigSupplier.getConfiguration().getString("heritrix.jobs-dir", "./jobs");
        getDefaultHost().attach("" , new EngineApplication(new Engine(new File(jobsDir))));
        getClients().add(Protocol.FILE);
        getClients().add(Protocol.CLAP);
    }
}
