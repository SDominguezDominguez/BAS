package com.example.BAS.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvisorTest {


    @Test
    void shouldGetId() {
        Advisor a = new Advisor();

        a.id = 1L;

        assertEquals(1, a.getId());
    }

    @Test
    void shouldSetOffice() {
        Advisor a = new Advisor();

        a.setOffice(123);

        assertEquals(123, a.getOffice());
    }

    @Test
    void shouldSetName() {
        Advisor a = new Advisor();

        a.setName("Test");

        assertEquals("Test", a.getName());
    }

    @Test
    void shouldSetEmail() {
        Advisor a = new Advisor();

        a.setEmail("testen@hotmail.com");

        assertEquals("testen@hotmail.com", a.getEmail());
    }

}