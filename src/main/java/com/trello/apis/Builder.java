package com.trello.apis;

import com.trello.utils.dataReader.PropertyReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import io.qameta.allure.restassured.AllureRestAssured;
public class Builder {
    private static final String baseURI = PropertyReader.getProperty("baseUrlApi");
    private static final String apiKey  = PropertyReader.getProperty("apiKey");
    private static final String apiToken = PropertyReader.getProperty("apiToken");

    //private constructor to prevent instantiation
    public Builder() {
    }

    // Base spec with auth only
    public static RequestSpecification getTrelloSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .addQueryParam("key", apiKey)
                .addQueryParam("token", apiToken)
                .addFilter(new AllureRestAssured())
                .build();
    }

    // Base spec with auth + extra query params
    public static RequestSpecification getTrelloSpec(Map<String, ?> queryParams) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .addQueryParam("key", apiKey)
                .addQueryParam("token", apiToken)
                .addFilter(new AllureRestAssured());


        if (queryParams != null && !queryParams.isEmpty()) {
            builder.addQueryParams(queryParams);
        }

        return builder.build();
    }
}
