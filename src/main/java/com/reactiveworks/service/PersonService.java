package com.reactiveworks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.reactiveworks.config.PersonEvent;
import com.reactiveworks.message.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service class to do operations on Person ,Its provides the method to do curd
 * operation on Person
 * 
 * @author Umair Ahmed
 *
 */
@Service
public class PersonService {

	private final List<Person> persons = new ArrayList<>();

	private final ApplicationEventPublisher publisher;

	PersonService(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public Mono<Person> create(Person person) {
		persons.add(person);
		return Mono.just(person).doOnSuccess(p -> this.publisher.publishEvent(new PersonEvent(p)));

	}

	public Flux<Person> all() {
		return Flux.fromIterable(persons);
	}

	public Mono<Person> findOne(long id) {
		return Mono.just(persons.stream().filter(p -> p.getId() == id).findFirst().get());
	}

	public Mono<Void> delete(long id) {
		persons.remove(persons.stream().filter(p -> p.getId() == id).findAny().get());
		return Mono.empty();
	}

}
