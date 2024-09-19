package grey.fable.vocabulary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import grey.fable.base.collection.CollectionUtils;
import grey.fable.base.text.StringUtils;
import grey.fable.vocabulary.dao.VocabularyDAO;
import grey.fable.vocabulary.pojo.entity.VocabularyDO;
import grey.fable.vocabulary.pojo.query.VocabularyQuery;
import grey.fable.vocabulary.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 词汇接口实现类.
 *
 * @author GreyFable
 * @since 2024/9/18 14:25
 */
@Service
public class VocabularyServiceImpl implements VocabularyService {

    private final VocabularyDAO vocabularyDAO;

    @Autowired
    public VocabularyServiceImpl(VocabularyDAO vocabularyDAO) {
        this.vocabularyDAO = vocabularyDAO;
    }

    @Override
    public VocabularyDO getVocabulary(VocabularyQuery query) {
        LambdaQueryWrapper<VocabularyDO> queryWrapper = getQueryWrapper(query);
        return vocabularyDAO.selectOne(queryWrapper);
    }

    private LambdaQueryWrapper<VocabularyDO> getQueryWrapper(VocabularyQuery query) {
        LambdaQueryWrapper<VocabularyDO> queryWrapper = new LambdaQueryWrapper<>();
        return queryWrapper.eq(StringUtils.isNotBlank(query.getTopic()), VocabularyDO::getTopic, query.getTopic())
                .eq(StringUtils.isNotBlank(query.getStatus()), VocabularyDO::getStatus, query.getStatus())
                .in(CollectionUtils.isNotEmpty(query.getIds()), VocabularyDO::getId, query.getIds())
                .like(StringUtils.isNotBlank(query.getWord()), VocabularyDO::getWord, query.getWord());
    }

    @Override
    public Page<VocabularyDO> listVocabularies(VocabularyQuery query) {
        LambdaQueryWrapper<VocabularyDO> queryWrapper = getQueryWrapper(query);
        return vocabularyDAO.selectPage(new Page<>(query.getCurrentPage(), query.getPageSize()), queryWrapper);
    }

    @Override
    public Integer saveVocabulary(VocabularyDO vocabularyDO) {
        return vocabularyDAO.insert(vocabularyDO);
    }

    @Override
    public Integer updateVocabulary(VocabularyDO vocabularyDO) {
        return vocabularyDAO.updateById(vocabularyDO);
    }

    @Override
    public Integer deleteVocabulary(List<String> ids) {
        return vocabularyDAO.deleteBatchIds(ids);
    }
}
