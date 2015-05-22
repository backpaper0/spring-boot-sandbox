package app.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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
 * TODO 起動の要求はできたけど既に完了したジョブだと言われて起動しない。
 * JobのスコープがSingletonだから？
 * スコープを変更して起動済みのJobと異なるインスタンスにすれば良い？
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

    @RequestMapping("/launch")
    public String launch() throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException,
            JobParametersInvalidException {

        JobParameters jobParameters = new JobParameters();
        launcher.run(job, jobParameters);

        return "launched";
    }
}
