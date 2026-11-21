package com.yoterra.hippo.services;

import org.junit.jupiter.api.Test;

import static com.yoterra.hippo.services.HashService.hash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashServiceTest {

    @Test
    void sameInputGivesSameHash() throws Exception {
        assertEquals(hash("Ritual project"), hash("Ritual project"));
    }

    @Test
    void differentInputsGiveDifferentHashes() throws Exception {
        assertNotEquals(hash("project-a"), hash("project-b"));
        assertNotEquals(hash("1.0.28"), hash("1.0.29"));
    }

    @Test
    void hashIsNeverNegative() throws Exception {
        for (String s : new String[] {"", "a", "user-1", "C87E697A-032E-4570-A239-8B3952848A95"}) {
            assertTrue(hash(s) >= 0, "negative hash for '" + s + "'");
        }
    }

    @Test
    void nullInputIsRejected() {
        assertThrows(NullPointerException.class, () -> hash(null));
    }
}
