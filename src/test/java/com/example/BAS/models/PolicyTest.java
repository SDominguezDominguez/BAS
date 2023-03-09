package com.example.BAS.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PolicyTest {
    @Test
    void shouldGetId() {
        Policy p = new Policy();

        p.id = 1L;

        assertEquals(1, p.getId());
    }

    @Test
    void shouldSetAndGetPolicyNumber() {
        Policy p = new Policy();

        p.setPolicyNumber("abc");
        String result = p.getPolicyNumber();

        assertEquals("abc", result);
    }

    @Test
    void shouldSetAndGetAmount() {
        Policy p = new Policy();

        p.setAmount(2500);
        int result = p.getAmount();

        assertEquals(2500, result);
    }

    @Test
    void shouldSetAndGetReceiveDateAmount() {
        Policy p = new Policy();

        p.setReceiveDateAmount(LocalDate.of(1995, 4, 20));

        assertEquals(LocalDate.of(1995, 4, 20), p.getReceiveDateAmount());
    }

    @Test
    void shouldSetAndGetReceiveDatePsk() {
        Policy p = new Policy();

        p.setReceiveDatePsk(LocalDate.of(1995, 4, 20));

        assertEquals(LocalDate.of(1995,4,20), p.getReceiveDatePsk());
    }

    @Test
    void shouldSetAndGetReminderDatePsk() {
        Policy p = new Policy();

        p.setReminderDatePsk(LocalDate.of(1995, 4, 20));

        assertEquals(LocalDate.of(1995,4,20), p.getReminderDatePsk());
    }

}