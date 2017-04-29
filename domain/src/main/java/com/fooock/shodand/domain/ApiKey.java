package com.fooock.shodand.domain;

/**
 *
 */
public class ApiKey {

    private final String apiKey;

    public ApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean valid() {
        return apiKey != null && !apiKey.isEmpty();
    }
}
