package com.trello.apis;

import com.trello.utils.logs.LogsManager;
import com.trello.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;
    //constructor
    public UserManagementAPI()
    {
        requestSpecification = RestAssured.given();
        verification = new Verification();

    }

    //endpoints
    private static final String createAccount_Endpoint = "/createAccount";
    private static final String deleteAccount_Endpoint = "/deleteAccount";

    //name, email, password, title (for example: Mr, Mrs, Miss), birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number
    @Step("Create new user account with full details")
    public UserManagementAPI createAccount(String name, String email, String password, String title, String birth_date, String birth_month, String birth_year, String firstname, String lastname, String company, String address1, String address2, String country, String zipcode, String state, String city, String mobile_number)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", title);
        formParams.put("birth_date", birth_date);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagementSpec(formParams))
                .post(createAccount_Endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Create new user account with minimal details")
    public UserManagementAPI createAccount(String name, String email, String password , String firstname, String lastname)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "6");
        formParams.put("birth_month", "December");
        formParams.put("birth_year", "1997");
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("zipcode", "123456");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("mobile_number", "0123456789");
        response = requestSpecification.spec(Builder.getUserManagementSpec(formParams))
                .post(createAccount_Endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Delete user account with email: {email}")
    public UserManagementAPI deleteAccount(String email, String password)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementSpec(formParams))
                .delete(deleteAccount_Endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    //validations
    @Step("Verify the account is created successfully")
    public UserManagementAPI verifyAccountCreated()
    {
        verification.Equals(response.jsonPath().get("message"), "User created!",
                "Account creation failed");
        return this;
    }

    @Step("Verify the account is deleted successfully")
    public UserManagementAPI verifyAccountDeleted() {
        verification.Equals(response.jsonPath().get("message"), "Account deleted!",
                "Account deletion failed");
        return this;
    }
}
