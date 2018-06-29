package com.example.chunk;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class PrintlnItemWriter implements ItemWriter<String> {

    @Override
    public void write(final List<? extends String> items) throws Exception {
        for (final String item : items) {
            System.out.println(item);
        }
    }
}
