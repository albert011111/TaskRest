package com.kruczek.task;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestContext.class, TaskRepository.class, TaskController.class})
@WebAppConfiguration
public class TaskControllerTest {


}

