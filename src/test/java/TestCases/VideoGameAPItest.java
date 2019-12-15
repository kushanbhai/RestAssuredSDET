package TestCases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class VideoGameAPItest {

	@Test(priority = 1)
	public void getAllvideogames() {
		given().when().get("http://localhost:8080/app/videogames").then().statusCode(200);
	}

	@Test(priority = 2)
	public void createnewVideogame() {
		HashMap data = new HashMap();

		data.put("id", "31");
		data.put("name", "spider-man");
		data.put("releaseDate", "2019-12-15T08:36:17.907Z");
		data.put("reviewScore", "5");
		data.put("category", "adventure");
		data.put("rating", "universal");

		Response res = given().contentType("application/json").body(data).when()
				.post("http://localhost:8080/app/videogames").then().statusCode(200).log().body().extract().response();

		String jsonstring = res.asString();
		Assert.assertEquals(jsonstring.contains("Record Added Successfully"), true);
	}

	@Test(priority = 3)
	public void getspecificvideogame() {
		given().contentType("application/json").when().get("http://localhost:8080/app/videogames/27").then()
				.statusCode(200).log().body().body("videoGame.id", equalTo("27"))
				.body("videoGame.name", equalToIgnoringCase("bat-man"));

	}

	@Test(priority = 4)
	public void updatevideogame() {
		HashMap data = new HashMap();

		data.put("id", "27");
		data.put("name", "bat-man");
		data.put("releaseDate", "2019-12-15T08:36:17.907Z");
		data.put("reviewScore", "15");
		data.put("category", "adventure");
		data.put("rating", "universal");

		given().contentType("application/json").body(data).when().put("http://localhost:8080/app/videogames/27").then()
				.statusCode(200).log().body().body("videoGame.id", equalTo("27"))
				.body("videoGame.name", equalToIgnoringCase("bat-man"));

	}

	@Test(priority = 5)
	public void deletevideogame() {
		Response res = given().when().delete("http://localhost:8080/app/videogames/100").then().statusCode(200).log()
				.body().extract().response();
		String jsonstring = res.asString();
		Assert.assertEquals(jsonstring.contains("Record Deleted Successfully"), true);
	}
}
