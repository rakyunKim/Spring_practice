package kr.ccfc.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "kr.ccfc.service.impl")
public class JetCacheConfig {
}
