package com.example.springbootmvc.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Request")
public class Request {

    @Column(name = "user_id")
    private int userid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int request_id;

    @Column(name = "request_type")
    private String request_type;

    @Column(name = "request_body")
    private String request_body;

    //TODO check date type
    @Column(name = "created_at")
    private Date created_at;

    //TODO check cascade type
    @ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "Request",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RequestResults requestResults;

    public Request() {
    }

    public Request(int userid, int request_id, String request_type, String request_body, Date created_at, User user, RequestResults requestResults) {
        this.userid = userid;
        this.request_id = request_id;
        this.request_type = request_type;
        this.request_body = request_body;
        this.created_at = created_at;
        this.user = user;
        this.requestResults = requestResults;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getRequest_body() {
        return request_body;
    }

    public void setRequest_body(String request_body) {
        this.request_body = request_body;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Request{" +
                "userid=" + userid +
                ", request_id=" + request_id +
                ", request_type='" + request_type + '\'' +
                ", request_body='" + request_body + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
