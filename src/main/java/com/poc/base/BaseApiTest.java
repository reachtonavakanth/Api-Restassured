package com.poc.base;

import java.io.File;

import com.aventstack.extentreports.Status;
import com.poc.reports.MyExtentReport;
import com.poc.utils.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.testng.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;

public class BaseApiTest extends Utils {

	public String dataFile = "src"+File.separator+"test"+File.separator+"resources" + File.separator + "TestData.xlsx";
	public String[] sheet = { "testdata", "config", "api", "apiconfig" };

	/*
	 * @author:Navakanth Description: To perform a get request
	 */

	public Response verifyGet(String url) {
		addLogs("URL is " + url);
		addLogs("Get Request is successful");
		return RestAssured.given().when().get(url);
	}

	/*
	 * @author:Navakanth Description: To verify a Post request
	 */

	public Response verifyPost(String str, String url) throws JsonProcessingException {
		addLogs("URL is " + url);
		addLogs("Post Request is successful");
		return RestAssured.given().body(str).when().post(url);

	}

	/*
	 * @author:Navakanth Description: To validate Status codes
	 */

	public void validateStatusCode(int expected, Response rsp) {
		addLogs("Expected is : "+String.valueOf(expected));
		addLogs("Actual is : "+String.valueOf(rsp.getStatusCode()));
		Assert.assertTrue(expected == rsp.getStatusCode());

	}

	/*
	 * @author:Navakanth Description: To get value from Json response
	 */

	public String getValue(Response rsp, String key) {
		JsonPath jsonPath = new JsonPath(rsp.asString());
		return jsonPath.getString(key);
	}

	/*
	 * @author:Navakanth Description: To get JsonPath object
	 */
	public JsonPath getJsonPathObj(Response rsp) {
		return new JsonPath(rsp.asString());
	}

	@BeforeTest
	public void before() {
		String strFile = "logs" + File.separator + getDateTime();
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		ThreadContext.put("ROUTINGKEY", strFile);
	}
	public void addLog(String msg) {
		log().info(msg);
	}

	public void addLogs(String msg) {
		MyExtentReport.getTest().log(Status.INFO, msg);
		log().info(msg);
	}
}
