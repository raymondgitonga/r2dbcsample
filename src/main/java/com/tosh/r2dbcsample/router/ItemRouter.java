package com.tosh.r2dbcsample.router;

import com.tosh.r2dbcsample.handler.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ItemRouter {
    @Bean
    public RouterFunction<ServerResponse> itemsRoute(ItemHandler itemHandler) {
        return RouterFunctions
                .route(GET("/v1/getAllItems").and(accept(MediaType.APPLICATION_JSON)),
                        itemHandler::getAllItems);

    }
}
