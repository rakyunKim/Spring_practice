package kr.ccfc.constants;


/**
 * 常用的常量
 */
public interface Constants {
    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

    /**
     * 成功标记
     */
    Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    Integer FAIL = 500;


    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    long CAPTCHA_EXPIRATION = 2;

    /**
     * 超级管理员 角色编码
     */
    String ADMIN_ROLE_CODE = "ROLE_ADMIN";

    /**
     * 内置的token
     */
    // String INSIDE_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjA3MTYzNzgzLCJqdGkiOiI1Nzk0ZTY0MC01NDMxLTRkNDItOWZhNi04MWFkN2M4ODgzNjgiLCJjbGllbnRfaWQiOiJpbnNpZGUtYXBwIn0.KMm8fDnvXJ0o8RaCGeH8cG_LtE6Tt-hUVNGhlMbZEPBJjGnWUFc_2fzgLa51TAxpqYpswtT8_Dn8owakoAz6gAUC8XRl0sEteDW5_KZdFGxmWMObsL4EKOZtkpX1s-nJad6m_v4jLmc6RKipaqzXO_jC3yyPH5UvRle82-fkT7lLXsNfKLxfvrwjSpk-HlJMzCIi3KXJvqQYbakU-AukjpNuaC0-aG6Cvbg9t3PfMMzzKE4Q8hpSRN5yu_ySr1m246F-lRx9OCyhT20CQBX0lKRGnnM-GEka4dzw52hyZHvopTetdyNyGGkoWTi0X1GCirmS40gvQY-OjyovUhGWLA";


    // String INS_URL = "https://graph.instagram.com?fields=id,media_type,media_url,username,timestamp";

    /**
     * 短期口令
     */
    // String SHORT_TOKEN = "https://api.instagram.com/oauth/authorize";

    /**
     * 应用编号
     */
    // String CLIENT_ID  = "430338168420856";
    String CLIENT_ID  = "563521274692191";

    String CLIENT_SECRET = "050efa7251c81f66b7fc35f837fd72bb";

    String REFRESH_TOKEN = "https://graph.instagram.com/refresh_access_token?grant_type=ig_refresh_token";

    // String CURRENT_TOKEN = "IGQVJXUTZAwcXBKMS1fZA3lEYUxhdm5zR29wcGdXa3J5anhWd1ZAhajFxUUtIY3ZAId2ZANNlQtRlZA4dGxkM3FZAQmdXdUdyZA0NaekhoRTN0WXFQRjFuazlhR3FGbTdnNjJXYnVFbzVIdXlB";
    // String CURRENT_TOKEN = "IGQVJWeENWMXc4SHJOU09JRDdCZA09mQlM2dk1jck8wVEVaQXd6MHpMVUpBcnNzN29LcXZAsOC1TaHc3Mi1WdHB0MXpoMTZAxbVdZAaGM2X0N4R2xtaTd6TmxQeXo0LVZA2M21MYWNZASkxXcjREdEgySC1fcwZDZD";
    String CURRENT_TOKEN = "IGQVJWWjJVeXFkOFhwTDh6aTdGSUlrLWZA4bFhiS1otaEZAaS1RBR3hoVzJ6OXI3TjlLamRUV0lPTDRuVU9BSjZAfdGQ2V0hHbUF3cGhEUFZAGWktDMGhjRzJxZAk9lY1k4RjhXUVQtdXJn";

    String MEDIA_LIST_URL = "https://graph.instagram.com";

    String MEDIA_URL = "https://graph.instagram.com";

    String USER_ID = "17841410757621843";
}
