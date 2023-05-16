package com.example.springbootweb.Config;
import com.example.springbootweb.AInterceptor.JwtInterceptor;
import com.example.springbootweb.AInterceptor.PermissionsInterceptor;
import com.example.springbootweb.Constant.Constant;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Resource
    private PermissionsInterceptor permissionsInterceptor;
    @Resource
    private JwtInterceptor jwtInterceptor;
    /**
     * 图片保存路径，自动从yml文件中获取数据
     * 示例： E:/images/
     */
//    @Value("${file-save-path}")
//    private final String fileSavePath = "D:/MyProgrammingLanguagesWorkspace/JAVAEE/SSM-WEB/target/SSM-WEB-1.0-SNAPSHOT/static/img/";
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，要声明拦截器对象和要拦截的请求
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**/**") //所有路径都被拦截
                .excludePathPatterns("/login/**") // 排除用户登录请求
                .excludePathPatterns("/register/**") // 排除用户注册请求
                .excludePathPatterns("/sys_login/**") // 排除万和用户登录请求
                .excludePathPatterns("/sys_register/**") // 排除万和用户注册请求
                .excludePathPatterns("/listings/**")//房源
                .excludePathPatterns("/carousels/**")//走马灯
                .excludePathPatterns("/kaptcha/**")//验证码
                .excludePathPatterns("/")
//                .excludePathPatterns("/loginOut/**")
                .excludePathPatterns("/static/img/**/**/**")//图片
                .excludePathPatterns("/images/**/**");
        registry.addInterceptor(permissionsInterceptor)
                .addPathPatterns("/**/**")
                .excludePathPatterns("/static/img/**/**/**")//图片
                .excludePathPatterns("/kaptcha/**")//验证码
                .excludePathPatterns("/images/**/**");
        System.out.println("权限验证实现类的配置");


        super.addInterceptors(registry);
        System.out.println("放行1:\t"+super.toString());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + Constant.FILES_ROOT_PATH);

        super.addResourceHandlers(registry);
        System.out.println("图片请求");
        System.out.println("放行2:\t"+super.getReturnValueHandlers().toString());
    }
}
