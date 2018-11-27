package com.kruczek.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "executeDate"}, allowGetters = true)
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(value = TemporalType.DATE)
    private Date createDate;

    @Column(name = "EXECUTE_DATE")
    @LastModifiedDate
    @Temporal(value = TemporalType.DATE)
    private Date executeDate;

    @Column(name = "desc")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
