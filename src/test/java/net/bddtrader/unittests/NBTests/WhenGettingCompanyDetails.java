package net.bddtrader.unittests.NBTests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class WhenGettingCompanyDetails {
    @Before
    public void prepare_rest_config(){
        RestAssured.baseURI="https://bddtrader.herokuapp.com/api";//"http://localhost:9000/api";
    }
    @Test
    public void should_return_name_and_sector(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("companyName", Matchers.equalTo("Apple, Inc."))
                .body("sector",Matchers.equalTo("Electronic Technology"));
    }
    @Test
    public void should_return_news_for_a_requested_company(){
        RestAssured.given()
                .queryParam("symbols","fb")
                .when()
                .get("/news")
                .then()
                .body("related",Matchers.everyItem(containsString("FB")));
    }

    @Test
    public void find_a_simple_field_value(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("industry", Matchers.equalTo("Telecommunications Equipment"));
    }
    @Test
    public void check_that_a_field_value_conatins_a_given_string(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/company")
                .then()
                .body("description", Matchers.containsString("smartphones"));
    }
    @Test
    public void find_a_nested_field_value(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("quote.symbol",equalTo("AAPL"));
    }
    @Test
    public void find_a_list_of_values(){
        RestAssured
                .when()
                .get("/tops/last")
                .then()
                .body("symbol",hasItems("PTN","PINE","TRS"));
    }
    @Test
    public void make_sure_at_least_one_item_matches_a_given_condition(){
        RestAssured
                .when()
                .get("/tops/last")
                .then()
                .body("price",hasItems(greaterThan(100.0f)));
                //.body("findAll{t->t.price>100}.size()",greaterThanOrEqualTo(1));
    }
    @Test
    public void find_a_field_of_an_element_in_a_list(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("trades[0].price",equalTo(319.59f));
    }

    @Test
    public void find_a_field_of_the_last_element_in_a_list(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("trades[-1].price",equalTo(319.54f));
    }
    @Test
    public void find_number_of_trades(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("trades.size()",equalTo(20));
    }
    @Test
    public void find_the_minimum_trade_price(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("trades.price.min()",equalTo(319.38f));
                //.body("trades.min{t->t.price}.price",equalTo(319.38f));
    }
    @Test
    public void find_the_size_of_the_trade_with_minimum_trade_price(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("trades.min{it.price}.volume",equalTo(100.0f));
    }
    @Test
    public void find_the_number_of_trades_with_a_price_greater_than_some_value(){
        RestAssured.given()
                .pathParam("symbol","aapl")
                .when()
                .get("/stock/{symbol}/book")
                .then()
                .body("trades.findAll{it.price > 319.50}.size()",equalTo(13));
    }
    @Test
    public void should_return_name_and_sector_from_local(){
        RestAssured.get("/stock/aapl/company")
                .then()
                .body("companyName", Matchers.equalTo("Apple, Inc."))
                .body("sector",Matchers.equalTo("Electronic Technology"));
    }


}