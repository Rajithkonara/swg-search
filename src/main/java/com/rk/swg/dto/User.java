package com.rk.swg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {

    private String _id;
    private String url;
    private String external_id;
    private String name;
    private String alias;
    private String created_at;
    private Boolean active;
    private Boolean verified;
    private Boolean shared;
    private String locale;
    private String timezone;
    private String last_login_at;
    private String email;
    private String phone;
    private String signature;
    private String organization_id;
    private String[] tags;
    private Boolean supspended;
    private String role;
}
