package com.ahirajustice.customersupport.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "messages")
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Lob
    @Column(nullable = false)
    private String body;

}
