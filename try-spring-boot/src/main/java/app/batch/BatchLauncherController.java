package app.batch;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Jobを起動するController。
 * 
 */
@RestController
public class BatchLauncherController {

    @Autowired
    private JobLauncher launcher;

    /*
     * TODO 名前をつけてインジェクションしたい！
     * CDIのNamedに相当するのはQualifier？
     * Beanを定義する側での設定が分からん。
     * @Bean(name = "job3")とかやってもダメっぽかった。。。
     */
    @Autowired
    private Job job;

    private final AtomicLong idGenerator = new AtomicLong(1);

    @RequestMapping("/launch")
    public String launch() throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException,
            JobParametersInvalidException {

        //JobParametersの内容を変更しないと同一のジョブ実行と思われるっぽい。
        //同一のジョブ実行だと思われたら二回目からは実行されない。
        //(一回目の実行が既に完了しているので)
        //とりあえずIDっぽいものを持たせて実行の要求の度にインクリメントすることで
        //何度も実行できるようになった。
        //cf. JobParametersIncrementer
        JobParameters jobParameters = new JobParametersBuilder().addLong(
                "simpleBatchId", idGenerator.getAndIncrement())
                .toJobParameters();
        JobExecution execution = launcher.run(job, jobParameters);

        return execution.toString();
    }
}
