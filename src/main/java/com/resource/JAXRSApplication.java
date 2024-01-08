package com.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class JAXRSApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
//		classes.add(RecipeResource.class);
		classes.add(JacksonJsonProvider.class);
		classes.add(Routes.class);
		return classes;
	}
}
