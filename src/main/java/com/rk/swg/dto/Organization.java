package com.rk.swg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Organization {

    private String _id;
    private String url;
    private String external_id;
    private String name;
    private String[] domain_names;
    private String created_at;
    private String details;
    private String shared_tickets;
    private String[] tags;

}
