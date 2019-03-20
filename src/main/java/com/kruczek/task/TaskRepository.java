package com.kruczek.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2018-09-24.
 */
@Repository
interface TaskRepository extends JpaRepository<Task, Long> {

}
