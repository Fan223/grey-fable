package grey.fable.vocabulary.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import grey.fable.vocabulary.pojo.dto.VocabularyDTO;
import grey.fable.vocabulary.pojo.dto.VocabularyPosDTO;
import grey.fable.vocabulary.pojo.entity.VocabularyDO;
import grey.fable.vocabulary.pojo.entity.VocabularyPosDO;
import grey.fable.vocabulary.pojo.vo.VocabularyPosVO;
import grey.fable.vocabulary.pojo.vo.VocabularyVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 词汇实体转换类.
 *
 * @author GreyFable
 * @since 2024/9/18 15:15
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VocabularyMapStruct {

    VocabularyVO convertVocabulary(VocabularyDO vocabularyDO);

    List<VocabularyVO> convertVocabulary(List<VocabularyDO> vocabularyDOS);

    Page<VocabularyVO> convertVocabulary(Page<VocabularyDO> vocabularyPage);

    VocabularyDO convertVocabulary(VocabularyDTO vocabularyDTO);

    VocabularyPosVO convertVocabularyPos(VocabularyPosDO vocabularyPosDO);

    VocabularyPosDO convertVocabularyPos(VocabularyPosDTO vocabularyPosDTO);

    List<VocabularyPosDO> convertVocabularyPos(List<VocabularyPosDTO> vocabularyPosDTOS);
}
