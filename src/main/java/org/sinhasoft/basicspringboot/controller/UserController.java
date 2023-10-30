package org.sinhasoft.basicspringboot.controller;

import org.sinhasoft.basicspringboot.dto.UserDTO;
import org.sinhasoft.basicspringboot.dto.UserSearchDTO;
import org.sinhasoft.basicspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService service;

  @GetMapping
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
    return new ResponseEntity<>(service.create(userDTO), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
//    return new ResponseEntity<>(service.update(userDTO), HttpStatus.OK);
    UserDTO result = service.update(userDTO);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<?> search(UserSearchDTO searchDTO) {
    return new ResponseEntity<>(service.search(searchDTO), HttpStatus.OK);
  }

}
