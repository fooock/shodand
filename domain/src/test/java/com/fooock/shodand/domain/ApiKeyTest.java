package com.fooock.shodand.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class ApiKeyTest {

    @Test
    public void invalidApiKey() throws Exception {
        String apiKey = "";
        ApiKey key = new ApiKey(apiKey);
        assertFalse(key.valid());
        assertFalse(new ApiKey(null).valid());
    }

    @Test
    public void validApiKey() throws Exception {
        String apiKey = "asdafkkw3451lasd8jqs823madsf";
        ApiKey key = new ApiKey(apiKey);
        assertTrue(key.valid());
    }
}