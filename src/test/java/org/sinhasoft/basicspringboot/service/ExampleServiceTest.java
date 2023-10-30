package org.sinhasoft.basicspringboot.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExampleServiceTest {
    private int sayac = 0;

    @BeforeAll
    public void beforeAll() {
        System.out.println("----------- before all -------------");
    }

    @Test
    public void test1() {
        System.out.println(++sayac);
    }

    @Test
    public void test2() {
        System.out.println(++sayac);
    }

    @Test
    public void test3() {
        System.out.println(++sayac);
    }

    @Test
    public void test4() {
        System.out.println(++sayac);
    }

    @Test
    public void test5() {
        System.out.println(++sayac);
    }
}
