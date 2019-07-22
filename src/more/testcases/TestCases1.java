package more.testcases;

import more.base.TestCaseBase;
import more.base.TestngListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestngListener.class})
public class TestCases1 extends TestCaseBase{

    @Test
    public void main(){
//        logger.error("java log");
        System.out.println("test1");
//        System.out.println(request("http://10.179.16.217:9527/disf/service/get?userName=dt&serviceName=disf!common-plat-gs-cratos"));
    }
}
