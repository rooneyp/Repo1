package com.rooney.Mess;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class MockMockito {
    Inner inner = new Inner();
    @Spy Inner spyInner = inner;
    boolean otherBehaviour;
    
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
}
