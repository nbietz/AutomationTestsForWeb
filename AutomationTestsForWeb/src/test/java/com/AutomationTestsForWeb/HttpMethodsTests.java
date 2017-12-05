package com.AutomationTestsForWeb;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static com.jayway.restassured.RestAssured.*;

import com.AutomationTestsForWeb.Posts;
import com.AutomationTestsForWeb.PostsNoAuthor;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static org.hamcrest.Matchers.lessThan;

public class HttpMethodsTests {

  @BeforeClass
  public void setBaseUri () {

    RestAssured.baseURI = "http://localhost:3000";
  }

@Test
  public void postString () {
    
    given().body("{\"id\":\"2\","
    +"\"title\":\"Hello Mumbai post string\","
    +"\"author\":\"StaffWriter\"}")
    .when()
    .contentType(ContentType.JSON)
    .post("/posts");
    }

@Test
public void sendPostObject () {
  
  Posts post = new Posts();
  post.setId ("3");
  post.setTitle ("Hello India post");
  post.setAuthor ("StaffWriter");
  
  given().body(post)
  .when()
  .contentType(ContentType.JSON)
  .post("/posts");
  }

@Test
public void updateUsingPut () {
  
  Posts post = new Posts();
  post.setId ("3");
  post.setTitle ("Hello Bhutan put");
  post.setAuthor ("StaffWriter");
  
  given().body (post)
  .when ()
  .contentType (ContentType.JSON)
  .put ("/posts/3");
  }

@Test
public void updateUsingPatch () {
  
  PostsNoAuthor post = new PostsNoAuthor();
  post.setId ("3");
  post.setTitle ("Hello Vietnam patch");
  
  given().body (post)
  .when ()
  .contentType (ContentType.JSON)
  .patch ("/posts/3");
  }

@Test
public void deleteTest () {
    
  given()
  .when ()
  .contentType (ContentType.JSON)
  .delete ("/posts/2");
  }

@Test
public void responseTime ()  {
  given()
  .when ()
  .get ("/posts")
  .then ()
  .time (lessThan (500l));
  }

}
