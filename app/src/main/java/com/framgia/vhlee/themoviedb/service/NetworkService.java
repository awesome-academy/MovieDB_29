package com.framgia.vhlee.themoviedb.service;

public class NetworkService extends RetrofitBuilder {
    private static ClientRequest sClientRequest;

    public static ClientRequest getInstance() {
        if (sClientRequest == null) {
            sClientRequest = RetrofitBuilder.initClient().create(ClientRequest.class);
        }
        return sClientRequest;
    }
}
