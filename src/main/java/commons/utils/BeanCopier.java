package commons.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象复制，目前有三种实现，手工复制（或MapStruct） > cglib > 反射
 *
 * @author guorui1
 */
public class BeanCopier {
    public static <T> List<T> copyProperties(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Object obj : sourceList) {
            list.add(copyProperties(obj, targetClass));
        }
        return list;
    }

    /**
     * 复制实体，生成目标对象
     *
     * @param source      源对象
     * @param targetClass 目标实体类
     * @param <T>         目标实体类类型
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        return copyProperties(source, targetClass, StringUtils.EMPTY);
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        T target = BeanUtils.instantiateClass(targetClass);
        copyProperties(source, target, ignoreProperties);
        return target;
    }

    public static <T> void copyProperties(Object source, T targetObj, String... ignoreProperties) {
        if (source != null) {
            BeanUtils.copyProperties(source, targetObj, ignoreProperties);
        }
    }

    public static <T> ResultBean<T> toResponseInfo(Object source, Class<T> targetClass) {
        if (source == null) {
            return ResultBean.success();
        }
        return ResultBean.success(copyProperties(source, targetClass));
    }

    public static <T> ResultBean<List<T>> toResponseInfo(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return ResultBean.success();
        }
        return ResultBean.success(sourceList.stream().map(item -> copyProperties(item, targetClass)).collect(Collectors.toList()));
    }
}
