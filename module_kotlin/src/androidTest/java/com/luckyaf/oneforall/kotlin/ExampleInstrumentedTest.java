package com.luckyaf.oneforall.kotlin;

import android.support.test.runner.AndroidJUnit4;

import com.luckyaf.oneforall.kotlin.test.LeanA;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    LeanA learnA = new LeanA();
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.luckyaf.oneforall.kotlin.test", appContext.getPackageName());
//    }

    @Test
    public void learnA(){
        assertEquals(1, learnA.testHello());
    }
    @Test
    public void learnSum(){
        assertEquals(3, learnA.sum(1,2));
    }

    @Test
    public void learnVar(){
        learnA.changeB();
        assertEquals(3, learnA.getB());
    }

    @Test
    public void learnString(){
        learnA.changeB();
        learnA.printS();
        System.out.print(learnA.getS2());

    }
}
