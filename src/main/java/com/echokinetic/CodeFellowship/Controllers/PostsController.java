package com.echokinetic.CodeFellowship.Controllers;

import com.echokinetic.CodeFellowship.Models.ApplicationUser;
import com.echokinetic.CodeFellowship.Models.ApplicationUserRepository;
import com.echokinetic.CodeFellowship.Models.Post;
import com.echokinetic.CodeFellowship.Models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

public class PostsController
{
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    public String getPostForm(Model m, Principal p) {
        m.addAttribute("user", p);
        return "post";
    }

    @PostMapping("/posts")
    public RedirectView createPost(String body, Principal p, Model m){
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, loggedInUser);
        postRepository.save(post);
        return new RedirectView("/users/" + loggedInUser.getId());
    }
}
