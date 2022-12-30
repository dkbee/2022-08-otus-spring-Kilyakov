package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.kilyakov.config.property.StudentTestingProperty;
import ru.otus.spring.kilyakov.dao.impl.CsvResourceDaoImpl;
import ru.otus.spring.kilyakov.service.impl.ConsoleServiceImpl;
import ru.otus.spring.kilyakov.service.impl.StudentTestingServiceImpl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StudentTestingServiceTest {

    @Autowired
    CsvResourceDaoImpl csvResourceDao;
    @Autowired
    StudentTestingProperty studentTestingProperty;
    @Mock
    ConsoleServiceImpl consoleService;

    @Test
    public void executeTestTest() throws Exception {
        StudentTestingServiceImpl service = new StudentTestingServiceImpl(csvResourceDao, studentTestingProperty,
                consoleService);
        service.executeTest();
        verify(consoleService, times(5)).readDigit(anyInt(), anyInt());
    }
}
