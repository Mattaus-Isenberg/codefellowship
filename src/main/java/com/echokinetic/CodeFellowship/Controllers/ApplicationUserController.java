package com.echokinetic.CodeFellowship.Controllers;

import com.echokinetic.CodeFellowship.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URL;
import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public RedirectView createNewApplicationUser(String username, String password, String first_Name, String last_Name, Date date_Of_Birth, String bio, URL picture)
    {
        System.out.println("You are adding a user");
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), first_Name, last_Name, date_Of_Birth, bio, picture);
        applicationUserRepository.save(newUser);
        return new RedirectView("login");
    }


    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/myprofile")
    public String getProfile(Principal p, Model m)
    {
        if (p != null)
        {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findByUser(applicationUser);
        List<ApplicationUser> registeredUsers = applicationUserRepository.findAll();
        registeredUsers.remove(applicationUserRepository.findByUsername(p.getName()));
        m.addAttribute("registeredUsers", registeredUsers);
        m.addAttribute("posts", posts);
        m.addAttribute("loggedUser", applicationUser);
        m.addAttribute("username", p.getName());
        m.addAttribute("user", p);
        return "myprofile";
        }
        return "login";
    }

    @PostMapping("/login")
    public RedirectView profilePage(Principal p, Model m)
    {
        ApplicationUser applicationUser =  applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("loggedUser", applicationUser);
        m.addAttribute("user", p);
        return new RedirectView("myprofile");
    }

    @PostMapping("/myprofile/posts")
    public RedirectView addPost(String body, Principal p)
    {
        ApplicationUser user =  applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, user);
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/viewUser")
    public String displayRegisteredUserProfile(String username, Model m, Principal p)
    {
        if (p != null)
        {
            ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
            ApplicationUser user = applicationUserRepository.findByUsername(username);
            m.addAttribute("loggedInUser", loggedInUser);
            m.addAttribute("user", user);
            return "friend";
        }
        return "login";
    }

    @PostMapping("/follow")
    public RedirectView follow(String newFriend, Principal p)
    {
        if (p != null)
        {
            ApplicationUser new_Friend = applicationUserRepository.findByUsername(newFriend);
            ApplicationUser me = applicationUserRepository.findByUsername(p.getName());
            me.follow(new_Friend);
            applicationUserRepository.save(me);
        }
        return new RedirectView("/myprofile");
    }

    @GetMapping("/feed")
    public String viewFeed(Principal p, Model m)
    {
        if (p != null)
        {
            ApplicationUser me = applicationUserRepository.findByUsername(p.getName());
            Set<ApplicationUser> friends = me.getFollowing();
            m.addAttribute("friends", friends);
            m.addAttribute("username", p.getName());

            return "feed";
        }
        return "login";
    }

}
