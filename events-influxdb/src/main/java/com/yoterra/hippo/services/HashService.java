package com.yoterra.hippo.services;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    public static Long hash(String str) throws NoSuchAlgorithmException {
        MessageDigest msg = MessageDigest.getInstance("SHA-256");
        byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));

        long value = 0L;

        for (byte b : hash) {
            value = (value << 8) + (b & 255);
            value = Math.abs(value);
        }
        return value;
    }
}
