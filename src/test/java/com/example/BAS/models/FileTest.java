package com.example.BAS.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @Test
    void shouldGetId() {
        File f = new File();

        f.id = 1L;

        assertEquals(1, f.getId());
    }

    @Test
    void shouldSetStatusComment() {
        File f = new File();

        f.setStatusComment("abc");

        assertEquals("abc", f.getStatusComment());
    }

    @Test
    void shouldSetStatus() {
        File f = new File();

        f.setStatus("Test");

        assertEquals("Test", f.getStatus());
    }

    @Test
    void shouldSetComment() {
        File f = new File();

        f.setComment("comment");

        assertEquals("comment", f.getComment());
    }

    @Test
    void shouldSetFileType() {
        File f = new File();

        f.setFileType("LOR");

        assertEquals("LOR", f.getFileType());
    }

    @Test
    void shouldSetContractAmount() {
        File f = new File();

        f.setContractAmount(2500);

        assertEquals(2500, f.getContractAmount());
    }
}