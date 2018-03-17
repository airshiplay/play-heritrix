package  com.airlenet.crawler.heritrix;

import com.airlenet.integration.core.ApplicationInitializer;
import com.airlenet.play.main.entity.PermissionEntity.PermissionType;
import com.airlenet.play.main.entity.MenuEntity;
import com.airlenet.play.main.InitDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlerHeritrixApplicationInitializer extends ApplicationInitializer {
    @Autowired
    private InitDataTools tools;

    @Override
    public void onRootContextRefreshed() {

        int sortNo=5;
        if (!tools.existMenuCode("crawlerHeritrix_management")) {
            MenuEntity crawlerHeritrixManagement = tools.createMenuByParent("", "crawlerHeritrix_management","fa fa-wpforms", null, null, sortNo++, null);
            tools.createMenuByParent("", "crawlerHeritrix_crawlertask_list", "fa fa-file-text","page/crawlerHeritrix/crawlerTask/crawlerTaskList", null, sortNo++, crawlerHeritrixManagement);
        }
    }
}
