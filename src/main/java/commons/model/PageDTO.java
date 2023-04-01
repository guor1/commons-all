package commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author guorui1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 列表数据
     */
    private List<T> list;
    /**
     * 总记录数
     */
    private int totalCount;
}
