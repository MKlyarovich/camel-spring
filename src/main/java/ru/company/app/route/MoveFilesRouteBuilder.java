package ru.company.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.company.app.processor.MoveFilesProcessor;

@Component
public class MoveFilesRouteBuilder extends RouteBuilder {
    private static final String DESTINATION_FOLDER = "destination-folder";

    private final MoveFilesProcessor moveFilesProcessor;

    @Autowired
    public MoveFilesRouteBuilder(MoveFilesProcessor moveFilesProcessor) {
        this.moveFilesProcessor = moveFilesProcessor;
    }

    @Override
    public void configure() {
        from("direct:saveDocumentToFileSystem")
                .convertBodyTo(String.class)
                .process(moveFilesProcessor)
                .to(String.format("file://%s", DESTINATION_FOLDER));
    }
}