import org.junit.jupiter.api.Test;
import ru.otus.spring.kilyakov.config.property.StudentTestingProperty;
import ru.otus.spring.kilyakov.domain.Question;
import ru.otus.spring.kilyakov.dao.impl.CsvResourceDaoImpl;
import ru.otus.spring.kilyakov.service.impl.StudentTestingServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentTestingServiceTest {

    @Test
    public void readQuestionsFailureTest() throws Exception {
        CsvResourceDaoImpl csvResourceDao = mock(CsvResourceDaoImpl.class);
        StudentTestingProperty studentTestingProperty = new StudentTestingProperty();
        when(csvResourceDao.readAllQuestions()).thenThrow(new NullPointerException());

        StudentTestingServiceImpl service = new StudentTestingServiceImpl(csvResourceDao, studentTestingProperty);
        service.printAllQuestions();
        assertThrows(NullPointerException.class, csvResourceDao::readAllQuestions);

        List<Question> actual = service.getQuestions();
        assertNull(actual);
    }

    @Test
    public void readQuestionsTest() throws Exception {
        CsvResourceDaoImpl csvResourceDao = mock(CsvResourceDaoImpl.class);
        StudentTestingProperty studentTestingProperty = new StudentTestingProperty();

        List<Question> excepted = new ArrayList<>();
        excepted.add(Question.builder().question("2 x 2 = ?").build());
        when(csvResourceDao.readAllQuestions()).thenReturn(excepted);

        StudentTestingServiceImpl service = new StudentTestingServiceImpl(csvResourceDao, studentTestingProperty);
        service.printAllQuestions();
        verify(csvResourceDao, times(1)).readAllQuestions();

        List<Question> actual = service.getQuestions();
        assertEquals(excepted, actual);
    }
}
