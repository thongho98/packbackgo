package own.thongho.web.packbackgo.services.Impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import own.thongho.web.packbackgo.bean.Comment;
import own.thongho.web.packbackgo.bean.Post;
import own.thongho.web.packbackgo.bean.User;
import own.thongho.web.packbackgo.dto.CommentRequest;
import own.thongho.web.packbackgo.services.PostService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private RestTemplate restTemplate;

    private String getAllPost = "http://api-packbackgo.herokuapp.com/post/list/";
    private String getPostById = "http://api-packbackgo.herokuapp.com/post/";
    private String createPost = "http://api-packbackgo.herokuapp.com/post/";
    private String getAllComment = "https://api-packbackgo.herokuapp.com/post/comments/?post_id=";
    private String createComment = "https://api-packbackgo.herokuapp.com/post/comments/";
    private String search = "https://api-packbackgo.herokuapp.com/post/search/?q=";
    @Override
    public List<Post> getAllPost() throws IOException {
        String result = restTemplate.getForObject(getAllPost, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Post> listPost = mapper.readValue(result, new TypeReference<List<Post>>(){});
        return listPost;
    }

    @Override
    public Post getPostById(long id) {
        Post result = restTemplate.getForObject(getPostById+"/"+id+"/", Post.class);
        return result;
    }

    @Override
    public Post createPost(Post post, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Token "+token);
        // Dữ liệu đính kèm theo yêu cầu.
        HttpEntity<Post> requestBody = new HttpEntity<>(post, headers);
        return restTemplate.postForObject(createPost, requestBody, Post.class);
    }

    //Cần chỉnh sửa
    @Override
    @JsonProperty("comments")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public List<Post> getAllComment(long postId) throws IOException {
        String result = restTemplate.getForObject(getAllComment+postId, String.class);
        System.out.println(result);
        ObjectMapper mapper = new ObjectMapper();
        List<Post> listComment = mapper.readValue(result, new TypeReference<List<Post>>(){});
        System.out.println(listComment);
        return listComment;
    }

    @Override
    public List<Post> search(String q, String on) throws IOException{
        String result = restTemplate.getForObject(search+q+"&on="+on, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Post> listComment = mapper.readValue(result, new TypeReference<List<Post>>(){});
        return listComment;
    }

    @Override
    public void createComment(CommentRequest comment, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Token "+token);
        // Dữ liệu đính kèm theo yêu cầu.
        HttpEntity<CommentRequest> requestBody = new HttpEntity<>(comment, headers);
        restTemplate.postForObject(createComment, requestBody, CommentRequest.class);
    }
}
