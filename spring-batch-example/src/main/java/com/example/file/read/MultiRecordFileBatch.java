package com.example.file.read;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MultiRecordFileBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public FlatFileItemReader<MultiRecordItem> multiRecordFileItemReader() {
		return new FlatFileItemReaderBuilder<MultiRecordItem>()
				.resource(new PathResource("inputs/input-multi-record-fixed.txt"))
				.lineMapper(multiRecordFilePatternMatchingCompositeLineMapper())
				.saveState(false)
				.build();
	}

	@Bean
	public PatternMatchingCompositeLineMapper<MultiRecordItem> multiRecordFilePatternMatchingCompositeLineMapper() {
		PatternMatchingCompositeLineMapper<MultiRecordItem> lineMapper = new PatternMatchingCompositeLineMapper<>();

		Map<String, FieldSetMapper<MultiRecordItem>> fieldSetMappers = new HashMap<>();
		fieldSetMappers.put("H*", fieldSetMapper(MultiRecordItem.Header.class));
		fieldSetMappers.put("D*", fieldSetMapper(MultiRecordItem.Data.class));
		fieldSetMappers.put("F*", fieldSetMapper(MultiRecordItem.Footer.class));
		lineMapper.setFieldSetMappers(fieldSetMappers);

		FixedLengthTokenizer headerLineTokenizer = new FixedLengthTokenizer();
		headerLineTokenizer.setColumns(new Range(1, 1), new Range(2, 6));
		headerLineTokenizer.setNames("classifier", "filler");

		FixedLengthTokenizer dataLineTokenizer = new FixedLengthTokenizer();
		dataLineTokenizer.setColumns(new Range(1, 1), new Range(2, 4), new Range(5, 6));
		dataLineTokenizer.setNames("classifier", "name", "number");

		FixedLengthTokenizer footerLineTokenizer = new FixedLengthTokenizer();
		footerLineTokenizer.setColumns(new Range(1, 1), new Range(2, 4), new Range(5, 6));
		footerLineTokenizer.setNames("classifier", "count", "filler");

		Map<String, LineTokenizer> tokenizers = new HashMap<>();
		tokenizers.put("H*", headerLineTokenizer);
		tokenizers.put("D*", dataLineTokenizer);
		tokenizers.put("F*", footerLineTokenizer);
		lineMapper.setTokenizers(tokenizers);

		return lineMapper;
	}

	private static BeanWrapperFieldSetMapper<MultiRecordItem> fieldSetMapper(Class<? extends MultiRecordItem> type) {
		BeanWrapperFieldSetMapper<MultiRecordItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(type);
		fieldSetMapper.setCustomEditors(Map.of(String.class, new StringTrimmerEditor(true)));
		fieldSetMapper.setConversionService(new DefaultFormattingConversionService());
		return fieldSetMapper;
	}

	@Bean
	public PassThroughItemProcessor<MultiRecordItem> multiRecordFileItemProcessor() {
		return new PassThroughItemProcessor<>();
	}

	@Bean
	public ListItemWriter<MultiRecordItem> multiRecordFileItemWriter() {
		return new ListItemWriter<>();
	}

	@Bean
	public Step multiRecordFileStep() {
		return new StepBuilder("MultiRecordFile", jobRepository)
				.<MultiRecordItem, MultiRecordItem> chunk(10, transactionManager)
				.reader(multiRecordFileItemReader())
				.processor(multiRecordFileItemProcessor())
				.writer(multiRecordFileItemWriter())
				.build();
	}

	@Bean
	public Job multiRecordFileJob() {
		return new JobBuilder("MultiRecordFile", jobRepository)
				.start(multiRecordFileStep())
				.build();
	}
}
