package own.thongho.web.packbackgo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import own.thongho.web.packbackgo.bean.Comment;
import own.thongho.web.packbackgo.bean.Post;
import own.thongho.web.packbackgo.bean.User;
import own.thongho.web.packbackgo.dto.CommentRequest;
import own.thongho.web.packbackgo.dto.PostRequest;
import own.thongho.web.packbackgo.dto.SearchRequest;
import own.thongho.web.packbackgo.dto.UserLoginRequest;
import own.thongho.web.packbackgo.services.Impl.PostServiceImpl;
import own.thongho.web.packbackgo.services.Impl.UserServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/listing",method = RequestMethod.GET)
    public String listing(ModelMap model) throws IOException {
        List<Post> list = postService.getAllPost();
        model.addAttribute("listPost",list);
        model.addAttribute("newSearch", new SearchRequest());
        return "listing";
    }

    @RequestMapping(value = "/listing/",method = RequestMethod.GET)
    public String singleListing(ModelMap model, @RequestParam("id") long id) throws IOException {
        Post post = postService.getPostById(id);
        model.addAttribute("post",post);
        model.addAttribute("comment",new CommentRequest());
        model.addAttribute("newSearch", new SearchRequest());
        return "single-listing";
    }

    @RequestMapping(value = "/post",method = RequestMethod.GET)
    public String createPost(ModelMap model) {
        System.out.println(session.getAttribute("usersession"));
        String checkSession = (String) session.getAttribute("usersession");
        if(checkSession == null){
            return "redirect:/login";
        }
        model.addAttribute("newPost", new PostRequest());
        model.addAttribute("newSearch", new SearchRequest());
        return "newpost";
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String createNewPost(@Valid @ModelAttribute("newPost") Post post) {
        String checkSession = (String) session.getAttribute("usersession");
        postService.createPost(post, checkSession);
        return "redirect:/listing";
    }

    @RequestMapping(value = "/comment/",method = RequestMethod.POST)
    public String createComment(@RequestParam("id") long id, @ModelAttribute("newComment") CommentRequest comment, ModelMap model) {
        comment.setPost_id(String.valueOf(id));
        String checkSession = (String) session.getAttribute("usersession");
        postService.createComment(comment, checkSession);
        return "redirect:/listing/?id="+id;
    }
}
