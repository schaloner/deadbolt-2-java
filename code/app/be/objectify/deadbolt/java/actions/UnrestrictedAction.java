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
package be.objectify.deadbolt.java.actions;

import be.objectify.deadbolt.java.cache.BeforeAuthCheckCache;
import be.objectify.deadbolt.java.cache.HandlerCache;
import com.typesafe.config.Config;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Implements the {@link Unrestricted} functionality, i.e. there are no restrictions on the resource.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class UnrestrictedAction extends AbstractDeadboltAction<Unrestricted>
{
    @Inject
    public UnrestrictedAction(final HandlerCache handlerCache,
                              final BeforeAuthCheckCache beforeAuthCheckCache,
                              final Config config)
    {
        super(handlerCache,
              beforeAuthCheckCache,
              config);
    }

    @Override
    public CompletionStage<Result> execute(final Http.RequestHeader request) throws Exception
    {
        return authorizeAndExecute(request);
    }

    @Override
    protected boolean deferred() {
        return configuration.deferred();
    }

    @Override
    public Optional<String> getContent()
    {
        return Optional.ofNullable(configuration.content());
    }

    @Override
    public String getHandlerKey()
    {
        return configuration.handlerKey();
    }

}
