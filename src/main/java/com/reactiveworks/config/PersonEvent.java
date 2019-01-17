package com.reactiveworks.config;

import org.springframework.context.ApplicationEvent;

import com.reactiveworks.message.Person;


public class PersonEvent  extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonEvent(Person source) {
        super(source);
    }
}
