package com.tosh.r2dbcsample.handler;

import com.tosh.r2dbcsample.data.Item;
import com.tosh.r2dbcsample.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ItemHandler {

    @Autowired
    ItemRepository itemRepository;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getAllItems(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemRepository.findAll(), Item.class);
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        Mono<Item> itemMono = itemRepository.findById(Integer.valueOf(id));

        return itemMono.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(item))
                        .switchIfEmpty(notFound)
        );
    }

    //createItem
    public Mono<ServerResponse> createItem(ServerRequest serverRequest) {
        Mono<Item> newItem = serverRequest.bodyToMono(Item.class);

        return newItem.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(itemRepository.save(item), Item.class)
        );
    }

    //deleteById
    public Mono<ServerResponse> deleteItem(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        Mono<Void> deletedItem = itemRepository.deleteById(Integer.valueOf(id));

        return deletedItem.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(deletedItem, void.class)
        );
    }

    //updateItem
    public Mono<ServerResponse> updateItem(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Item> itemToUpdate = serverRequest.bodyToMono(Item.class)
                .flatMap(newItem -> {
                    Mono<Item> itemMono = itemRepository.findById(Integer.valueOf(id))
                            .flatMap(oldItem -> {
                                oldItem.setDescription(newItem.getDescription());
                                oldItem.setPrice(newItem.getPrice());
                                return itemRepository.save(oldItem);
                            });
                    return itemMono;
                });

        return itemToUpdate.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(item))
                        .switchIfEmpty(notFound)
        );
    }

    public Mono<ServerResponse> findSingleItem(ServerRequest serverRequest) {
        String price = serverRequest.pathVariable("price");

        Mono<Item> itemByPrice = itemRepository.findSingleItem(Double.parseDouble(price));

        return itemByPrice.flatMap(item ->
                ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(item))
                );
    }
}
