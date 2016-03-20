package be.objectify.deadbolt.java.composite;

import be.objectify.deadbolt.java.ConstraintLogic;
import be.objectify.deadbolt.java.DeadboltAnalyzer;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DefaultDeadboltExecutionContextProvider;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.cache.DefaultPatternCache;
import be.objectify.deadbolt.java.cache.SubjectCache;
import be.objectify.deadbolt.java.testsupport.FakeCache;
import be.objectify.deadbolt.java.testsupport.TestRole;
import be.objectify.deadbolt.java.testsupport.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import play.mvc.Http;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ConstraintBuildersTest extends AbstractCompositeTest
{
    @Test
    public void testAllOf() throws Exception
    {
        final ExecutionContextProvider ecProvider = Mockito.mock(ExecutionContextProvider.class);
        Mockito.when(ecProvider.get()).thenReturn(new DefaultDeadboltExecutionContextProvider());
        final SubjectCache subjectCache = Mockito.mock(SubjectCache.class);
        Mockito.when(subjectCache.apply(Mockito.any(DeadboltHandler.class),
                                        Mockito.any(Http.Context.class)))
               .thenReturn(CompletableFuture.completedFuture(Optional.of(new TestSubject.Builder().role(new TestRole("foo")).build())));
        final ConstraintLogic logic = new ConstraintLogic(new DeadboltAnalyzer(),
                                                          subjectCache,
                                                          new DefaultPatternCache(new FakeCache()),
                                                          ecProvider);
        final ConstraintBuilders builders = new ConstraintBuilders(logic);

        final String[] array = builders.allOf("foo",
                                              "bar");
        Assert.assertNotNull(array);
        Assert.assertEquals(2,
                            array.length);
        Assert.assertEquals("foo",
                            array[0]);
        Assert.assertEquals("bar",
                            array[1]);
    }

    @Test
    public void testAnyOf() throws Exception
    {
        final ExecutionContextProvider ecProvider = Mockito.mock(ExecutionContextProvider.class);
        Mockito.when(ecProvider.get()).thenReturn(new DefaultDeadboltExecutionContextProvider());
        final SubjectCache subjectCache = Mockito.mock(SubjectCache.class);
        Mockito.when(subjectCache.apply(Mockito.any(DeadboltHandler.class),
                                        Mockito.any(Http.Context.class)))
               .thenReturn(CompletableFuture.completedFuture(Optional.of(new TestSubject.Builder().role(new TestRole("foo")).build())));
        final ConstraintLogic logic = new ConstraintLogic(new DeadboltAnalyzer(),
                                                          subjectCache,
                                                          new DefaultPatternCache(new FakeCache()),
                                                          ecProvider);
        final ConstraintBuilders builders = new ConstraintBuilders(logic);

        final List<String[]> list = builders.anyOf(new String[]{"foo"},
                                                   new String[]{"bar"});
        Assert.assertNotNull(list);
        Assert.assertEquals(2,
                            list.size());
        Assert.assertArrayEquals(new String[]{"foo"},
                                 list.get(0));
        Assert.assertArrayEquals(new String[]{"bar"},
                                 list.get(1));
    }
}