package com.yoterra.hippo.services;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static com.yoterra.hippo.services.HashService.hash;
import static org.junit.jupiter.api.Assertions.*;

class HashServiceTest {
    String project_id1, project_id2;

    @Test
    void hashTest() throws NoSuchAlgorithmException {
        project_id1 = "Hippo shopping";
        project_id2 = "Hippo shopping";
        assertEquals(hash(this.project_id1), hash(this.project_id2));
    }

}
