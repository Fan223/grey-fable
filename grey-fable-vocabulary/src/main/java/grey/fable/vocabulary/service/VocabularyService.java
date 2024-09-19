package grey.fable.vocabulary.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import grey.fable.vocabulary.pojo.entity.VocabularyDO;
import grey.fable.vocabulary.pojo.query.VocabularyQuery;

import java.util.List;

/**
 * 词汇接口.
 *
 * @author GreyFable
 * @since 2024/9/18 11:58
 */
public interface VocabularyService {

    VocabularyDO getVocabulary(VocabularyQuery vocabularyQuery);

    Page<VocabularyDO> listVocabularies(VocabularyQuery vocabularyQuery);

    Integer saveVocabulary(VocabularyDO vocabularyDO);

    Integer updateVocabulary(VocabularyDO vocabularyDO);

    Integer deleteVocabulary(List<String> ids);
}
