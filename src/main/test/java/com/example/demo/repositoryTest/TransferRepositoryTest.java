package com.example.demo.repositoryTest;

import com.example.demo.controller.TransferController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TransferRepositoryTest {

    @Autowired
    private TransferController transferController;

    @Test
    public void TransferRepositoryTest() throws Exception {
        assertThat(transferController).isNotNull();
    }
}
