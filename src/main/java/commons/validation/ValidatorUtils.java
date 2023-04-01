package commons.validation;

import commons.exception.BizException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author guorui1
 */
public class ValidatorUtils {
    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static ValidateResult validateEntity(boolean throwEx, Object object, Class<?>... groups) {
        ValidateResult res = new ValidateResult();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            List<String> messageList = new ArrayList<>();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                messageList.add(constraint.getMessage());
            }
            if (throwEx) {
                throw new BizException(String.join(",", messageList));
            }
            res.setMessageList(messageList);
        }
        return res;
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws BizException 校验不通过，则报BizException异常
     */
    public static ValidateResult validateEntity(Object object, Class<?>... groups) {
        return validateEntity(true, object, groups);
    }
}
