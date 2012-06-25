package com.github.pires.example.module;

import com.github.pires.example.OtherExample;
import com.google.inject.AbstractModule;

public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OtherExample.class);
	}

}
