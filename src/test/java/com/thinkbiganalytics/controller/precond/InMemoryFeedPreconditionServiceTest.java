package com.thinkbiganalytics.controller.precond;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.thinkbiganalytics.controller.precond.metric.DatasetUpdatedSinceMetric;
import com.thinkbiganalytics.controller.precond.metric.FeedExecutedSinceScheduleMetric;
import com.thinkbiganalytics.controller.precond.metric.WithinSchedule;
import com.thinkbiganalytics.metadata.sla.spi.core.InMemorySLAProvider;

public class InMemoryFeedPreconditionServiceTest {
    
    private InMemoryFeedPreconditionService service = new InMemoryFeedPreconditionService();

    @Before
    public void setUp() throws Exception {
        this.service.setPrivider(new InMemorySLAProvider());
    }

    @Test
    public void testCreatePrecondition() throws ParseException {
        FeedExecutedSinceScheduleMetric metric1 = new FeedExecutedSinceScheduleMetric("x", "0 0 6 * * ? *");
        DatasetUpdatedSinceMetric metric2 = new DatasetUpdatedSinceMetric("datasetX", "0 0 6 * * ? *");
        WithinSchedule metric3 = new WithinSchedule("0 0 6 * * ? *", "2h");
        
        FeedPrecondition pre = this.service.createPrecondition("test", metric1, metric2, metric3);
        
        assertThat(pre).isNotNull();
        
        assertThat(this.service.getPrecondition(pre.getId())).isNotNull();
        assertThat(this.service.getPrecondition(pre.getName())).isNotNull();
    }

}
