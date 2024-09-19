package grey.fable.query;

import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * 分页基础查询参数.
 *
 * @author GreyFable
 * @since 2024/9/18 15:20
 */
@Setter
@ToString
public class PageQuery {

    private Integer currentPage;

    private Integer pageSize;

    public int getCurrentPage() {
        return Optional.ofNullable(currentPage).filter(i -> i > 0).orElse(1);
    }

    public int getPageSize() {
        return Optional.ofNullable(pageSize).filter(i -> i > 0).orElse(12);
    }
}
