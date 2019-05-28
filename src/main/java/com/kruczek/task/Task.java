package com.kruczek.task;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kruczek.auth.model.user.User;
import com.kruczek.calendar.day.Day;


@Entity(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
//@JsonIgnoreProperties(value = {"createDate", "executeDate"}, allowGetters = true)
public class Task {
	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(nullable = false)
	@NotNull
	@Length(min = 3, message = "Task name must have at least 3 signs")
	private String name;

	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	@Temporal(value = TemporalType.DATE)
	private Date createDate;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean finished;

	@Column(name = "EXECUTE_DATE", columnDefinition = "date default null")
	@Temporal(value = TemporalType.DATE)
	private Date executeDate = null;

	@Column
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@ManyToOne
	@JoinColumn(name = "day_id")
//	@JsonManagedReference
	@JsonIgnore
	private Day day;

	@Transient
	private String userName;

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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public String getUserName() {
		return userName;
	}
}
