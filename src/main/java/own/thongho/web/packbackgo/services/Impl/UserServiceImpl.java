package own.thongho.web.packbackgo.services.Impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import own.thongho.web.packbackgo.bean.User;
import own.thongho.web.packbackgo.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    private String resource = "http://api-packbackgo.herokuapp.com/users/create-user/";
    private String login = "http://api-packbackgo.herokuapp.com/login/";

    @Override
    public User createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Dữ liệu đính kèm theo yêu cầu.
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(resource, requestBody, User.class);
    }

    @Override
    public String login(String username, String password) {
        String token ="";
        //Request header
        HttpHeaders headers = new HttpHeaders();

        //Request Body
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("username", username);
        parametersMap.add("password", password);

        // Dữ liệu đính kèm theo yêu cầu.
        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<>(parametersMap,headers);

        ResponseEntity<String> response = restTemplate.exchange(login, HttpMethod.POST, requestBody, String.class);
        String reponseBody = response.getBody();

        JsonObject jsonObject = new JsonParser().parse(reponseBody).getAsJsonObject();
        token = jsonObject.get("api_token").getAsString();

        return token;
    }
}
