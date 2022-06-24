package com.ahirajustice.customersupport.common.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "authorities")
public class Authority extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean isSystem;

    public Authority(String name) {
        this.name = name;
    }

    public Authority(String name, boolean isSystem) {
        this.name = name;
        this.isSystem = isSystem;
    }

}
