package own.thongho.web.packbackgo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import own.thongho.web.packbackgo.bean.Post;
import own.thongho.web.packbackgo.dto.SearchRequest;
import own.thongho.web.packbackgo.services.Impl.PostServiceImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostServiceImpl postService;

    @RequestMapping(value = {"","/","/index"}, method = RequestMethod.GET)
    public String index(ModelMap model) throws IOException {
        List<Post> list = postService.getAllPost();
        model.addAttribute("listPost",list);
        model.addAttribute("newSearch", new SearchRequest());
        return "index";
    }

    @RequestMapping("/contact")
    public String contact(ModelMap model) {
        model.addAttribute("newSearch", new SearchRequest());
        return "contact";
    }

    @RequestMapping("/single-listing")
    public String singleListing(ModelMap model) {
        model.addAttribute("newSearch", new SearchRequest());
        return "single-listing";
    }

    @RequestMapping("/thank-you")
    public String thank(ModelMap model) {
        model.addAttribute("newSearch", new SearchRequest());
        return "thank-you";
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(@Valid @ModelAttribute("newSearch") SearchRequest searchRequest, ModelMap model) throws IOException {
        model.addAttribute("newSearch", new SearchRequest());
        List<Post> list = postService.search(searchRequest.getQ(),searchRequest.getOn());
        model.addAttribute("listPost",list);
        return "listing";
    }
}
