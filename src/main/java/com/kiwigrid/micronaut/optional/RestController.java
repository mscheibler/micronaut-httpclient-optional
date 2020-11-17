package com.kiwigrid.micronaut.optional;

import java.util.Optional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

@Controller("/")
public class RestController implements ApiWithOptional {

	@Override
	public HttpResponse<String> someRestMethod(Optional<Integer> intParam, Optional<String> stringParam) {
		String response = "got value for intParam: '" + intParam + "' and stringParam: '" + stringParam + '\'';
		return HttpResponse.ok(response);
	}
}
