package io.javac.minoss.minosscommon.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author pencilso
 * @date 2020/1/23 8:30 下午
 */
@Validated
public class DateUtils {

    /**
     * get current time
     *
     * @return
     */
    @NotNull
    public static Date now() {
        return new Date();
    }

    /**
     * get current time stamp
     *
     * @return
     */
    public static Long nowMS() {
        return System.currentTimeMillis();
    }

    /**
     * add time
     *
     * @param date     current time
     * @param time     need add time
     * @param timeUnit time unit {day 、hours、minutes、seconds、milliseconds}
     * @return 返回添加后的时间
     */
    public static Long add(@NotNull Long date, long time, @NonNull TimeUnit timeUnit) {
        long result;
        switch (timeUnit) {
            case DAYS:
                result = date + time * (24 * 60 * 60 * 1000);
                break;
            case HOURS:
                result = date + time * (60 * 60 * 1000);
                break;
            case MINUTES:
                result = date + time * (60 * 1000);
                break;
            case SECONDS:
                result = date + time * 1000;
                break;
            case MILLISECONDS:
                result = date + time;
                break;
            default:
                result = date;
        }
        return result;
    }
}
