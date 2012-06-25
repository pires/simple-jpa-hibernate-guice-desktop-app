package com.github.pires.example.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.pires.example.model.Person;
import com.github.pires.example.model.Person_;

public class PersonDao extends AbstractDao<Person> {

	public PersonDao() {
		super(Person.class);
	}

	public Person getByName(String name) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		Root<Person> person = cq.from(Person.class);

		Predicate cond1 = cb.equal(person.get(Person_.name), name);
		cq.where(cond1);

		return getEntityManager().createQuery(cq).getSingleResult();
	}

}