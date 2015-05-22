package app.batch;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
 * ItemReader -> ItemProcessor -> ItemWriter の流れを試す。
 *
 */
@Component
public class ChunkedBatch {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job2() throws Exception {

        AtomicInteger source = new AtomicInteger(1);
        Logger logger = LoggerFactory.getLogger(ChunkedBatch.class);

        ItemReader<Integer> reader = () -> {
            int value = source.getAndIncrement();
            if (value > 100) {
                //nullを返したらデータの読み取り終了とみなされる
                return null;
            }
            return value;
        };

        ItemProcessor<Integer, String> processor = item -> {
            //nullを返したらItemWriterには渡さない
            if (item % 2 == 0 && item > 10) {
                return null;
            }
            return String.format("*%d*", item);
        };

        ItemWriter<String> writer = items -> logger.info("{}", items);

        Step step = steps.get("step2")
                //10個ずつItemWriterに渡される
                .<Integer, String> chunk(10).reader(reader)
                .processor(processor).writer(writer).build();

        return jobs.get("job2").start(step).build();
    }
}
