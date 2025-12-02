package com.trello.apis;

import com.trello.utils.dataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static String baseURI = PropertyReader.getProperty("baseUrlApi");
    //private constructor to prevent instantiation
    public Builder() {
    }

    //build request specification
    public static RequestSpecification getUserManagementSpec(Map<String, ?> formParams)
    {
        return new RequestSpecBuilder().setBaseUri(baseURI)
                .setContentType(ContentType.URLENC)
                .addFormParams(formParams)
                .build();
    }
}
