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

import be.objectify.deadbolt.java.cache.CompositeCache;
import be.objectify.deadbolt.java.cache.DefaultCompositeCache;
import be.objectify.deadbolt.java.cache.DefaultPatternCache;
import be.objectify.deadbolt.java.cache.DefaultSubjectCache;
import be.objectify.deadbolt.java.cache.DefaultBeforeAuthCheckCache;
import be.objectify.deadbolt.java.cache.PatternCache;
import be.objectify.deadbolt.java.cache.SubjectCache;
import be.objectify.deadbolt.java.cache.BeforeAuthCheckCache;
import be.objectify.deadbolt.java.composite.ConstraintBuilders;
import be.objectify.deadbolt.java.filters.FilterConstraints;
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
public class DeadboltModule extends Module
{
    @Override
    public List<Binding<?>> bindings(final Environment environment,
                                     final Config config)
    {
        return Arrays.asList(subjectCache(),
                   beforeAuthCheckCache(),
                   patternCache(),
                   analyzer(),
                   viewSupport(),
                   templateFailureListenerProvider(),
                   constraintLogic(),
                   compositeCache(),
                   constraintBuilders(),
                   filterConstraints());
    }

    /**
     * Create a binding for {@link TemplateFailureListenerProvider}.
     *
     * @return the binding
     */
    public Binding<TemplateFailureListenerProvider> templateFailureListenerProvider()
    {
        return bindClass(TemplateFailureListenerProvider.class).toSelf().in(Singleton.class);
    }

    /**
     * Create a binding for {@link ViewSupport}.
     *
     * @return the binding
     */
    public Binding<ViewSupport> viewSupport()
    {
        return bindClass(ViewSupport.class).toSelf().in(Singleton.class);
    }

    /**
     * Create a binding for {@link DeadboltAnalyzer}.
     *
     * @return the binding
     */
    public Binding<DeadboltAnalyzer> analyzer()
    {
        return bindClass(DeadboltAnalyzer.class).toSelf().in(Singleton.class);
    }

    /**
     * Create a binding for {@link ConstraintBuilders}.
     *
     * @return the binding
     */
    public Binding<ConstraintBuilders> constraintBuilders()
    {
        return bindClass(ConstraintBuilders.class).toSelf().in(Singleton.class);
    }

    /**
     * Create a binding for {@link PatternCache}.
     *
     * @return the binding
     */
    public Binding<PatternCache> patternCache()
    {
        return bindClass(PatternCache.class).to(DefaultPatternCache.class).in(Singleton.class);
    }

    /**
     * Create a binding for {@link CompositeCache}.
     *
     * @return the binding
     */
    public Binding<CompositeCache> compositeCache()
    {
        return bindClass(CompositeCache.class).to(DefaultCompositeCache.class).in(Singleton.class);
    }

    /**
     * Create a binding for {@link SubjectCache}.
     *
     * @return the binding
     */
    public Binding<SubjectCache> subjectCache()
    {
        return bindClass(SubjectCache.class).to(DefaultSubjectCache.class).in(Singleton.class);
    }

    /**
     * Create a binding for {@link BeforeAuthCheckCache}.
     *
     * @return the binding
     */
    public Binding<BeforeAuthCheckCache> beforeAuthCheckCache()
    {
        return bindClass(BeforeAuthCheckCache.class).to(DefaultBeforeAuthCheckCache.class).in(Singleton.class);
    }

    /**
     * Create a binding for {@link ConstraintLogic}.
     *
     * @return the binding
     */
    public Binding<ConstraintLogic> constraintLogic()
    {
        return bindClass(ConstraintLogic.class).toSelf().in(Singleton.class);
    }

    /**
     * Create a binding for {@link FilterConstraints}.
     *
     * @return the binding
     */
    public Binding<FilterConstraints> filterConstraints()
    {
        return bindClass(FilterConstraints.class).toSelf().in(Singleton.class);
    }
}
