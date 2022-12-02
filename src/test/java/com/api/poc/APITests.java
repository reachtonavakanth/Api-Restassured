package com.api.poc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.poc.base.BaseApiTest;
import com.poc.base.BuildRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.utils.Utils;
import io.restassured.response.Response;

public class APITests extends BaseApiTest {

    public String base[] = null;
    public String getLocation[] = null;
    public String createLocation[] = null;
    public Utils util = null;

    @BeforeClass
    public void getData() {
        util = new Utils();
        base = util.toReadExcelData(dataFile, sheet[3], "base");
        getLocation = util.toReadExcelData(dataFile, sheet[3], "getlocation");
        createLocation = util.toReadExcelData(dataFile, sheet[3], "createlocation");
    }

    @Test(enabled = true)

    public void verifyGetLocation() {

        String str[] = util.toReadExcelData(dataFile, sheet[2], "TC_01");
        String actVal[] = str[1].split(",");

        Response rsp = verifyGet(base[1] + getLocation[1]);

        addLogs("Response \n" + rsp.asString());

        // Validate Status code
        validateStatusCode(200, rsp);

        List<String> li = new ArrayList<String>();

        // get Json List from resp
        int objSize = getJsonPathObj(rsp).getString("items[0].location").length();

        // iterate a get value at each key for all json objects
        for (int i = 0; i <= objSize; i++) {
            li.add(getJsonPathObj(rsp).getString("items[" + i + "].location"));
        }
        addLogs("Expected Location is : " + getStringFromStringArr(actVal));
        // Validate Actual & expected
        for (int i = 0; i < li.size(); i++) {
            if (Arrays.asList(actVal).contains(li.get(i)) == true) {
                addLogs(String.valueOf(li.get(i)) + " is present in expected list of locations");
            } else {
                addLogs(String.valueOf(li.get(i)) + " is not present in expected list of locations");
            }
            Assert.assertTrue(Arrays.asList(actVal).contains(li.get(i)), li.get(i) + " is ");
        }
    }

    @Test
    public void createLocation() throws JsonProcessingException {

        String str[] = util.toReadExcelData(dataFile, sheet[2], "TC_02");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", str[2]);
        map.put("cost", str[3]);
        map.put("location", str[4]);

        BuildRequest request = new BuildRequest();
        addLogs("Request " + request.buildjson(map));
        Response rsp = verifyPost(request.buildjson(map), base[1] + createLocation[1]);

        addLogs("Response \n" + rsp.asString());
        validateStatusCode(200, rsp);

        // validating payload  & response data

        Assert.assertTrue(map.get("id").equals(getValue(rsp, "id")));
        addLogs("Request Payload id : " + map.get("id"));
        addLogs("Response Payload id : " + getValue(rsp, "id"));

        Assert.assertTrue(map.get("cost").equals(getValue(rsp, "cost")));
        addLogs("Request Payload cost : " + map.get("cost"));
        addLogs("Response Payload cost : " + getValue(rsp, "cost"));

        Assert.assertTrue(map.get("location").equals(getValue(rsp, "location")));
        addLogs("Request Payload location : " + map.get("location"));
        addLogs("Response Payload cost : " + getValue(rsp, "location"));
    }

}
