package kr.ccfc.task;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kr.ccfc.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class InsTokenRefreshTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 每月1日 凌晨两点执行
     * 刷新 ins 长期token
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void refreshToken() {
        String accessToken = (String) redisTemplate.opsForValue().get("INS:LONG:TOKEN");
        if (StringUtils.isEmpty(accessToken) || accessToken.contains("null")) {
            accessToken = getToken();
        }
        String refreshTokenUrl = UrlBuilder.of(Constants.REFRESH_TOKEN)
                .addQuery("access_token", accessToken)
                .build();
        String body = HttpRequest.get(refreshTokenUrl)
                .execute()
                .body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (!jsonObject.containsKey("access_token")) {
            log.error("ins 동기화 실패 :{}", jsonObject);
            return;
        }
        String newToken = jsonObject.getString("access_token");
        redisTemplate.opsForValue()
                .set("INS:LONG:TOKEN", newToken);
    }

    /**
     * 一天执行一次
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void getInsMedia() throws InterruptedException {
        try {
            String longInsToken;
            try {
                longInsToken = (String) redisTemplate.opsForValue().get("INS:LONG:TOKEN");
                if (StringUtils.isEmpty(longInsToken) || longInsToken.contains("null")) {
                    throw new IllegalArgumentException("ins token error");
                }
            } catch (Exception e) {
                redisTemplate.opsForValue().set("INS:LONG:TOKEN", getToken());
                longInsToken = getToken();
            }
            if (StringUtils.isEmpty(longInsToken)) {
                longInsToken = getToken();
            }
            String mediaUrl = UrlBuilder.of(Constants.MEDIA_LIST_URL)
                    .addPath(Constants.USER_ID)
                    .addPath("media")
                    .addQuery("access_token", longInsToken)
                    .build();
            String mediaIds = HttpRequest.get(mediaUrl)
                    .execute()
                    .body();
            JSONObject mediasJson = JSONObject.parseObject(mediaIds);
            //JSONObject paging = mediasJson.getJSONObject("paging");
            JSONArray data = mediasJson.getJSONArray("data");

            // interfo 20230227 - API 사용가능한 제한 수 막기위해 주석.
            /*while (paging != null && paging.containsKey("next")) {
                Thread.sleep(2000);
                String next = HttpRequest
                        .get(paging.getString("next"))
                        .execute().body();
                JSONObject jsonObject = JSONObject.parseObject(next);
                log.info("next:{}", jsonObject.toJSONString());
                JSONArray nextData = jsonObject.getJSONArray("data");
                if (nextData == null) {
                    break;
                }
                data.addAll(nextData);
                paging = jsonObject.getJSONObject("paging");
            }*/
            if (CollectionUtil.isNotEmpty(data)) {
                for (int i = 0; i < data.size(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    String mediaId = obj.getString("id");
                    String url = UrlBuilder.of(Constants.MEDIA_URL)
                            .addPath(mediaId + "")
                            .addQuery("fields", "id,media_type,media_url,username,timestamp,permalink")
                            .addQuery("access_token", longInsToken)
                            .build();
                    String body = HttpRequest.get(url)
                            .execute()
                            .body();
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    String id = jsonObject.getString("id");
                    log.info(" ins 데이터 가져오기：{}", jsonObject.toJSONString());
                    redisTemplate.opsForValue()
                            .set("INS:MEDIAS:" + id, jsonObject, 1, TimeUnit.DAYS);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getToken() {
        return Constants.CURRENT_TOKEN;
    }
}
