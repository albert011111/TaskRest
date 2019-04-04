package com.kruczek.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.kruczek.utils.Commons.ADMIN;
import static com.kruczek.utils.Commons.USER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
    private static final String TASK_JASON = "{'id':2," +
            "'name':'Odkurzanie'," +
            "'createDate':'2017-01-16T23:00:00.000+0000'," +
            "'executeDate':null," +
            "'description':'Robic to czesto'" +
            "}";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskServiceImpl taskService;

    private List<Task> tasks;
    private Task taskById;

    @Before
    public void setup() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2017-01-17");

        taskById = new Task();
        taskById.setId(2L);
        taskById.setName("Odkurzanie");
        taskById.setDescription("Robic to czesto");
        taskById.setCreateDate(date);

        tasks = new ArrayList<>(Collections.singletonList(taskById));
    }

    @WithMockUser(roles = {USER, ADMIN})
    @Test
    public void getTasksWithRequiredRolesTest() throws Exception {
        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json("[" + TASK_JASON + "]"));

        verify(taskService, times(1)).getAll();
        verifyNoMoreInteractions(taskService);

        //TODO przeanalizować problem dotyczacy roles{...}
    }

    @WithMockUser(roles = {"SONGO"})
    @Test
    public void getTasksWithWrongRolesTest() throws Exception {
        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isForbidden());

        verify(taskService, times(0)).getAll();
        verifyNoMoreInteractions(taskService);
    }

    @WithMockUser(roles = {USER, ADMIN})
    @Test
    public void getTasksByIdTest() throws Exception {
        when(taskService.getById(2L)).thenReturn(Optional.ofNullable(taskById));

        mockMvc.perform(get("/api/tasks/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(TASK_JASON));

        verify(taskService, times(1)).getById(2L);
        verifyNoMoreInteractions(taskService);
    }
}