package com.kruczek.task;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TaskServiceImpl.class})
public class TaskServiceImplTest {

    @Autowired
    private TaskServiceImpl taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Before
    public void setupData() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Bieganie");
        task.setDescription("Opis zadania 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Sprzatanie");
        task2.setDescription("Opis zadania 2");

        List<Task> tasks = Arrays.asList(task, task2);

        when(taskRepository.findAll())
                .thenReturn(tasks);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        //delete test
//        Mockito.doReturn(Collections.singletonList(task2)).when(taskRepository).delete(task);

    }

    @Test
    public void whenGetAll_thenListSizeShouldBe2() {
        List<Task> allTasks = taskService.getAll();
        Assertions.assertThat(allTasks.size()).isEqualTo(2);
    }

    @Test
    public void whenGetAll_thenNameAt0ShouldBeBieganie() {
        List<Task> allTasks = taskService.getAll();
        Assertions.assertThat(allTasks.get(0).getName()).isEqualTo("Bieganie");
    }

    @Test
    public void whenGetAll_thenIdAt1ShouldBe2() {
        List<Task> allTasks = taskService.getAll();
        Assertions.assertThat(allTasks.get(1).getId()).isEqualTo(2);
    }

    @Test
    public void whenId1_thenGetByIdShouldBeFound() {
        long id = 1L;
        Optional<Task> task = taskService.getById(id);
        Assertions.assertThat(task.isPresent()).isEqualTo(true);
    }

    @Test
    public void whenId999_thenGetByIdShouldBeEmptyOptional() {
        long id = 999L;
        Optional<Task> task = taskService.getById(id);
        Assertions.assertThat(task.isPresent()).isEqualTo(false);
    }

/*    @Test
    public void whenTaskDelete_thenListSizeShouldBe1() {
        Assertions.assertThat(taskService.delete(tasks.get(0)).size()).isEqualTo(1);
    }*/


    @Test(expected = NullPointerException.class)
    public void whenIdNull_thenGetByIdShouldThrowNullPointerExc() {
        Long id = null;
        taskService.getById(id);
    }
}
