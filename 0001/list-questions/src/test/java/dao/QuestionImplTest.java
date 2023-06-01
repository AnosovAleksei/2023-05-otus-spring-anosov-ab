package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.dao.LoadDataDao;
import ru.otus.dao.QuestionImpl;
import ru.otus.dto.QuestionItem;

import java.util.ArrayList;
import java.util.List;

public class QuestionImplTest {
    public class LoadDataImpl implements LoadDataDao {

        @Override
        public List<String[]> getData(){
            return new ArrayList<>(){{
                add(new String[]{"2+1","3","5"});
                add(new String[]{"2+2","4","5"});
            }};
        }
    }
    @Test
    public void testWork(){

        LoadDataImpl loadDataImpl = new LoadDataImpl();
        QuestionImpl questionImpl = new QuestionImpl(loadDataImpl);

        Assertions.assertNotNull(questionImpl);

        List<QuestionItem> QuestionItems = questionImpl.getQuestionItems();

        Assertions.assertEquals(QuestionItems.size(),2);
    }
}
