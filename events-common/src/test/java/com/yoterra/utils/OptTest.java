package com.yoterra.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptTest {

    @Test
    void getChainsAndStopsAtNull() {
        assertEquals(Integer.valueOf(5), Opt.get("hello", String::length));
        assertEquals("5", Opt.get("hello", String::length, String::valueOf));
        assertNull(Opt.get((String) null, String::length));
        assertNull(Opt.get("x", s -> (String) null, String::length));
    }

    @Test
    void getOrDefFallsBackOnNull() {
        assertEquals(Integer.valueOf(3), Opt.getOrDef("abc", 0, String::length));
        assertEquals(Integer.valueOf(0), Opt.getOrDef((String) null, 0, String::length));
        assertTrue(Opt.getOrDef("abc", false, String::length, Integer.valueOf(3)::equals));
    }

    @Test
    void defaultValues() {
        assertEquals("a", Opt.defVal("a", "b"));
        assertEquals("b", Opt.defVal(null, "b"));
        assertEquals("b", Opt.lazyDefVal(null, () -> "b"));
    }

    @Test
    void reqThrowsTheGivenException() {
        assertSame("v", Opt.req("v", () -> new IllegalStateException("missing")));
        assertThrows(IllegalStateException.class, () -> Opt.req(null, () -> new IllegalStateException("missing")));
    }

    @Test
    void strmIsNullSafe() {
        assertTrue(Opt.strm((List<String>) null).toList().isEmpty());
        assertFalse(Opt.strm((String[]) null).anyMatch(s -> true));
        assertTrue(Opt.strm((String) null, s -> Collections.singletonList(s)).toList().isEmpty());
    }

    @Test
    void strmOperations() {
        List<String> items = Arrays.asList("a", null, "bb", "ccc");

        assertEquals(Arrays.asList(1, 2, 3), Opt.strm(items).noNulls().map(String::length).toList());
        assertEquals(2, Opt.strm(items).noNulls().filter(s -> s.length() > 1).toSet().size());
        assertTrue(Opt.strm("x", "y").allMatch(s -> s.length() == 1));

        Map<Integer, String> byLength = Opt.strm(items).noNulls().toMapByKey(String::length);
        assertEquals("bb", byLength.get(2));
    }

    @Test
    void collectionAndNumberHelpers() {
        assertEquals("a", CollectionUtils.first(Arrays.asList("a", "b")));
        assertNull(CollectionUtils.first(Collections.emptyList()));
        assertNull(CollectionUtils.first(null));

        assertEquals(Long.valueOf(42), NumUtils.toLong(" 42 "));
        assertNull(NumUtils.toLong(""));
        assertThrows(NumberFormatException.class, () -> NumUtils.toLong("abc"));
    }
}
