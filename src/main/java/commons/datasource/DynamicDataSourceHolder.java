package commons.datasource;

import org.springframework.core.NamedThreadLocal;

public class DynamicDataSourceHolder {

    public static final ThreadLocal<String> holder = new NamedThreadLocal<>("DynamicDataSource");

    public static String getDataSourceName() {
        return holder.get();
    }

    public static void setDataSourceName(String dataSourceName) {
        holder.set(dataSourceName);
    }
}
