package com.github.pires.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.pires.example.dao.PersonDao;
import com.github.pires.example.model.Person;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class OtherExample {

	private static final Logger logger = Logger.getLogger(OtherExample.class
	    .getName());

	private final Injector injector;

	@Inject
	public OtherExample(Injector injector) {
		this.injector = injector;
	}

	public void run() {
		// inject stuff
		PersonDao personDao = injector.getInstance(PersonDao.class);

		// persist another person
		Person p = new Person();
		p.setName("Person X");
		personDao.create(p);

		logger.log(Level.INFO, "Querying DB for person with name Person 1..");
		// retrieve one person from the persisted people
		Person retrieved = personDao.getByName("Person 1");

		logger.log(Level.INFO, "Retrieved " + retrieved.getName());
	}

}
