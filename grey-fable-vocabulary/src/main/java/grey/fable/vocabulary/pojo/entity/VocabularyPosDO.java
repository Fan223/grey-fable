package grey.fable.vocabulary.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 词汇词性实体类.
 *
 * @author GreyFable
 * @since 2024/9/18 14:25
 */
@Data
@TableName("vocabulary_pos")
public class VocabularyPosDO {

    @TableId
    private Long id;

    private Long wordId;

    private String pos;

    private String translation;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
