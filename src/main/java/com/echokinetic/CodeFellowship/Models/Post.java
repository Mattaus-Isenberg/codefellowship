package com.echokinetic.CodeFellowship.Models;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String body;
    Date created_At_TimeStamp;

    @ManyToOne
    ApplicationUser user;

    public Post(){}

    public Post(String body, Date created_At_TimeStamp)
    {
        this.body = body;
        this.created_At_TimeStamp = created_At_TimeStamp;
    }


    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated_At_TimeStamp() {
        return created_At_TimeStamp;
    }

    public void setCreated_At_TimeStamp(Date created_At_TimeStamp) {
        this.created_At_TimeStamp = created_At_TimeStamp;
    }
}
