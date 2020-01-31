package com.echokinetic.CodeFellowship.Models;

import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>
{
    public List<Post> findByUser(ApplicationUser user);

}
