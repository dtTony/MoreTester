package more;

import more.base.TestNGXml;

public class RunTestCases {

    public static void main(String[] args){
        TestNGXml x = new TestNGXml();
        x.RunSuites("more.testcases.TestCases1");
        x.RunSuites("more.testcases.TestCases2");
//        x.CreateTestNGXML("more.testcases.", "./target/classes/more/testcases/");
//        XmlSuite suite = new XmlSuite();
//        suite.setName("TmpSuite");
//        XmlTest test = new XmlTest(suite);
//        test.setName("TmpTest");
//        List<XmlClass> classes = new ArrayList<>();
//        classes.add(new XmlClass("more.TestCases.TestCases"));
//        test.setXmlClasses(classes) ;
//
//
//        List<XmlSuite> suites = new ArrayList<>();
//        suites.add(suite);
//        TestNG tng = new TestNG();
//        tng.setXmlSuites(suites);
//        tng.run();
    }
}
