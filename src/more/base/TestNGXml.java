package more.base;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestNGXml {

    /**
     * 创建testngxml并执行
     */

    public void RunSuites(String testCaseClass){
        XmlSuite suite = new XmlSuite();
        suite.setName("TestCasesSuite");
        XmlTest test = new XmlTest(suite);
        test.setName("TestCasesTest");
        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass(testCaseClass));
        test.setXmlClasses(classes);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
    }

    /**
     * 生成testng.xml
     * @return
     */
    public Boolean CreateTestNGXML(String testcasepackage, String outputpath) {
        String prefix = "Test";
        String classesDir = outputpath;
        XmlSuite suite = new XmlSuite();
        XmlTest xmlTest = new XmlTest(suite);
        List<XmlClass> xmlClasses = new ArrayList<>();

        // testng.xml全名
        suite.setFileName(prefix + "_testng.xml");
        // 不设置这个suite.toXml()会报NPT
        suite.setJunit(false);
        suite.setName(prefix);
        xmlTest.setName(prefix);

        try {
            // 新建classloader实例
            URLClassLoader loader = URLClassLoader.newInstance(new URL[] {}, getClass().getClassLoader());
            // 测试用例class所在目录加到classloader的classpath中
            addPath(loader, new File(classesDir).toURI().toURL());

            // 循环遍历所有的class文件
            Collection<File> classFiles = FileUtils.listFiles(new File(classesDir), new WildcardFileFilter("*.class"),
                    TrueFileFilter.INSTANCE);
            for (File f : classFiles) {
                // 包含$说明为内部类
                if (f.getName().contains("$")) {
                    continue;
                }
                XmlClass xmlClass = new XmlClass();
                List<XmlInclude> includes = new ArrayList<>();

                // 只要class名称不要后缀
                String clazzName = testcasepackage + f.getName().replace(".class", "");
                xmlClass.setName(clazzName);

                Class<?> clazz = Class.forName(clazzName, false, loader);
                // 遍历所有方法，找到包含@Test标注的方法
                for (Method m : clazz.getMethods()) {
                    if (hasTestAnnotation(m)) {
                        XmlInclude xmlInclude = new XmlInclude(m.getName());
                        includes.add(xmlInclude);
                    }
                }
                xmlClass.setIncludedMethods(includes);
                xmlClasses.add(xmlClass);
            }

            xmlTest.setXmlClasses(xmlClasses);

            // 保存到文件系统中
//            FileUtils.write(new File(classesDir + "/" + suite.getFileName()), suite.toXml(), Charset.defaultCharset());
            FileUtils.write(new File("./" + suite.getFileName()), suite.toXml(), Charset.defaultCharset());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断当前方法是否包含@Test标注
     *
     * @param m
     * @return
     */
    private Boolean hasTestAnnotation(Method m) {
        List<Annotation> annotations = Arrays.asList(m.getAnnotations());
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals(Test.class.getName())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * addURL为protected方法，需要借助于reflect包才能实现动态添加class到classpath中
     *
     * @param urlClassLoader
     * @param url
     * @throws Exception
     */
    public static void addPath(ClassLoader urlClassLoader, URL url) throws Exception {
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[] { url });
    }
}
