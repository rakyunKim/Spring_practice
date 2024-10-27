package kr.ccfc.service.impl;

import cn.hutool.core.util.BooleanUtil;
import kr.ccfc.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private StringRedisTemplate strRedisTemplate;

    @Override
    public boolean logOut(String token) {
        Boolean delete = strRedisTemplate.delete(token);
        return BooleanUtil.isTrue(delete);
    }
}
