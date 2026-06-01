package com.testframework.model.invoker;
import org.testng.ITestResult;

import java.lang.annotation.Annotation;


public class InvokerApplicationTestResultInfoService {

    /**
     * Get Annotation form  test method
     *
     * @param testResult      test result
     * @param annotationClass annotation class
     * @param <T>             class annotation
     * @return annotation info
     */
    public static <T extends Annotation> T getAnnotationInfo(ITestResult testResult, Class<T> annotationClass) {
        return testResult.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(annotationClass);
    }

    /**
     * Get annotation value from test method using reflection to call value() method
     *
     * @param testResult      test result
     * @param annotationClass annotation class
     * @param <T>             annotation type
     * @return annotation value or "N/A" if not present or value() method not found
     */
    public static <T extends Annotation> String getAnnotationValue(ITestResult testResult, Class<T> annotationClass) {
        T annotation = getAnnotationInfo(testResult, annotationClass);
        if (annotation == null) {
            return "No " + annotationClass.getSimpleName();
        }

        try {
            return String.valueOf(annotationClass.getMethod("value").invoke(annotation));
        } catch (Exception e) {
            return annotation.toString();
        }
    }
}
