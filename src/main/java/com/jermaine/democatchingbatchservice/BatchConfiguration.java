package com.jermaine.democatchingbatchservice;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
    // end::jobstep[]
}


    //first chunk of code defines the input, processor, and output
    //The next chunk focuses on the actual job configuration.
    //@EnableBatchProcessing annotations adds many critical beans that support jobs and saves you a lot of leg work
    //this example uses a memory-based database, meaning that when it's done, the data is gone
    //reader() creates an ItemReader
    //ItemReader is a strategy interface for providing the data.
    //sample.data.csv is a file
    //parses each line item with enough information to turn it into a Person.
    // processor() creates an instance of PersonItemProcessor defined earlie meant to uppercase the data
    //write(dataCourse) creates an ItemWriter
    //ItemWriter is a basic interface for generic output operations
    //EnableBatchProcessing is used to enable Spring Batch features and provide a base configuration for setting up batch jobs in an @Configuration class
    //The first method defines the job and the second one defines a single step.
    //FlatFileItemReader is a restartable ItemReader that reads lines from input setResource(Resource).
    // FlatFileItemReaderBuilder is A builder implementation for the FlatFileItemReader
    //itemSqlParameterSourceProvider A convenient implementation for providing BeanPropertySqlParameterSource when the item has JavaBean properties that correspond to names used for parameters in the SQL statement.
    //delimited() is a sequence of one or more characters used to specify the boundary between separate, independent regions in plain text or other data streams.
    //The FieldSetMapper interface defines a single method, mapFieldSet, which takes a FieldSet object and maps its contents to an object.
    //A FieldSet is Spring Batch’s abstraction for enabling the binding of fields from a file resource.
    //JdbcBatchItemWriterBuilder is a builder implementation for the JdbcBatchItemWriter
    //DataSource object is the preferred means of getting a connection.
    //JobBuilderFactory is a convenient factory for a JobBuilder which sets the JobRepository automatically.
    //JobBuilder is a Convenience for building jobs of various kinds
    //increment() adds 1 to a variable
    //listener is designed to process some kind of event
    //flow is a Java switch statement
    //chunk() is a Template Engine for Java
    //reader() is an abstract class for reading character streams
    //processor is the implementation of the Java virtual machine (JVM)
    //writer() is intended for reading and writing text.
    //In this job definition, you need an incrementer because jobs use a database to maintain execution state.
    // then list each step, of which this job has only one step. The job ends, and the Java API produces a perfectly configured job.
    //In the step definition, you define how much data to write at a time