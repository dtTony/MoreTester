package more.testcases;

import more.base.TestCaseBase;
import more.base.TestngListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestngListener.class})
public class TestCases2 extends TestCaseBase{

    @Test(invocationCount = 2)
    public void main(){
//        logger.error("java log");
        System.out.println("test2");
//        Assert.assertEquals(1,2);
//        getRequest("http://www.baidu.com");
//        Map<String, Object> mapParam = new HashMap<>();
//        mapParam.put("alias", "TitanID_172");
//        mapParam.put("conditions[effect_status]", "1");
//        mapParam.put("page", "1");
//        mapParam.put("size", "10");
//        String jsonParam = "{'alias': 'TitanID_172', 'conditions[effect_status]':'1', 'page':'1', 'size':'1'}";
//        postRequestString("http://10.96.94.212:8800/titan/iGetListData",jsonParam);
    }
}
