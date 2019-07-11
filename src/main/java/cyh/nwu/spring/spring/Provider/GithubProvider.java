package cyh.nwu.spring.spring.Provider;

import com.alibaba.fastjson.JSON;
import cyh.nwu.spring.spring.DTO.Access_Token_DTO;
import cyh.nwu.spring.spring.DTO.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 提供github的登录服务
 */
//初始化到spring上下文，IOC不需要再进行实例化
@Component
public class GithubProvider {
 public String getAccessToken(Access_Token_DTO accesstokendto){
     MediaType mediaType = MediaType.get("application/json; charset=utf-8");
     OkHttpClient client = new OkHttpClient();
     RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokendto));
         Request request = new Request.Builder()
                 .url("https://github.com/login/oauth/access_token")
                 .post(body)
                 .build();
         try (Response response = client.newCall(request).execute()) {
              String ret = response.body().string();
             String token = ret.split("&")[0].split("=")[1];

             return token;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
 }

 public GithubUser getUser(String accessToken){
     OkHttpClient client = new OkHttpClient();
     Request request = new Request.Builder()
             .url("https://api.github.com/user?access_token="+accessToken)
             .build();
     try (Response response = client.newCall(request).execute()) {
         String string = response.body().string();
         GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
         return githubUser;
     } catch (IOException e) {
         e.printStackTrace();
     }
     return null;

 }

}
