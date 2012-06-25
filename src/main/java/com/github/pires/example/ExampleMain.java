package com.github.pires.example;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.pires.example.dao.PersonDao;
import com.github.pires.example.model.Person;
import com.github.pires.example.module.PersistenceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;

public class ExampleMain {

	private static final Logger logger = Logger.getLogger(ExampleMain.class
	    .getName());

	public static final String PU_NAME = "examplePU";

	private static PersonDao personDao;

	public static final void main(String... args) {
		// inject stuff
		Injector injector = Guice.createInjector(new PersistenceModule(),
		    new JpaPersistModule(PU_NAME));
		injector.getInstance(ApplicationInitializer.class);
		personDao = injector.getInstance(PersonDao.class);

		try {
			logger.log(Level.INFO, "Populating DB..");
			populateDb();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an error while populating db.. ", e);
		}

		logger.log(Level.INFO, "Querying DB for person with name Person 2..");
		// retrieve one person from the persisted people
		Person retrieved = personDao.getByName("Person 2");
		logger.log(Level.INFO, "Retrieved " + retrieved.getName());

		injector.getInstance(OtherExample.class).run();
	}

	private static void populateDb() throws SQLException {
		// persist people
		Person p1 = new Person();
		p1.setName("Person 1");
		personDao.create(p1);

		Person p2 = new Person();
		p2.setName("Person 2");
		personDao.create(p2);

		Person p3 = new Person();
		p3.setName("Person 3");
		personDao.create(p3);
	}

}