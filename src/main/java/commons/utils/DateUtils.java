package commons.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author guor
 */
public class DateUtils {
    /**
     * 获取当前时间
     *
     * @return 返回当前时间
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, ISO_DATETIME_FORMAT);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        return toDate(LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(pattern)));
    }

    public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String CN_DATETIME_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String UTC_DATE_FORMAT = "yyyy/MM/dd";

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String PLAIN_DATE_FORMAT = "yyyyMMdd";
    public static final String PLAIN_DATETIME_FORMAT = "yyyyMMddHHmmss";

    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    public static final Locale DEFAULT_LOCALE = Locale.CHINESE;

    public static final int SECONDS_IN_DAY = 86400;
    public static final long MILLIS_IN_DAY = 86400000L;

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Calendar toCalendar(LocalDateTime localDateTime) {
        return GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
    }

    public static Calendar toCalendar(LocalDate localDate) {
        return GregorianCalendar.from(ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.systemDefault()));
    }

    public static long toMilliseconds(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long toMilliseconds(LocalDate localDate) {
        return toMilliseconds(localDate.atStartOfDay());
    }

    public static LocalDateTime fromCalendar(Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    public static LocalDateTime fromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime fromMilliseconds(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

    public static Date addSeconds(final Date date, final int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addMilliseconds(final Date date, final int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    public static Date add(final Date date, final int dateField, final int amount) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(dateField, amount);
        return c.getTime();
    }
}
