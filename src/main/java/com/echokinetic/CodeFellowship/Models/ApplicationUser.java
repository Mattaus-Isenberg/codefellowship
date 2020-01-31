package com.echokinetic.CodeFellowship.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.net.URL;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @ManyToMany
    @JoinTable(
            name="friends",
            joinColumns = @JoinColumn(name="registered_user"),
            inverseJoinColumns = @JoinColumn(name="following")
    )
    private Set<ApplicationUser> following;

    @ManyToMany(mappedBy = "following")
    private Set<ApplicationUser> following_Me;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    String username;
    String password;

    String first_Name;
    String last_Name;
    Date date_Of_Birth;
    String bio;
    URL picture;

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }



    public ApplicationUser() {};

    public ApplicationUser(String username, String password, String first_Name, String last_Name, Date date_Of_Birth, String bio, URL picture)
    {
        this.username = username;
        this.password = password;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.date_Of_Birth = date_Of_Birth;
        this.bio = bio;
        this.picture = picture;
    }

    public List<Post> getPosts()
    {
        return posts;
    }

    public void setPosts(List<Post> posts)
    {
        this.posts = posts;
    }

    public void follow(ApplicationUser user)
    {
        following.add(user);
    }
    public long getId()
    {
        return id;
    }

    public Set<ApplicationUser> getFollowing() {
        return following;
    }

    public void setFollowing(Set<ApplicationUser> following) {
        this.following = following;
    }

    public Set<ApplicationUser> getFollowing_Me() {
        return following_Me;
    }

    public void setFollowing_Me(Set<ApplicationUser> following_Me) {
        this.following_Me = following_Me;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public Date getDate_Of_Birth() {
        return date_Of_Birth;
    }

    public void setDate_Of_Birth(Date date_Of_Birth) {
        this.date_Of_Birth = date_Of_Birth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
