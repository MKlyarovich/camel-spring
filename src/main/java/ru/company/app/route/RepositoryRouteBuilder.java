package ru.company.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.company.app.builder.DocumentBuilder;
import ru.company.app.model.Document;

@Component
public class RepositoryRouteBuilder extends RouteBuilder {

    private final DocumentBuilder documentBuilder;

    @Autowired
    public RepositoryRouteBuilder(DocumentBuilder documentBuilder) {
        this.documentBuilder = documentBuilder;
    }

    @Override
    public void configure() {
        SqlComponent sqlComponent = getContext().getComponent("sql", SqlComponent.class);
        getContext().addComponent("sqlComponent", sqlComponent);

        from("direct:getAllDocumentsRepository")
                .to("sqlComponent:{{sql.getAllDocuments}}")
                .marshal()
                .json(JsonLibrary.Jackson, Document.class);

        from("direct:getDocumentByIdRepository")
                .to("sql-stored:get_document(INTEGER ${headers.id})")
                .bean(documentBuilder, "getDocument")
                .marshal()
                .json(JsonLibrary.Jackson, Document.class);

        from("direct:addDocumentRepository")
                .bean(documentBuilder, "addDocument")
                .to("sqlComponent:{{sql.addDocument}}")
                .marshal()
                .json(JsonLibrary.Jackson, Document.class);
    }
}