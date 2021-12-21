package com.bluntsoftware.bizvest.service;

import com.bluntsoftware.bizvest.model.Item;
import com.bluntsoftware.bizvest.repository.ItemRepo;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Service
public class ItemService{

  private final ItemRepo repo;

  public ItemService(ItemRepo repo) {
    this.repo = repo;
  }

  public  Item save(Item item) {
    return repo.save(item);
  }

  public void deleteById(String id) {
      repo.deleteById(id);
  }

  public Optional<Item> findById(String id) {
    return repo.findById(id);
  }

  public Iterable<Item> findAll() {
    return repo.findAll();
  }

  public Page<Item> search(String term,Pageable pageable) {
    log.info("create a filter in repo for search term {}",term);
    return repo.findAll(pageable);
  }
}
