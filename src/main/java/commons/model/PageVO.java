package commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 翻页信息对象，默认第1页10条数据
 *
 * @author guorui1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageVO {
    /**
     * 当前页数
     */
    private int pageNo;
    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 偏移
     */
    public int getOffset() {
        return pageNo <= 0 ? 0 : (pageNo - 1) * pageSize;
    }
}
