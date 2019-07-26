package ru.company.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MoveFilesProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

        String fileName = localDateTime.format(dateTimeFormatter) + ".json";

        exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
    }
}