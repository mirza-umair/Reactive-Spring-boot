package com.reactiveworks.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactiveworks.message.Person;
import com.reactiveworks.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
class PersonController {

	private final PersonService personService;

	PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public Flux<Person> getAll() {
		return personService.all();
	}

	@GetMapping("/{id}")
	public Mono<Person> getById(@PathVariable("id") long id) {
		return personService.findOne(id);
	}

	@PostMapping
	public Mono<Person> create(@RequestBody Person person) {
		return personService.create(person);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteById(@PathVariable long id) {
		return personService.delete(id);
	}

}
