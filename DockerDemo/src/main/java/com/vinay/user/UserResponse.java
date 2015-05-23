package com.vinay.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vinays
 *
 */
@XmlRootElement(name = "Users")
@XmlAccessorType (XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends ResourceSupport implements Serializable{
    private static final long serialVersionUID = -1153674828263951969L;
   
    @XmlElement(name = "User")
    private List<User> users = new ArrayList<User>();
    
    private String content;
    
    public UserResponse() { }
    
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    @JsonCreator
    public UserResponse(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    
}
