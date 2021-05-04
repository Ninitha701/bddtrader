package net.bddtrader.unittests.NBTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WhenDeleteOrUpdateAClient {
    @Before
    public void prepare_rest_config(){
        RestAssured.baseURI="http://localhost:9000/api";
    }
    @Test
    public void delete_a_client_record(){
        String id= getClientId(Client.withFirstName("Andy").andLastName("David").andEmail("Andy@gmail.com"));
        //Delete Client
        RestAssured.given().delete("/client/{id}",id);
        //Then the client should no longer exist
        RestAssured.given().get("/client/{id}",id)
                .then()
                .statusCode(404);
    }
    @Test
    public void update_email_of_a_client_record(){
        String id= getClientId(Client.withFirstName("Andy").andLastName("David").andEmail("Andy@gmail.com"));
        //Update email of the Client
        Map<String, Object> updates=new HashMap<>();
        updates.put("email","david@gmail.com");
        RestAssured.given()
                .contentType(ContentType.JSON).body(updates)
        .put("/client/{id}",id)
                .then().statusCode(200);
        //Then the client should have new email id
        RestAssured.given().get("/client/{id}",id)
                .then()
                .body("email", Matchers.equalTo("david@gmail.com"));
    }

    public String getClientId(Client existingClient){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(existingClient)
                .post("/client")
                .jsonPath().getString("id");
    }
}


