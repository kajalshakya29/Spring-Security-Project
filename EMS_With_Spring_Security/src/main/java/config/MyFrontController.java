package config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
public class MyFrontController extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SecurityConfig.class, DatabaseConfig.class, MvcConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // tempDir, maxFileSize (5MB), maxRequestSize (10MB), fileSizeThreshold (0)
        long maxFileSize = 5 * 1024 * 1024; // 5 MB
        long maxRequestSize = 10 * 1024 * 1024; // 10 MB (Request size should be larger than file size)

        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                "/tmp/employee_uploads",
                maxFileSize,
                maxRequestSize,
                0
        );

        registration.setMultipartConfig(multipartConfig);
    }
}