package grey.fable.vocabulary.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 词汇传输类.
 *
 * @author GreyFable
 * @since 2024/9/18 14:45
 */
@Data
public class VocabularyDTO {

    private String id;

    private String topic;

    private String word;

    private String phonetic;

    private String status;

    private List<VocabularyPosDTO> vocabularyPosDTOS;
}
