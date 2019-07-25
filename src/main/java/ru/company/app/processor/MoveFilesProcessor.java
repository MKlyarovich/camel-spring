package ru.company.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import ru.company.app.util.DateUtils;

@Component
public class MoveFilesProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        String processingDateTime = DateUtils.currentLocalDateTimeToStringByFormat("yyyy-MM-dd_HH-mm-ss-SSS");
        String fileName = processingDateTime + ".json";

        exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
    }
}