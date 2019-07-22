package more.base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestngListener extends TestListenerAdapter {
    private Logger logger = LogManager.getLogger(this.getClass().getName());
    protected ExtentReports extent;
    protected ExtentTest test;

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        logger.info("【" + tr.getName() + " Start】");
        extent=TestCaseBase.getExtent();
        test= extent.startTest(tr.getName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        logger.info("【" + tr.getName() + " Failure】");
        test.log(LogStatus.FAIL, tr.getThrowable());
        extent.endTest(test);

    }


    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        logger.info("【" + tr.getName() + " Skipped】");
        test.log(LogStatus.SKIP, "SKIP");
        extent.endTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        logger.info("【" + tr.getName() + " Success】");
        test.log(LogStatus.PASS, "Pass");
        extent.endTest(test);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }
}
