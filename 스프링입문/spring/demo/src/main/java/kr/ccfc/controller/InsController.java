package kr.ccfc.controller;

import kr.ccfc.constants.Constants;
import kr.ccfc.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/ins")
@Slf4j
public class InsController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/media")
    public R<Object> getInsMedia() {
        Set<String> keys = redisTemplate.keys("INS:MEDIAS:*");
        if (CollectionUtils.isEmpty(keys)) {
            return R.ok();
        }
        List<Object> values = redisTemplate.opsForValue()
                .multiGet(keys);
        return R.ok(values);
    }

    // interfo 20230227
    // InsTokenRefreshTask를 실행하기 위해 코드 가져옴.
    @GetMapping("/get_media")
    public void getMedia() {
        try {
            String longInsToken;
            try {
                longInsToken = (String) redisTemplate.opsForValue().get("INS:LONG:TOKEN");
                if (StringUtils.isEmpty(longInsToken) || longInsToken.contains("null")) {
                    throw new IllegalArgumentException("ins token error");
                }
            } catch (Exception e) {
            	e.printStackTrace();
                redisTemplate.opsForValue().set("INS:LONG:TOKEN", Constants.CURRENT_TOKEN);
                longInsToken = Constants.CURRENT_TOKEN;
            }
            if (StringUtils.isEmpty(longInsToken)) {
                longInsToken = Constants.CURRENT_TOKEN;
            }
            String mediaUrl = UrlBuilder.of(Constants.MEDIA_LIST_URL)
                    .addPath(Constants.USER_ID)
                    .addPath("media")
                    .addQuery("access_token", longInsToken)
                    .build();
            System.out.println("media url : " + mediaUrl);
            String mediaIds = HttpRequest.get(mediaUrl)
                    .execute()
                    .body();
            System.out.println("media data : " + mediaIds);
            JSONObject mediasJson = JSONObject.parseObject(mediaIds);
            // JSONObject paging = mediasJson.getJSONObject("paging");
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
        	e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        System.out.println("OK");
    }
}


