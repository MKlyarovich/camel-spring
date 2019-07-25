package ru.company.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ControllerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .host("localhost")
                .port(8090);

        rest("/camel")
                .get().to("direct:getAllDocumentsController")
                .post().to("direct:documentService")
                .post("/db").to("direct:addDocumentController");

        from("direct:getAllDocumentsController")
                .setHeader("type", constant("getAll"))
                .to("direct:documentService");

        from("direct:addDocumentController")
                .setHeader("type", constant("saveToDb"))
                .to("direct:documentService");
    }
}