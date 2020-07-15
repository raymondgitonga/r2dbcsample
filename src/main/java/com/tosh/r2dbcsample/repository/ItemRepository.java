package com.tosh.r2dbcsample.repository;

import com.tosh.r2dbcsample.data.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, Integer> {
}
