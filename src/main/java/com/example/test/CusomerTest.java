package com.example.test;

import com.example.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fyj
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CusomerTest {

    @Autowired
    private Customer customer;

    @Test
    public void getValue(){
        System.out.println(customer.getValue());
    }
}
