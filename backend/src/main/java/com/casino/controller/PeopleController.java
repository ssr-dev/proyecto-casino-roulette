package com.example.rule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rulete/co")
public class PeopleController {

  //   @Autowired
  // ElementsManagerService elementsManagerService;

  // @GetMapping("/all")
  // public ArrayList<ElementModel> getAll() {
  //   return elementsManagerService.getElements();
  // }

  // @PostMapping("/add")
  // public ElementModel addPerson(@RequestBody ElementModel element) {
  //   elementsManagerService.newAddElement(element);
  //   return element;
  // }

  // @GetMapping("/id/{id}")
  // public ElementDto getById(@PathVariable int id) {
  //   return ElementDto.toPersonDto(elementsManagerService.getElement(id));
  // }

  // @DeleteMapping("/delete/{id}")
  // public ElementModel deletePerson(@PathVariable int id) {
  //   ElementModel personModel = elementsManagerService.deletePerson(id);
  //   return personModel;
  // }
 
  // @PostMapping("/modify")
  // public ElementDto modifyElement (@RequestBody ElementModel element){
  //   ElementModel elementModel = elementsManagerService.modifyElement(element);
  //   return ElementDto.toPersonDto(elementModel);
  // }

  // @GetMapping("/average")
  // public double getAverage() {
  //   return elementsManagerService.calculateAveragePrices();
  // }

  // @GetMapping("/amountUnid")
  // public ArrayList<Integer> getAmountUnid() {
  //   return elementsManagerService.getAmountPerUnid();
  // }

}
