package com.bluntsoftware.bizvest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bluntsoftware.bizvest.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends MongoRepository<Item, String> {
}