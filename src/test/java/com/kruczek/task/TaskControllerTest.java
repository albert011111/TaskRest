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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskServiceImpl taskService;
    private List<Task> tasks;

    @Before
    public void setup() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2017-01-17");

        Task t1 = new Task();
        t1.setId(1L);
        t1.setName("Odkurzanie");
        t1.setDescription("Robic to czesto");
        t1.setCreateDate(date);

        tasks = new ArrayList<>(Collections.singletonList(t1));
    }

    @WithMockUser(username = "mockUser", roles = {ADMIN, USER})
    @Test
    public void getTasksTest() throws Exception {
        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json("[" +
                        "{'id':1," +
                        "'name':'Odkurzanie'," +
                        "'createDate':'2017-01-16T23:00:00.000+0000'," +
                        "'executeDate':null," +
                        "'description':'Robic to czesto'" +
                        "}]"));

        verify(taskService, times(1)).getAll();
        verifyNoMoreInteractions(taskService);
    }
}
