package com.ig.banking_system.base.model;

import com.ig.banking_system.model.previlleges.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@RequiredArgsConstructor
public class BaseEntity {
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
    @Column(name = "version")
    @Version
    private int version;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private Users createdBy;
	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private Users updatedBy;

    @PrePersist
    public void onInsert() {
        this.createdDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedDate = new Date();
    }
}
