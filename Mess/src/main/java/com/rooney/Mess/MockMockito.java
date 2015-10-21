package com.rooney.Mess;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableMap;

@RunWith(MockitoJUnitRunner.class)
public class MockMockito {
    Inner inner = new Inner();
    @Spy Inner spyInner = inner;
    boolean otherBehaviour;
    
    @Mock MyPojo mockInterface;

    
//    public static class ReturnFromMap implements Answer<T> {
//        private Map<String, String> cannedData;
//        
//        public ReturnFromMap(Map<String, T> cannedData) {
//            this.cannedData = cannedData;
//        }
//
//        @Override
//        public <T> answer(InvocationOnMock invocation) throws Throwable {
//            return cannedData.get(invocation.getArguments()[0]);
//        }
//        
//        public static Answer<String> returnFromMap(Map<String, T> cannedData) {
//            return new ReturnFromMap(cannedData);
//        }
//    }
//    
    @Test
    public void testReturnFromMap() throws Exception {
        Map<String, String> cannedData = ImmutableMap.of("foo1", "bar1", "foo2", "bar2");
        
//        when(mockInterface.useParam(eq("foo1"))).thenReturn("bar1");
        
//        when(mockInterface.useParam(contains(cannedData))).thenReturn("bar1");
        
        when(mockInterface.useParam(anyString())).then(returnFromMap(cannedData));
        
        assertThat(mockInterface.useParam("foo1"), is("bar1"));
        assertThat(mockInterface.useParam("foo2"), is("bar2"));
    }

    
    private Answer<String> returnFromMap(final Map<String, String> cannedData) {
        return new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return cannedData.get(invocation.getArguments()[0]);
            }
        };
    }



    @Test
    public void messWithMockInterface() {
//        Builder<MyPojo> builder = new Builder<MyPojo>();
        
        //todo add boolean
//        builder.addfilter(
//            eq(mockInterface.getFoo(), "mocked"),
//            lt(mockInterface.getBar(), 100),
//            gt(mockInterface.getDonkey(), 100)
//            )
//            .sort(mockInterface.getBar(), DESC)
//            .start(0)
//            .limit(20)
//            .page(2);
            
            
        when(mockInterface.getFoo()).thenReturn("Mocked");

    }
    
    @Test 
    public void testVoidDoNothing() {
        doNothing().when(spyInner).callFoo();
        
        spyInner.callFoo();
        assertFalse(inner.fooCalled);

        inner.callFoo();
        assertTrue(inner.fooCalled);
    }

    @Test 
    public void testVoidWithOtherBehaviour() {
        doAnswer(new Answer<Void>() { //type can be 'Object' also
            public Void answer(InvocationOnMock invocation) throws Throwable {
                otherBehaviour = true; 
                return null;
            }
        }).when(spyInner).callFoo();
        
        inner.callFoo();
        assertFalse(otherBehaviour);
        
        spyInner.callFoo();
        assertTrue(otherBehaviour); //has been affected
        
    }
    
    class Inner {
        boolean fooCalled;
        void callFoo() {
            this.fooCalled = true;
        }
    }
    
    public static interface MyPojo{
        String getFoo();
        Long getBar();
        String getDonkey();
        String useParam(String param);
    }
}
