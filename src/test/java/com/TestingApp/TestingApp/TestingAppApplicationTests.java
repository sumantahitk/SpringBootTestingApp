package com.TestingApp.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
//@SpringBootTest
class    TestingAppApplicationTests {

    @BeforeEach
    void setUp(){
        log.info("Starting the method, setting up config");
    }
    @AfterEach
    void tearDown(){
        log.info("tearing down the method");
    }
    @BeforeAll
    static void setUpOnce(){
        log.info("Setup once...");
    }

    @AfterAll
    static void tearDownOnce(){
        log.info("Tearing down all...");
    }

	@Test
//    @Disabled
	void testNumberOne() {
        log.info("test one is run");
	}

    @Test
    void test1(){
       int a=2,b=4;
       int res=addTwoNumber(a,b);

//       Assertions.assertEquals(6,res);
        Assertions.assertThat(res)
                .isEqualTo(6)
                .isCloseTo(7, Offset.offset(1));

        Assertions.assertThat("Apple")
                .isEqualTo("Apple")
                .startsWith("Ap")
                .endsWith("le")
                .hasSize(5);
    }

    @Test
    void test2() {
        System.out.println(addTwoNumber(-3 ,-1));
    }

    @Test
//    @DisplayName("displayTestNameTwo")
    void testTwoNumber(){
        log.info("test two is run");
    }


    int addTwoNumber(int a,int b)
    {
        return a+b;
    }

    @Test
    void testDivideTwoNumber_whenDenominatorIsZero(){
        int a=4,b=0;
//        double res=divideTwoNumbers(a,b);
        Assertions.assertThatThrownBy(()->divideTwoNumbers(a,b))
                        .isInstanceOf(Exception.class)
                .hasMessage("Tried to divide by zero");
//        System.out.println(res);

    }
    double divideTwoNumbers(int a,int b){
        try{
            return  a/b;
        }catch (ArithmeticException e){
            log.error("Arithmatic Exception Occured: "+e.getLocalizedMessage());
            throw new ArithmeticException("Tried to divide by zero");
        }
    }

}
