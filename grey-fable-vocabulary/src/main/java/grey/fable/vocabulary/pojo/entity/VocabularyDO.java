package grey.fable.vocabulary.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 词汇实体类.
 *
 * @author GreyFable
 * @since 2024/9/18 14:25
 */
@Data
@TableName("vocabulary")
public class VocabularyDO {

    @TableId
    private Long id;

    private String topic;

    private String word;

    private String phonetic;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
