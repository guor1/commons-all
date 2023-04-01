package commons.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author guorui1
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements IEntity<Long> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
}
