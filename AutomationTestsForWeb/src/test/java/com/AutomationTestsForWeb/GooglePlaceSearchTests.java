package com.AutomationTestsForWeb;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

public class GooglePlaceSearchTests {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    private String baseURI = "https://maps.googleapis.com";
	private String apiKey = "AIzaSyDM5iDuSBXOR29A0nJCl3yKFv6aDTp7UQ8";
	private String query = "Churchgate Station";
	private String address = "Maharshi Karve Road, Churchgate, Mumbai, Maharashtra 400020, India";
	private String mapsApiPlaceTextSearchJson = "/maps/api/place/textsearch/json";
	private String mapsApiPlaceTextSearchXml = "/maps/api/place/textsearch/xml";

  @BeforeClass
  public void setBaseUri () {

    RestAssured.baseURI = baseURI;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @BeforeClass
  public void requestSpec () {

	RequestSpecBuilder builder = new RequestSpecBuilder();
	builder.setBaseUri (baseURI);
	builder.setBasePath (mapsApiPlaceTextSearchJson);
	builder.addParam ("query", query);
	builder.addParam ("key", apiKey);
    
    requestSpec = builder.build ();

 }

  @BeforeClass
  public void responseSpec () {
    
    RestAssured.baseURI=baseURI;
    ResponseSpecBuilder builder = new ResponseSpecBuilder ();
    builder.expectContentType (ContentType.JSON);
    builder.expectStatusCode (200);
    
    responseSpec= builder.build ();
   
 }

  @Test
  public void testJSONStatusCode () {
    
    Response res = 
    given()
    .param("query", query)
    .param("key", apiKey)
    .when()
    .get(mapsApiPlaceTextSearchJson);

    AssertJUnit.assertEquals(res.statusCode(), 200);
  }

@Test
public void testJSONStatusCodeRestAssured() {

	given()
	    .param("query", query)
        .param("key", apiKey)
        .when()
        .get(mapsApiPlaceTextSearchJson)
        .then()
        .assertThat().statusCode(200);
  }

@Test
  public void testJSONPrintResult()  {
    Response res = given()
    .param("query", query)
    .param("key", apiKey)
    .when()
    .get(mapsApiPlaceTextSearchJson)
    .then()
    .contentType(ContentType.JSON)
    .extract().response();

    System.out.println (res.asString ());

  }

  @Test
public void testJSONAddress ()  {
  Response res = given()
  .param ("query", query)
  .param ("key", apiKey)
  .when()
  .get (mapsApiPlaceTextSearchJson)
  .then()
  .contentType(ContentType.JSON)
  .extract().response();
  
  String result = res.path("results[0].formatted_address");
  
  AssertJUnit.assertEquals(result, address);
  }

@Test
  public void testXMLStatusCode () {
    
    Response res = 
    given()
    .param("query", query)
    .param("key", apiKey)
    .when()
    .get(mapsApiPlaceTextSearchXml);

    AssertJUnit.assertEquals(res.statusCode(), 200);
  }

@Test
public void testXMLStatusCodeRestAssured() {

	given()
	    .param("query", query)
        .param("key", apiKey)
        .when()
        .get(mapsApiPlaceTextSearchXml)
        .then()
        .assertThat().statusCode(200);
  }

@Test
public void testXMLStatusCodeRestAssuredFAIL() {

	given()
	    .param("query", query)
        .param("key", apiKey)
        .when()
        .get(mapsApiPlaceTextSearchXml)
        .then()
        .assertThat().statusCode(500);
  }

@Test
public void testXMLContentTypeStatusCodeRestAssured() {

	given()
	    .param("query", query)
        .param("key", apiKey)
        .when()
        .get(mapsApiPlaceTextSearchXml)
        .then()
        .contentType(ContentType.XML)
        .assertThat().statusCode(200);
  }

@Test
public void testXMLContentTypeStatusCodeRestAssuredFAIL() {

	given()
	    .param("query", query)
        .param("key", apiKey)
        .when()
        .get(mapsApiPlaceTextSearchXml)
        .then()
        .contentType(ContentType.JSON)
        .assertThat().statusCode(200);
  }

@Test
  public void testXMLPrintResult()  {
    Response res = given()
    .param ("query", query)
    .param ("key", apiKey)
    .when()
    .get (mapsApiPlaceTextSearchXml)
    .then()
    .contentType(ContentType.XML)
    .extract().response();

    System.out.println (res.asString ());

  }

@Test
public void testXMLAddress () {
  
 String res = given()
	  .param ("query", query)
      .param ("key", apiKey)
      .when ()
      .get (mapsApiPlaceTextSearchXml)
      .then ()
      .contentType(ContentType.XML)
      .extract()
      .path("placesearchresponse.result[0].formatted_address");

  AssertJUnit.assertEquals(res, address);
  }

@Test
public void testRequestSpec () {
  
       given()
      .spec (requestSpec)
      .when ()
      .log().all()
      .get ("")
      .then ()
      .contentType (ContentType.JSON)
      .statusCode (200);
       }

@Test
public void testResponseSpec() {
    
       given()
       .param("query", "restaurants in mumbai")
       .param("key", apiKey)
       .when()
       .get(mapsApiPlaceTextSearchJson)
       .then()
       .spec(responseSpec);
       }

@Test
public void testRequestAndResponseSpec() {
    
       given()
       .spec (requestSpec)
       .when ()
       .get ("")
       .then ()
       .spec (responseSpec);
       }

@Test
public void logTest() {
    
       given()
       .spec (requestSpec)
       .when ()
       .log ()
       .all ()
       .get (mapsApiPlaceTextSearchJson);
       }

@Test
public void loggingTestIfError() {
    
    given()
       .param ("query", query)
       .param ("key", apiKey )
       .when ()
       .get (mapsApiPlaceTextSearchJson)
       .then ()
       .log ().ifError ().assertThat ().statusCode (200);
       }
}
