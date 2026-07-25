package org.example.learn.spring.framework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

import java.io.File;

/**
 * 输出cglib生成的代理类
 */
public class Ch002ConfigurationClassProxyCglibDumpTest{

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        System.out.println("workingDir = " + workingDir);

        String cglibDumpDir = workingDir + "/cglib-dump";

        if (new File(cglibDumpDir).exists()) {
            new File(cglibDumpDir).delete();
        }

        // 设置系统属性,这样cglib可以将生成的代理类的class文件输出到指定目录
        System.setProperty("cglib.debugLocation", cglibDumpDir);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setEnvironment(new StandardEnvironment());
        applicationContext.registerBean(AppConfig.class);
        applicationContext.refresh();

        applicationContext.close();

    }
}