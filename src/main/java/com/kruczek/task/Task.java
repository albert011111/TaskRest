package com.kruczek.task;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;


@Entity(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createDate", "executeDate"}, allowGetters = true)
public class Task {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false)
	@NotNull
	@Length(min = 3, message = "Task name must have at least 3 signs")
	private String name;

	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	@CreatedDate
	@Temporal(value = TemporalType.DATE)
	private Date createDate;

	@Column(name = "EXECUTE_DATE", columnDefinition = "date default null")
	@Temporal(value = TemporalType.DATE)
	private Date executeDate = null;

	@Column(name = "DESCRIPTION")
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

	@Nullable
	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(@Nullable Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
