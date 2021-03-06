/*
 * Copyright 2013 Steve Chaloner
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
package be.objectify.deadbolt.java.test.modules;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.TemplateFailureListener;
import be.objectify.deadbolt.java.cache.HandlerCache;
import be.objectify.deadbolt.java.test.dao.DefaultUserDao;
import be.objectify.deadbolt.java.test.dao.UserDao;
import be.objectify.deadbolt.java.test.security.CompositeConstraints;
import be.objectify.deadbolt.java.test.security.HandlerQualifiers;
import be.objectify.deadbolt.java.test.security.MyCustomTemplateFailureListener;
import be.objectify.deadbolt.java.test.security.MyDeadboltHandler;
import be.objectify.deadbolt.java.test.security.MyHandlerCache;
import be.objectify.deadbolt.java.test.security.SomeOtherDeadboltHandler;
import com.typesafe.config.Config;
import play.Environment;
import play.inject.Binding;
import play.inject.Module;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class CustomDeadboltHook extends Module
{
    @Override
    public List<Binding<?>> bindings(final Environment environment,
                                     final Config config)
    {
        return Arrays.asList(bindClass(TemplateFailureListener.class).to(MyCustomTemplateFailureListener.class).in(Singleton.class),
                             bindClass(UserDao.class).to(DefaultUserDao.class).in(Singleton.class),
                             bindClass(DeadboltHandler.class).qualifiedWith(HandlerQualifiers.MainHandler.class).to(MyDeadboltHandler.class).in(Singleton.class),
                             bindClass(DeadboltHandler.class).qualifiedWith(HandlerQualifiers.SomeOtherHandler.class).to(SomeOtherDeadboltHandler.class).in(Singleton.class),
                             bindClass(HandlerCache.class).to(MyHandlerCache.class).in(Singleton.class),
                             bindClass(CompositeConstraints.class).toSelf().eagerly());
    }
}
