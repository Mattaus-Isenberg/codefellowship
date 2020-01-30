package com.echokinetic.CodeFellowship.Controllers;



import com.echokinetic.CodeFellowship.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public RedirectView createNewApplicationUser(String username, String password, String first_Name, String last_Name, Date date_Of_Birth, String bio)
    {
        System.out.println("You are adding a user");
        // make the user AND salt and hash the password
        // this does the salting and hashing for you
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), first_Name, last_Name, date_Of_Birth, bio);

        // save the user to db
        applicationUserRepository.save(newUser);

        // send them back home
        return new RedirectView("login");
    }


    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/myprofile")
    public String getProfile(Principal p, Model m)
    {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findByUser(applicationUser);
        m.addAttribute("posts", posts);
        m.addAttribute("loggedUser", applicationUser);
        m.addAttribute("username", p.getName());
        m.addAttribute("user", p);
        return "myprofile";
    }

    @PostMapping("/login")
    public RedirectView profilePage(Principal p, Model m)
    {
        ApplicationUser applicationUser =  applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("loggedUser", applicationUser);
        m.addAttribute("user", p);
        return new RedirectView("myprofile");
    }


    @GetMapping("/users/{id}")
    public String showUserInfo()
    {
        return "users";
    }

    @GetMapping("/posts")
    public String getPosts(Model m)
    {
        List<Post> posts = postRepository.findAll();
        m.addAttribute("posts", posts);
        return "tracks";
    }

    @PostMapping("/myprofile/posts")
    public RedirectView addPost(String body, Principal p)
    {
        ApplicationUser user =  applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, user);
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }
}
