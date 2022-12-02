package com.poc.listners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.poc.reports.MyExtentReport;
import com.poc.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        try {
            MyExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                     .assignCategory("Sanity")
                    .assignAuthor("Automation Team");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        MyExtentReport.getTest().log(Status.PASS, "Test Passed !!!");    }

    @Override
    public void onTestFailure(ITestResult result) {

        MyExtentReport.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        MyExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //  ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            MyExtentReport.getReporter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}