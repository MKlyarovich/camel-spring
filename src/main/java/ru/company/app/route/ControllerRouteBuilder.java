package ru.company.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.company.app.model.Document;
import ru.company.app.processor.BeanValidationExceptionProcessor;

@Component
public class ControllerRouteBuilder extends RouteBuilder {

    private final BeanValidationExceptionProcessor beanValidationExceptionProcessor;
    private final JsonDataFormat jsonDataFormat;

    @Autowired
    public ControllerRouteBuilder(BeanValidationExceptionProcessor beanValidationExceptionProcessor, JsonDataFormat jsonDataFormat) {
        this.beanValidationExceptionProcessor = beanValidationExceptionProcessor;
        this.jsonDataFormat = jsonDataFormat;
    }

    @Override
    public void configure() {
        jsonDataFormat.setUnmarshalType(Document.class);

        restConfiguration()
                .component("servlet")
                .host("localhost")
                .port(8090);

        rest("/camel")
                .get().to("direct:getAllDocumentsController")
                .post().to("direct:documentService")
                .post("/db").to("direct:addDocumentController");

        onException(BeanValidationException.class)
                .handled(true)
                .process(beanValidationExceptionProcessor);

        from("direct:getAllDocumentsController")
                .setHeader("type", constant("getAll"))
                .to("direct:documentService");

        from("direct:addDocumentController")
                .unmarshal(jsonDataFormat)
                .to("bean-validator:documentValidate")
                .setHeader("type", constant("saveToDb"))
                .to("direct:documentService");
    }
}