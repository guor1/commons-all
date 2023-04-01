package commons.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author guorui1
 */
@Getter
@Setter
@MappedSuperclass
public class TemporalEntity extends BaseEntity {
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "created_user")
    private String createdUser;
    @Column(name = "modified_user")
    private String modifiedUser;
}
