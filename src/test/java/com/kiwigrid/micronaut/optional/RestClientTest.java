package com.kiwigrid.micronaut.optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestClientTest {

	@Inject
	ClientWithoutOptional clientWithoutOptional;
	@Inject
	ClientWithOptional clientWithOptional;

	@Test
	@Order(1)
	@DisplayName("working test without using Optional and null values")
	void testClientWithNull() {
		HttpResponse<String> response = clientWithoutOptional.getSomething(null, null);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals("got value for intParam: 'Optional.empty' and stringParam: 'Optional.empty'", response.body());
	}

	@Test
	@Order(2)
	@DisplayName("working test without using Optional and non-null values")
	void testClientWithNonnull() {
		HttpResponse<String> response = clientWithoutOptional.getSomething(2, "foo");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals("got value for intParam: 'Optional[2]' and stringParam: 'Optional[foo]'", response.body());
	}

	@Test
	@Order(3)
	@DisplayName("failing test with Optional.empty as values")
	void testClientWithOptionalEmpty() {
		HttpResponse<String> response = clientWithOptional.getSomething(Optional.empty(), Optional.empty());
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals("got value for intParam: 'Optional.empty' and stringParam: 'Optional.empty'", response.body());
	}

	@Test
	@Order(4)
	@DisplayName("failing test with present Optional values")
	void testClientWithOptional() {
		HttpResponse<String> response = clientWithOptional.getSomething(Optional.of(4), Optional.of("bar"));
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals("got value for intParam: 'Optional[4]' and stringParam: 'Optional[bar]'", response.body());
	}

	@Client("${test.clientId:/}")
	public interface ClientWithoutOptional {
		@Get("/something")
		@Consumes("text/plain")
		HttpResponse<String> getSomething(
				@Nullable @QueryValue Integer intParam,
				@Nullable @QueryValue String stringParam);
	}

	@Client("${test.clientId:/}")
	public interface ClientWithOptional {
		@Get("/something")
		@Consumes("text/plain")
		HttpResponse<String> getSomething(
				@Nonnull @QueryValue Optional<Integer> intParam,
				@Nonnull @QueryValue Optional<String> stringParam);
	}
}
