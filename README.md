# micronaut-httpclient-optional
Just a little showcase about the use of java.util.Optional in the Micronaut declarative HTTP client.

The `RestClientTest` shows how the declarative HTTP client handles optional parameters.
1. No `Optional` just `null` values. The client request is as expected. The parameters are not part of the request:
   `GET http://localhost:46893/something`
2. The parameter values are supplied as is. The client request is as expected. The parameters are added to the request:
   `GET /something?stringParam=foo&intParam=2`
3. Absent parameter values are represented via `java.util.Optional.EMPTY`. The client request contains a toString-representation
   of the EMPTY Optional instead of ommitting the parameters:
   `GET /something?stringParam=Optional.empty&intParam=Optional.empty`
4. Parameter values are wrapped as `java.util.Optional`. The client request does contain a toString-representation of the provided
   Optionals instead of the unwrapped parameter values:
   `GET http://localhost:46893/something?stringParam=Optional%5Bbar%5D&intParam=Optional%5B4%5D`

On the server side the wrapping of absent values with Optional works as expected. Absent parameters become `Optional.EMPTY`
and present parameters are wrapped into Optional.
