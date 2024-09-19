package grey.fable.vocabulary.service.impl.impl;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import grey.fable.base.collection.CollectionUtils;
import grey.fable.base.text.StringUtils;
import grey.fable.vocabulary.dao.VocabularyPosDAO;
import grey.fable.vocabulary.pojo.entity.VocabularyPosDO;
import grey.fable.vocabulary.pojo.query.VocabularyPosQuery;
import grey.fable.vocabulary.service.VocabularyPosService;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 词汇词性接口实现类.
 *
 * @author GreyFable
 * @since 2024/9/18 16:26
 */
@Service
public class VocabularyPosServiceImpl implements VocabularyPosService {

    private final VocabularyPosDAO vocabularyPosDAO;
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public VocabularyPosServiceImpl(VocabularyPosDAO vocabularyPosDAO, SqlSessionFactory sqlSessionFactory) {
        this.vocabularyPosDAO = vocabularyPosDAO;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<VocabularyPosDO> listVocabularyPos(VocabularyPosQuery query) {
        LambdaQueryWrapper<VocabularyPosDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CollectionUtils.isNotEmpty(query.getWordIds()), VocabularyPosDO::getWordId, query.getWordIds())
                .like(StringUtils.isNotBlank(query.getTranslation()), VocabularyPosDO::getTranslation, query.getTranslation());
        return vocabularyPosDAO.selectList(queryWrapper);
    }

    @Override
    public Integer saveVocabularyPos(List<VocabularyPosDO> vocabularyPosDOS) {
        MybatisBatch<VocabularyPosDO> mybatisBatch = new MybatisBatch<>(sqlSessionFactory, vocabularyPosDOS);
        MybatisBatch.Method<VocabularyPosDO> method = new MybatisBatch.Method<>(VocabularyPosDAO.class);
        List<BatchResult> execute = mybatisBatch.execute(method.insert());
        return execute.size();
    }

    @Override
    public Integer updateVocabularyPos(List<VocabularyPosDO> vocabularyPosDos) {
        MybatisBatch<VocabularyPosDO> mybatisBatch = new MybatisBatch<>(sqlSessionFactory, vocabularyPosDos);
        MybatisBatch.Method<VocabularyPosDO> method = new MybatisBatch.Method<>(VocabularyPosDAO.class);
        List<BatchResult> execute = mybatisBatch.execute(method.updateById());
        return execute.size();
    }

    @Override
    public Integer deleteVocabularyPos(List<String> wordIds, List<String> ids) {
        LambdaQueryWrapper<VocabularyPosDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CollectionUtils.isNotEmpty(ids), VocabularyPosDO::getId, ids)
                .in(VocabularyPosDO::getWordId, wordIds);
        return vocabularyPosDAO.delete(queryWrapper);
    }
}
