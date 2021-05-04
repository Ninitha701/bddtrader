package net.bddtrader.unittests.NBTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.condition.Not.not;
import static org.hamcrest.Matchers.equalTo;

public class WhenCreatingANewClient {
    @Before
    public void prepare_rest_config(){
        RestAssured.baseURI="http://localhost:9000/api";//"https://bddtrader.herokuapp.com/api";
    }
    @Test
    public void ecah_new_client_should_get_a_unique_id(){
        String newClient="{\n" +
                "  \"email\": \"Test@test.com\",\n" +
                "  \"firstName\": \"test\",  \n" +
                "  \"lastName\": \"string\"\n" +
                "}";
        RestAssured.given()
                .contentType("application/json")
                .body(newClient)
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)));
    }
    @Test
    public void create_a_new_client_using_client_class(){
        Client newClient=Client.withFirstName("Jim").andLastName("Halpert").andEmail("jim@halpert.com");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newClient)
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)));
    }
    @Test
    public void create_a_new_client_using_a_map(){
        Map<String,Object> newClient= new HashMap<>();
        newClient.put("firstName","Kevin");
        newClient.put("lastName","Malone");
        newClient.put("email","kevin@malone.com");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newClient)
                .post("/client")
                .then().statusCode(200)
                .and().body("id", Matchers.not(equalTo(0)));
    }
}
