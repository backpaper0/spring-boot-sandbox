package com.example.file.read;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

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
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	@StepScope
	public FlatFileItemReader<ExampleItem> noLineBreaksFlatFileExampleReader(LineSplitter lineSplitter) {
		return new FlatFileItemReaderBuilder<ExampleItem>()
				.resource(new PathResource(lineSplitter.getPath()))
				.encoding("UTF-8")
				.saveState(false)
				.targetType(ExampleItem.class)
				.fixedLength()
				.columns(
						new Range(1, 3),
						new Range(4, 8))
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
				.<ExampleItem, ExampleItem> chunk(1, transactionManager)
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
	public static class LineSplitter implements InitializingBean {

		private Path path;

		@Override
		public void afterPropertiesSet() throws Exception {
			path = Files.createTempFile("oneline-splitted-", ".txt");
		}

		public Path getPath() {
			return path;
		}

		@BeforeStep
		public void beforeStep(StepExecution stepExecution) throws IOException {
			try (BufferedReader in = Files.newBufferedReader(Path.of("inputs/oneline.txt"));
					BufferedWriter out = Files.newBufferedWriter(path)) {
				char[] cs = new char[8];
				int i;
				while (-1 != (i = in.read(cs))) {
					out.write(cs, 0, i);
					out.newLine();
				}
			}
		}

		@AfterStep
		public void afterStep(StepExecution stepExecution) throws IOException {
			Files.deleteIfExists(Path.of("target/oneline-splitted.txt"));
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
