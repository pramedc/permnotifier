package permnotifier;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataLoader {

    public static void main(String[] args) throws Exception {
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring/applicationContext*.xml");
        JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
        Job job = context.getBean("initialDataJob", Job.class);
    	
    	JobParametersBuilder builder = new JobParametersBuilder();
//        builder.addDate("startDate", startDateTime.toDate());
        JobExecution jobExecution = jobLauncher.run(job,
                builder.toJobParameters());
    }

    
}
