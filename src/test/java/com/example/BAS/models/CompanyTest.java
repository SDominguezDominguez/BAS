package com.example.BAS.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void shouldGetId() {
        Company c = new Company();

        c.id = 1L;

        assertEquals(1, c.getId());
    }

    @Test
    void shouldSetAndGetName() {
        Company c = new Company();

        c.setName("Test");

        assertEquals("Test", c.getName());
    }

    @Test
    void shouldSetAndGetContactPerson() {
        Company c = new Company();

        c.setContactPerson("Contact Person");

        assertEquals("Contact Person", c.getContactPerson());
    }

    @Test
    void shouldSetAndGetEmail() {
        Company c = new Company();

        c.setEmail("testen@hotmail.com");

        assertEquals("testen@hotmail.com", c.getEmail());
    }
}