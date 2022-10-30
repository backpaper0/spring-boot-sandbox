package com.example.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.WritableResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link RandomAccessFile}を使って後から固定長ファイルのヘッダーに処理件数を書き込む例。
 *
 */
@SpringBootTest
public class FlatFileWriteCountToHeaderExampleTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;

	static final WritableResource resource = new PathResource("target/output.txt");

	@Test
	void test() throws Exception {
		jobLauncher.run(config.job(), new JobParameters());

		byte[] b;
		try (InputStream in = resource.getInputStream()) {
			b = in.readAllBytes();
		}
		String s = new String(b, StandardCharsets.UTF_8);

		System.out.println(s);
	}

	static class FileDeleteStepExecutionListener extends StepExecutionListenerSupport {
		@Override
		public void beforeStep(StepExecution stepExecution) {
			if (resource.exists()) {
				try {
					Files.delete(resource.getFile().toPath());
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class ExampleItem {
		private Integer value;
	}

	static class ExampleFieldExtractor implements FieldExtractor<ExampleItem> {

		@Override
		public Object[] extract(ExampleItem item) {
			return new Object[] {
					item.getValue()
			};
		}
	}

	static class WriteCountToHeaderStepExecutionListener extends StepExecutionListenerSupport {
		@Override
		public ExitStatus afterStep(StepExecution stepExecution) {
			long count = stepExecution.getWriteCount();
			String s = String.format("%04d", count);
			try (RandomAccessFile raf = new RandomAccessFile(resource.getFile(), "rw")) {
				raf.seek(0L);
				raf.write(s.getBytes());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			return null;
		}
	}

	@TestConfiguration
	static class TestConfig {

		@Autowired
		StepBuilderFactory steps;
		@Autowired
		JobBuilderFactory jobs;

		@Bean
		public IteratorItemReader<ExampleItem> itemReader() {
			Iterator<ExampleItem> iterator = IntStream.rangeClosed(1, 10).mapToObj(ExampleItem::new).iterator();
			return new IteratorItemReader<>(iterator);
		}

		@Bean
		public PassThroughItemProcessor<ExampleItem> itemProcessor() {
			return new PassThroughItemProcessor<>();
		}

		@Bean
		public FlatFileItemWriter<ExampleItem> itemWriter() {
			return new FlatFileItemWriterBuilder<ExampleItem>()
					.resource(resource)
					.lineSeparator("\n")
					.formatted()
					.format("%s")
					.fieldExtractor(new ExampleFieldExtractor())
					.saveState(false)
					.name("test")
					.headerCallback(new FlatFileHeaderCallback() {
						@Override
						public void writeHeader(Writer writer) throws IOException {
							writer.write("9999");
						}
					})
					.build();
		}

		@Bean
		public Step step() {
			return steps.get("test")
					.<ExampleItem, ExampleItem> chunk(3)
					.reader(itemReader())
					.processor(itemProcessor())
					.writer(itemWriter())
					.listener(new FileDeleteStepExecutionListener())
					.listener(new WriteCountToHeaderStepExecutionListener())
					.build();
		}

		@Bean
		public Job job() {
			return jobs.get("test")
					.start(step())
					.build();
		}
	}

}
