package com.juaracoding.aihujian6.ujian6.page;
/*
IntelliJ IDEA 2023.3.6 (Community Edition)
Build #IC-233.15026.9, built on March 21, 2024
@Author kiyandark a.k.a. Muhammad Irfan Syaifur Rizal Wiratama
Java Developer
Created on 18/05/2024 13:33
@Last Modified 18/05/2024 13:33
Version 1.0
*/

import com.juaracoding.misrwujian6.connection.Constants;
import com.juaracoding.misrwujian6.connection.DriverSingleton;
import com.juaracoding.misrwujian6.ujian6.scenariomapping.Test1Soal11;
import com.juaracoding.misrwujian6.util.GlobalFunction;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class Test1Hooks {
    public static WebDriver driver;
    public static ExtentTest extentTest;
    public static ExtentReports reports = new ExtentReports(GlobalFunction.rootProject+"/practiceautomation-report/extentreport/login.html");
    private static Test1Soal11[] tests = Test1Soal11.values();
    private static final int[] DATA_OUTLINE = {1,1,1,1,1,1,1};
    private String testReport = "";

    @Before
    public void setUp() {
        DriverSingleton.getInstance(Constants.EDGE);
        driver = DriverSingleton.getDriver();
        testReport = tests[GlobalFunction.testCount].getTestName();
        extentTest = reports.startTest(testReport);
        if(GlobalFunction.countOutline==DATA_OUTLINE[GlobalFunction.testCount])
        {
            GlobalFunction.countOutline=0;
            GlobalFunction.testCount++;
        }
        GlobalFunction.countOutline++;
    }

    @AfterStep
    public void getResultStatus(Scenario scenario) throws IOException {
        if(scenario.isFailed()) {
            String screenshotPath = GlobalFunction.getScreenshot(driver, "PTA_LoginOutlineHooks"+scenario.getName().replace(" ", "_"));
            extentTest.log(LogStatus.FAIL, scenario.getName()+"\n"
                    +extentTest.addScreenCapture(screenshotPath));;
        }
    }

    @After
    public void closeObject() {
        reports.endTest(extentTest);
        reports.flush();
    }

    @AfterAll
    public static void closeBrowser() {
        GlobalFunction.delay(Constants.TIMEOUT_DELAY, Constants.GLOB_PARAM_DELAY);
        DriverSingleton.closeObjectInstance();
    }
}
