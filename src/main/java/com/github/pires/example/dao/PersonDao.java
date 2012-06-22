package com.github.pires.example.dao;

import javax.persistence.EntityManager;

import com.github.pires.example.model.Person;
import com.google.inject.Inject;

public class PersonDao {

	protected EntityManager em;

	@Inject
	public PersonDao(EntityManager entityManager) {
		this.em = entityManager;
	}

	public void create(Person person) {
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
	}

	public Person getByName(String name) {
		return (Person) em
		    .createQuery("SELECT person FROM Person person WHERE person.name=:name")
		    .setParameter("name", name).getSingleResult();
	}

}