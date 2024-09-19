package grey.fable.vocabulary.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 词汇展示类.
 *
 * @author GreyFable
 * @since 2024/9/18 14:44
 */
@Data
public class VocabularyVO {

    private String id;

    private String topic;

    private String word;

    private String phonetic;

    private String status;

    private List<VocabularyPosVO> vocabularyPosVOS;
}
