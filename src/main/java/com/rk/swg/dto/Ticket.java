package com.rk.swg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ticket {

    private String _id;
    private String url;
    private String external_id;
    private String created_at;
    private String type;
    private String subject;
    private String description;
    private String priority;
    private String status;
    private String submitter_id;
    private String assignee_id;
    private String organization_id;
    private String[] tags;
    private Boolean has_incidents;
    private String due_at;
    private String via;

}
