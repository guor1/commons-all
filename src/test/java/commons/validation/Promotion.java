package commons.validation;

import commons.model.DateRange;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class Promotion {
    @NotNull(message = "修改活动时，活动ID不为空")
    private Long id;
    /**
     * 活动名称
     */
    @NotNull(message = "活动名称不为空")
    @Length(max = 20, min = 5, message = "活动名称长度为[5,20]")
    private String promotionName;
    /**
     * 活动时间
     */
    @NotNull(message = "活动时间不为空")
    private DateRange promotionDate;
}
