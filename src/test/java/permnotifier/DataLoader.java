package permnotifier;
import org.joda.time.MutableDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
public class DataLoader {

	@Autowired
    private JobLauncher jobLauncher;
 
    @Autowired
    @Qualifier(value = "dolJob")
    private Job job;
 
    @Test
    public void loadTestData() throws Exception {
    	
    	MutableDateTime startDateTime = new MutableDateTime();
    	startDateTime.setMonthOfYear(2);
    	startDateTime.setDayOfMonth(7);
    	
    	for(int i = 0; i <= 3; i++) {
			startDateTime.addDays(1);
    		JobParametersBuilder builder = new JobParametersBuilder();
	        builder.addDate("startDate", startDateTime.toDate());
	        JobExecution jobExecution = jobLauncher.run(job,
	                builder.toJobParameters());
	        System.out.println(jobExecution.getExitStatus().getExitCode());	
    	}
    }

    
}
