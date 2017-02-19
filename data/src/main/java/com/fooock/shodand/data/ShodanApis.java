package com.fooock.shodand.data;

import com.fooock.shodan.ShodanExploitApi;
import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodan.ShodanStreamingApi;

/**
 * This class contains all Shodan APIs
 */
public class ShodanApis {

    private final ShodanRestApi shodanRestApi;
    private final ShodanExploitApi shodanExploitApi;
    private final ShodanStreamingApi shodanStreamingApi;

    public ShodanApis(String apiKey) {
        shodanExploitApi = new ShodanExploitApi(apiKey);
        shodanRestApi = new ShodanRestApi(apiKey);
        shodanStreamingApi = new ShodanStreamingApi(apiKey);
    }

    public ShodanRestApi getShodanRestApi() {
        return shodanRestApi;
    }

    public ShodanExploitApi getShodanExploitApi() {
        return shodanExploitApi;
    }

    public ShodanStreamingApi getShodanStreamingApi() {
        return shodanStreamingApi;
    }
}
