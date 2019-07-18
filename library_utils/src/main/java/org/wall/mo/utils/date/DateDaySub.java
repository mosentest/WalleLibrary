package org.wall.mo.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.cnblogs.com/ggband/p/9711905.html
 */
public class DateDaySub {

    private static Calendar calS = Calendar.getInstance();
    private static Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");//定义整则表达式

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 计算剩余时间
     *
     * @param startDateStr yyyy-MM-dd
     * @param endDateStr   yyyy-MM-dd
     * @return ？年？个月？天
     */
    public static int[] remainDateToString(String startDateStr, String endDateStr) throws ParseException {
        int[] result = new int[]{-1, -1, -1};
        Date startDate = null;
        Date endDate = null;

        startDate = simpleDateFormat.parse(startDateStr);
        endDate = simpleDateFormat.parse(endDateStr);

        calS.setTime(startDate);
        int startY = calS.get(Calendar.YEAR);
        int startM = calS.get(Calendar.MONTH);
        int startD = calS.get(Calendar.DATE);
        int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);

        calS.setTime(endDate);
        int endY = calS.get(Calendar.YEAR);
        int endM = calS.get(Calendar.MONTH);
        //处理2011-01-10到2011-01-10，认为服务为一天
        int endD = calS.get(Calendar.DATE);
        int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);

        //StringBuilder sBuilder = new StringBuilder();
        if (endDate.compareTo(startDate) < 0) {
            return result;
        }
        int lday = endD - startD;
        if (lday < 0) {
            endM = endM - 1;
            lday = startDayOfMonth + lday;
        }
        //处理天数问题，如：2011-01-01 到 2013-12-31  2年11个月31天     实际上就是3年
        if (lday == endDayOfMonth) {
            endM = endM + 1;
            lday = 0;
        }
        int mos = (endY - startY) * 12 + (endM - startM);
        int lyear = mos / 12;
        int lmonth = mos % 12;
        if (lyear > 0) {
            result[0] = lyear;
            //sBuilder.append(lyear + "年");
        } else {
            result[0] = 0;
        }
        if (lmonth > 0) {
            //sBuilder.append(lmonth + "个月");
            result[1] = lmonth;
        } else {
            result[1] = 0;
        }
        // if(lyear==0)//满足项目需求 满一年不显示天数
        if (lday > 0) {
            //sBuilder.append(lday + "天");
            result[2] = lday;
        } else {
            result[2] = 0;
        }
        return result;
    }


    /**
     * https://blog.csdn.net/u014003446/article/details/83898779
     * @return
     */
    public static  long perThridMouthTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);// 设为当前月的1号
        calendar.add(Calendar.MONTH, -2);// 0表示当前月，-2就是当前月-2
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /*
     * 转换 dataAndTime 2013-12-31 23:59:59 到
     * date 2013-12-31
     */
    public static String getDate(String dateAndTime) {
        if (dateAndTime != null && !"".equals(dateAndTime.trim())) {
            Matcher m = p.matcher(dateAndTime);
            if (m.find()) {
                return dateAndTime.subSequence(m.start(), m.end()).toString();
            }
        }
        return "error";
    }


    /**
     *     * 获取两个日期之间的间隔天数
     *     * @return
     *     
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }
}