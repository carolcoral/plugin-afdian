package run.halo.afdian.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestUtils {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * post请求，requestBody方式传参
     *
     * @param object 请求参数
     * @param url 请求地址
     * @param cls 返回报文转换对象类型
     * @param <T> 返回报文类型
     * @return 返回报文
     */
    public <T> T post(Object object, String url, Class<T> cls){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(object, url, cls, headers);
    }

    /**
     * post请求，requestParam方式传参
     *
     * @param requestParams 请求参数
     * @param url 请求地址
     * @param cls 返回报文转换对象类型
     * @param <T> 返回报文类型
     * @return 返回报文
     */
    public <T> T post(MultiValueMap<String, Object> requestParams, String url, Class<T> cls){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return post(requestParams, url, cls, headers);
    }

    /**
     * post请求，requestBody方式传参
     *
     * @param object 请求参数
     * @param url 请求地址
     * @param cls 返回报文转换对象类型
     * @param headers 请求头
     * @param <T> 返回报文类型
     * @return 返回报文
     */
    public <T> T post(Object object, String url, Class<T> cls, HttpHeaders headers){
        HttpEntity<Object> requestEntity  = new HttpEntity<>(object, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestEntity, cls);
        return responseEntity.getBody();
    }

    /**
     * post请求，requestParam方式传参
     *
     * @param requestParams 请求参数
     * @param url 请求地址
     * @param cls 返回报文转换对象类型
     * @param headers 请求头
     * @param <T> 返回报文类型
     * @return 返回报文
     */
    public <T> T post(MultiValueMap<String, Object> requestParams, String url, Class<T> cls, HttpHeaders headers){
        HttpEntity<MultiValueMap<String, Object>> requestEntity  = new HttpEntity<>(requestParams, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestEntity, cls);
        return responseEntity.getBody();
    }


}
