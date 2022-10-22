import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;
import ru.otus.spring.kilyakov.config.property.StudentTestingProperty;
import ru.otus.spring.kilyakov.dao.impl.CsvResourceDaoImpl;
import ru.otus.spring.kilyakov.domain.Question;
import ru.otus.spring.kilyakov.service.impl.ConsoleServiceImpl;
import ru.otus.spring.kilyakov.service.impl.StudentTestingServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class StudentTestingServiceTest {

    @Mock
    CsvResourceDaoImpl csvResourceDao;
    @Mock
    StudentTestingProperty studentTestingProperty;
    @Mock
    ConsoleServiceImpl consoleService;
    @Mock
    MessageSource messageSource;

    private static List<Question> questions = new ArrayList<>();

    @BeforeEach
    public void beforeEach() throws Exception {
        questions.add(Question.builder().question("2 x 2 = ?")
                .answer1("4")
                .answer2("6")
                .answer3("2")
                .rightAnswer(1)
                .build());
    }

    @Test
    public void executeTestTest() throws Exception {
        doReturn(questions).when(csvResourceDao).readAllQuestions();
        doReturn("").when(studentTestingProperty).getFailureMessage();
        doReturn("").when(studentTestingProperty).getGoodMessage();
        doReturn("").when(studentTestingProperty).getExcellentMessage();
        doReturn("").when(studentTestingProperty).getSatisfactoryMessage();

        StudentTestingServiceImpl service = new StudentTestingServiceImpl(csvResourceDao, studentTestingProperty,
                consoleService);
        service.executeTest();
        verify(consoleService, times(1)).readDigit(anyInt(), anyInt());
    }
}
