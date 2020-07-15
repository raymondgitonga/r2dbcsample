package com.tosh.r2dbcsample.repository;

import com.tosh.r2dbcsample.data.Item;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ItemRepository extends ReactiveCrudRepository<Item, Integer> {
    @Query("SELECT * FROM item WHERE price =:price")
    Mono<Item> findSingleItem(double price);
}
