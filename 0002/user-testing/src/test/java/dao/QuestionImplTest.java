package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.dao.QuestionDaoImpl;
import ru.otus.dto.QuestionItem;
import java.util.List;

public class QuestionImplTest {

    @Test
    public void testWork(){


        QuestionDaoImpl questionImpl = new QuestionDaoImpl("data.csv");

        Assertions.assertNotNull(questionImpl);

        List<QuestionItem> QuestionItems = questionImpl.getQuestionItems();

        Assertions.assertEquals(QuestionItems.size(),2);
    }
}
