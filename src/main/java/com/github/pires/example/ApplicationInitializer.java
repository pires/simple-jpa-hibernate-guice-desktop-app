package com.github.pires.example;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 * Initialize services.
 */
public class ApplicationInitializer {

	@Inject
	ApplicationInitializer(PersistService persistenceService) {
		// start JPA
		persistenceService.start();
	}

}