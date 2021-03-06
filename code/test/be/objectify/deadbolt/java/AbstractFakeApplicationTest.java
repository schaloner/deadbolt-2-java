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
package be.objectify.deadbolt.java;

import be.objectify.deadbolt.java.cache.DefaultPatternCache;
import be.objectify.deadbolt.java.cache.HandlerCache;
import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Subject;
import com.typesafe.config.ConfigFactory;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.F;
import play.mvc.Http;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import static play.inject.Bindings.bind;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public abstract class AbstractFakeApplicationTest extends WithApplication
{

    @Override
    protected Application provideApplication()
    {
        return new GuiceApplicationBuilder().bindings(new DeadboltModule())
                                            .bindings(bind(HandlerCache.class).toInstance(handlers()))
                                            .in(Mode.TEST)
                                            .build();
    }

    public DeadboltHandler init(final Supplier<Subject> getSubject)
    {
        final DeadboltHandler handler = handler(getSubject);

        Helpers.start(this.app);

        return handler;
    }

    protected HandlerCache handlers(DeadboltHandler handler) {
        return handlers();
    }

    protected abstract HandlerCache handlers();

    protected DeadboltHandler handler(final Supplier<Subject> getSubject)
    {
        return new NoPreAuthDeadboltHandler()
        {
            @Override
            public CompletionStage<Optional<? extends Subject>> getSubject(final Http.RequestHeader requestHeader)
            {
                return CompletableFuture.supplyAsync(() -> Optional.ofNullable(getSubject.get()));
            }
        };
    }

    protected DeadboltHandler handler(final Supplier<Subject> getSubject,
                                      final List<? extends Permission> associatedPermissions)
    {
        return new NoPreAuthDeadboltHandler()
        {
            @Override
            public CompletionStage<Optional<? extends Subject>> getSubject(final Http.RequestHeader requestHeader)
            {
                return CompletableFuture.supplyAsync(() -> Optional.ofNullable(getSubject.get()));
            }

            @Override
            public CompletionStage<List<? extends Permission>> getPermissionsForRole(final String roleName)
            {
                return CompletableFuture.completedFuture(associatedPermissions);
            }
        };
    }

    protected DeadboltHandler withDrh(final Supplier<DynamicResourceHandler> drh)
    {
        return new NoPreAuthDeadboltHandler()
        {
            @Override
            public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.RequestHeader requestHeader)
            {
                return CompletableFuture.supplyAsync(() -> Optional.ofNullable(drh.get()));
            }
        };
    }

    public ViewSupport viewSupport()
    {
        final ConstraintLogic constraintLogic = new ConstraintLogic(new DeadboltAnalyzer(),
                                                                    (handler, rh) -> handler.getSubject(rh).thenApply(maybeSubject -> F.Tuple(maybeSubject, rh)),
                                                                    new DefaultPatternCache());

        final Application app = provideApplication();
        return new ViewSupport(app.config(),
                               handlers(),
                               new TemplateFailureListenerProvider(app.injector()),
                               constraintLogic);
    }
}
