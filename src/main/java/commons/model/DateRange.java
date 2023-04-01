package commons.model;

import commons.exception.BizException;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guorui1
 */
@Data
public class DateRange implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 活动生效时间
     */
    private Date beginTime;
    /**
     * 活动失效时间
     */
    private Date endTime;

    public DateRange(Date beginTime, Date endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        if (beginTime == null || endTime == null) {
            throw new BizException("开始时间和结束时间不能同时为空");
        }
        if (beginTime.after(endTime)) {
            throw new BizException("开始时间不能晚于结束时间");
        }
    }
}
