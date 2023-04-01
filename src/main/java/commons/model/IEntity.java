package commons.model;

import java.io.Serializable;

/**
 * 实体信息抽象：
 * 1.表名
 * 2.实体编号
 * 3.字段列表
 *
 * @author guorui1
 */
public interface IEntity<ID extends Serializable> extends Serializable {

    /**
     * 主键，所有表都用自增主键
     */
    ID getId();
}
