/*
 * Copyright 2010-2016 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.objectify.deadbolt.java.filters;

import be.objectify.deadbolt.java.DeadboltHandler;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import play.mvc.Results;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static be.objectify.deadbolt.java.filters.Methods.GET;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class AuthorizedRouteTest
{

    @Test
    public void constructorWithDefaultHandler()
    {
        final FilterFunction constraint = (requestHeader, handler, onSuccess) -> CompletableFuture.completedFuture(Results.ok());
        final AuthorizedRoute route = new AuthorizedRoute(GET,
                                                          "/foo",
                                                          constraint);

        Assert.assertEquals(GET,
                            route.method());
        Assert.assertEquals("/foo",
                            route.path());
        Assert.assertEquals(constraint,
                            route.constraint());

        Assert.assertFalse(route.handler().isPresent());
    }

    @Test
    public void constructorWithSpecificHandler()
    {
        final FilterFunction constraint = (requestHeader, handler, onSuccess) -> CompletableFuture.completedFuture(Results.ok());
        final DeadboltHandler handler = Mockito.mock(DeadboltHandler.class);
        final AuthorizedRoute route = new AuthorizedRoute(GET,
                                                          "/foo",
                                                          constraint,
                                                          Optional.of(handler));

        Assert.assertEquals(GET,
                            route.method());
        Assert.assertEquals("/foo",
                            route.path());
        Assert.assertEquals(constraint,
                            route.constraint());
        Assert.assertEquals(route.handler().orElse(null),
                            handler);
    }
}