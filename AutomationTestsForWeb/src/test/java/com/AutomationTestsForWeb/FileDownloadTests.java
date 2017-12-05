package com.AutomationTestsForWeb;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import static com.jayway.restassured.RestAssured.*;


public class FileDownloadTests {

 @Test
  public void verifyFileDownload ()  {
   
   int fileSize;
   File filePath =  new File (System.getProperty("user.dir")+"/resources/rest-assured-3.0.5-dist.zip");
   
   fileSize = (int)filePath.length();
   
   System.out.println("The actual value is "+fileSize);

   byte [] expectedValue =
     given ()
     .get("http://dl.bintray.com/johanhaleby/generic/rest-assured-3.0.5-dist.zip")
     .asByteArray();
   
          System.out.println("The expected value is "+expectedValue.length);

   AssertJUnit.assertEquals(fileSize, expectedValue.length); 
  }
}