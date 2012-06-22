package com.github.pires.example.test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.github.pires.example.dao.PersonDao;
import com.github.pires.example.model.Person;
import com.github.pires.example.module.PersistenceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PersistenceTest {
	@Test
	public void testDb() throws SQLException {
		// inject stuff
		Injector injector = Guice.createInjector(new PersistenceModule());
		PersonDao personDao = injector.getInstance(PersonDao.class);

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

		// retrieve one person from the persisted people
		Person retrieved = personDao.getByName("Person 2");

		// validate results
		Assert.assertEquals(p2.getId(), retrieved.getId());
		Assert.assertEquals(p2.getName(), retrieved.getName());
	}
}
