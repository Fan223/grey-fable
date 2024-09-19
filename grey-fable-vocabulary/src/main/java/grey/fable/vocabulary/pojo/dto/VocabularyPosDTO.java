package grey.fable.vocabulary.pojo.dto;

import lombok.Data;

/**
 * 词汇词性传输类.
 *
 * @author GreyFable
 * @since 2024/9/18 14:45
 */
@Data
public class VocabularyPosDTO {

    private String id;

    private String pos;

    private String translation;
}
