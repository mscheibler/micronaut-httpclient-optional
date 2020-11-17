package com.kiwigrid.micronaut.optional;

import java.util.Optional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;

public interface ApiWithOptional {

	@Get("/something")
	@Produces("text/plain")
	HttpResponse<String> someRestMethod(
			@QueryValue Optional<Integer> intParam,
			@QueryValue Optional<String> stringParam);
}
