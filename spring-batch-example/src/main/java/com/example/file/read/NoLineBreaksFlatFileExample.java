package com.example.file.read;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.infrastructure.item.file.transform.Range;
import org.springframework.batch.infrastructure.item.support.ListItemWriter;
import org.springframework.batch.infrastructure.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * 改行ではなく長さで区切る固定長ファイルを読み込む。
 * FlatFileItemReaderはそういったファイルには対応していなさそう。
 * そのためBeforeStepイベントリスナーで行に分割したファイルを作って、
 * それをFlatFileItemReaderで処理する。
 *
 */
@Configuration
@RequiredArgsConstructor
public class NoLineBreaksFlatFileExample {

    @Autowired
    private JobRepository jobRepository;

    @Bean
    @StepScope
    public FlatFileItemReader<ExampleItem> noLineBreaksFlatFileExampleReader(LineSplitter lineSplitter) {
        return new FlatFileItemReaderBuilder<ExampleItem>()
                .resource(new FileSystemResource(lineSplitter.getPath()))
                .encoding("UTF-8")
                .saveState(false)
                .targetType(ExampleItem.class)
                .fixedLength()
                .columns(new Range(1, 3), new Range(4, 8))
                .names("id", "content")
                .recordSeparatorPolicy(new BlankLineIsNullPolicy())
                .build();
    }

    @Bean
    public PassThroughItemProcessor<ExampleItem> noLineBreaksFlatFileExampleProcessor() {
        return new PassThroughItemProcessor<>();
    }

    @Bean
    public ListItemWriter<ExampleItem> noLineBreaksFlatFileExampleWriter() {
        return new ListItemWriter<>();
    }

    @Bean
    public Step noLineBreaksFlatFileExampleStep(LineSplitter lineSplitter) {
        return new StepBuilder("noLineBreaksFlatFileExampleStep", jobRepository)
                .<ExampleItem, ExampleItem>chunk(1)
                .listener(lineSplitter)
                .reader(noLineBreaksFlatFileExampleReader(null))
                .processor(noLineBreaksFlatFileExampleProcessor())
                .writer(noLineBreaksFlatFileExampleWriter())
                .build();
    }

    @Bean
    public Job noLineBreaksFlatFileExampleJob() {
        return new JobBuilder("noLineBreaksFlatFileExampleJob", jobRepository)
                .start(noLineBreaksFlatFileExampleStep(null))
                .build();
    }

    /**
     * BeforeStepイベントリスナーで行に分割した一時ファイルを作成する。
     * AfterStepイベントリスナーで一時ファイルを削除する。
     *
     */
    @Component
    @StepScope
    public static class LineSplitter implements InitializingBean, StepExecutionListener {

        private Path path;

        @Override
        public void afterPropertiesSet() throws Exception {
            path = Files.createTempFile("oneline-splitted-", ".txt");
            System.out.println(path);
        }

        public Path getPath() {
            return path;
        }

        @Override
        public void beforeStep(StepExecution stepExecution) {
            try (BufferedReader in = Files.newBufferedReader(Path.of("inputs/oneline.txt"));
                    BufferedWriter out = Files.newBufferedWriter(path)) {
                char[] cs = new char[8];
                int i;
                while (-1 != (i = in.read(cs))) {
                    out.write(cs, 0, i);
                    out.newLine();
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
            }
            return null;
        }
    }

    /**
     * 空行はnull扱い。
     *
     */
    public static class BlankLineIsNullPolicy extends DefaultRecordSeparatorPolicy {

        @Override
        public String postProcess(String record) {
            if (record != null && record.isEmpty()) {
                return null;
            }
            return record;
        }
    }
}
