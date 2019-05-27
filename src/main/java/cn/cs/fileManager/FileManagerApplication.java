package cn.cs.fileManager;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableWebSecurity
@SpringBootApplication
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
	}

    @Bean
    public HttpMessageConverters fastjsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return new HttpMessageConverters(fastConverter);

    }
    @Bean
    public MultipartConfigElement multipartConfigElement() { 
    	MultipartConfigFactory factory = new MultipartConfigFactory();
       //  单个数据大小
       factory.setMaxFileSize("10240000000KB");
       /// 总上传数据大小
       factory.setMaxRequestSize("10240000000KB");
       return factory.createMultipartConfig();
    }
    
    
}
