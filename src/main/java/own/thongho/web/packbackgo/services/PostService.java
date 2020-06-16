package own.thongho.web.packbackgo.services;

import own.thongho.web.packbackgo.bean.Comment;
import own.thongho.web.packbackgo.bean.Post;
import own.thongho.web.packbackgo.dto.CommentRequest;

import java.io.IOException;
import java.util.List;

public interface PostService {
    public List<Post> getAllPost() throws IOException;
    public Post getPostById(long id);
    public Post createPost(Post post, String token);
    public void createComment(CommentRequest comment, String token);
    public List<Post> getAllComment(long postId) throws IOException;
    public List<Post> search(String q, String on) throws IOException;
}
