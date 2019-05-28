package com.kruczek.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kruczek.auth.model.user.User;

@Repository
interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findAllByUser(User user);
}
