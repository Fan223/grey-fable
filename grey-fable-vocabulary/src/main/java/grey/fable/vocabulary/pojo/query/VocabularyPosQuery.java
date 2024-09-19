package grey.fable.vocabulary.pojo.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 词汇词性查询参数.
 *
 * @author GreyFable
 * @since 2024/9/18 14:47
 */
@Data
@Builder
public class VocabularyPosQuery {

    private List<Long> wordIds;

    private String translation;
}
