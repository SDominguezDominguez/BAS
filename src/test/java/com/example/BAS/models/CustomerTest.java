package com.example.BAS.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldKeepCustomerFields() {
        Customer c = new Customer("Test", "123", "SNS", "testen@hotmail.com");

        String name = c.getName();
        String number = c.getCustomerNumber();
        String brand = c.getBrand();
        String email = c.getEmail();

        assertEquals("Test", name);
        assertEquals("123", number);
        assertEquals("SNS", brand);
        assertEquals("testen@hotmail.com", email);
    }

    @Test
    void shouldKeepCustomerFieldsWithoutEmail() {
        Customer c = new Customer("Test", "123", "SNS");

        String name = c.getName();
        String number = c.getCustomerNumber();
        String brand = c.getBrand();

        assertEquals("Test", name);
        assertEquals("123", number);
        assertEquals("SNS", brand);
    }

    @Test
    void shouldGetId() {
        Customer c = new Customer();

        c.id = 1L;

        assertEquals(1, c.getId());
    }

    @Test
    void shouldSetName() {
        Customer c = new Customer();

        c.setName("Test");

        assertEquals("Test", c.getName());
    }

    @Test
    void shouldSetCustomerNumber() {
        Customer c = new Customer();

        c.setCustomerNumber("123");

        assertEquals("123", c.getCustomerNumber());
    }

    @Test
    void shouldSetBrand() {
        Customer c = new Customer();

        c.setBrand("SNS");

        assertEquals("SNS", c.getBrand());
    }

    @Test
    void shouldSetEmail() {
        Customer c = new Customer();

        c.setEmail("testen@hotmail.com");

        assertEquals("testen@hotmail.com", c.getEmail());
    }
}