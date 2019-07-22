package more.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import more.core.Operater.HttpApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

public class TestCaseBase extends HttpApi {
    private static String reportLocation = "report/ExtentReport.html";
    protected static ExtentReports extent;
    protected Logger logger;

    protected TestCaseBase() {
        this.logger = LogManager.getLogger(this.getClass().getName());
    }

    @BeforeSuite
    protected void beforeSuite() {
        System.out.println("before suite");
        extent = new ExtentReports(reportLocation, true, NetworkMode.OFFLINE);
        extent.addSystemInfo("Host Name", "Lining");
    }

    @AfterSuite
    protected void afterSuite() {
        extent.close();
    }

    protected static ExtentReports getExtent(){
        return extent;
    }

    protected String request(String url){
        return doGet(url);
    }

}
