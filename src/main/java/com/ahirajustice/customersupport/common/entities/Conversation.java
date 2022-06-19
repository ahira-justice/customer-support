package com.ahirajustice.customersupport.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "conversations")
public class Conversation extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private User agent;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User customer;

}
