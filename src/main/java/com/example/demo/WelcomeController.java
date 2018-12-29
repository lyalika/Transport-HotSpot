package com.example.demo;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
class WelcomeController {

  @RequestMapping("/welcome") 
  String welcome(@RequestParam(value = "name") Optional<String> name) {

	  return "Welcome ".concat(name.orElse("World")).concat("!");

 
  }
}