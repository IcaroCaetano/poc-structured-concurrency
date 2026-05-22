package com.myproject.poc_structured_concurrency.context;

public final class RequestContext {

    private RequestContext() {
    }

    public static final ScopedValue<String> REQUEST_ID =
            ScopedValue.newInstance();
}