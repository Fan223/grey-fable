package grey.fable.vocabulary.service;

import grey.fable.vocabulary.pojo.entity.VocabularyPosDO;
import grey.fable.vocabulary.pojo.query.VocabularyPosQuery;

import java.util.List;

/**
 * 词汇词性接口.
 *
 * @author GreyFable
 * @since 2024/9/18 11:58
 */
public interface VocabularyPosService {

    List<VocabularyPosDO> listVocabularyPos(VocabularyPosQuery vocabularyPosQuery);

    Integer saveVocabularyPos(List<VocabularyPosDO> vocabularyPosDos);

    Integer updateVocabularyPos(List<VocabularyPosDO> vocabularyPosDos);

    Integer deleteVocabularyPos(List<String> wordIds, List<String> ids);
}
