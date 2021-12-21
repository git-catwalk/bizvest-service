package com.bluntsoftware.bizvest.controller;

import com.bluntsoftware.bizvest.model.Item;
import com.bluntsoftware.bizvest.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/rest")
public class ItemController {

  private final ItemService service;

  public ItemController(ItemService service) {
    this.service = service;
  }

  @PostMapping(value="/item",produces = MediaType.APPLICATION_JSON_VALUE)
  public Item save(@RequestBody Map<String,Object> dto){
    ObjectMapper mapper = new ObjectMapper();
    return this.service.save(mapper.convertValue(dto,Item.class));
  }

  @GetMapping(value = "/item/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Item> findById(@PathVariable("id") String id ){
    return this.service.findById(String.valueOf(id));
  }

  @GetMapping(value = "/item",produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Item> findAll(){
    return this.service.findAll();
  }

  @DeleteMapping(value = "/item/{id}")
  public void deleteById(@PathVariable("id") String id ){
   this.service.deleteById(String.valueOf(id));
  }

  @ResponseBody
  @GetMapping(value = {"/item/search"}, produces = { "application/json" })
  public Page<Item> search(@RequestParam(value = "term",  defaultValue = "") String searchTerm,
                             @RequestParam(value = "page",  defaultValue = "0") Integer page,
                             @RequestParam(value = "limit", defaultValue = "50") Integer limit){
    return this.service.search(searchTerm,PageRequest.of(page,limit));
  }
}
