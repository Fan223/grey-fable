package grey.fable.vocabulary.pojo.query;

import grey.fable.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 词汇查询参数.
 *
 * @author GreyFable
 * @since 2024/9/18 14:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VocabularyQuery extends PageQuery {

    private List<Long> ids;

    private String topic;

    private String word;

    private String status;

    private String translation;

    public List<Long> getIds() {
        ids = Optional.ofNullable(ids).orElse(new ArrayList<>());
        return ids;
    }
}
