package ru.company.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class BeanValidationExceptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        exchange.getIn().setBody(exception.getMessage());
    }
}