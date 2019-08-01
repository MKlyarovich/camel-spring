package ru.company.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ServiceRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:documentService")
                .choice()
                    .when(header("type").isEqualTo("getAll"))
                        .to("direct:getAllDocumentsRepository")
                    .when(header("type").isEqualTo("getById"))
                        .to("direct:getDocumentByIdRepository")
                    .when(header("type").isEqualTo("saveToDb"))
                        .to("direct:addDocumentRepository")
                    .otherwise()
                        .to("direct:saveDocumentToFileSystem");
    }
}