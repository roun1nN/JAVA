package utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 使用Hutool中的DateUtil替换该工具类
 */
@Deprecated
public class DateUtils {

	/** 年-月-日 时:分:秒 显示格式 */
	// 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
	public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_TO_STRING_DETAIAL_PATTERN_M = "yyyy-MM-dd HH:mm:ss:SSS";
	public static String DATE_TO_STRING_DETAIAL_PATTERN2 = "yyyy/MM/dd HH:mm:ss";

	/** 年-月-日 显示格式 */
	public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

	/** 年月日 显示格式 */
	public static String DATE_TO_STRING_BUSINESS_PATTERN = "yyyyMMdd";

	/** 年月日 显示格式 */
	public static String DATE_TO_STRING_YEARMONTH_PATTERN = "yyyyMM";
	/** 年月日 显示格式 */
	public static String DATE_TO_STRING_DAY_PATTERN = "dd";
	/** 年月日 时：分 显示格式 */
	public static String DATE_TO_STRING_BUSINESS_HH24MM_PATTERN = "yyyyMMdd HH:mm";

	/** 时：分：秒 */
	public static String DATE_TO_STRING_TIME_PATTERN = "HH:mm:ss";

	private SimpleDateFormat simpleDateFormat;

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static Date getNowDate() {
		Date nowDate = new Date();
		return nowDate;

	}

	/**
	 * Date类型转为指定格式的String类型
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String DateToString(Date source, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(source);
	}

	/**
	 * 
	 * unix时间戳转为指定格式的String类型
	 * 
	 * 
	 * System.currentTimeMillis()获得的是是从1970年1月1日开始所经过的毫秒数 unix时间戳:是从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数,不考虑闰秒
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String timeStampToString(long source, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = new Date(source * 1000);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期转换为时间戳(unix时间戳,单位秒)
	 * 
	 * @param date
	 * @return
	 */
	public static long dateToTimeStamp(Date date) {
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp.getTime() / 1000;

	}

	/**
	 * 
	 * 字符串转换为对应日期(可能会报错异常)
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String source, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = simpleDateFormat.parse(source);
		return date;
	}

	/**
	 * 获得当前时间对应的指定格式
	 * 
	 * @param pattern
	 * @return
	 */
	public static String currentFormatDate(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());

	}

	/**
	 * 获得当前unix时间戳(单位秒)
	 * 
	 * @return 当前unix时间戳
	 */
	public static long currentTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 判断curTime(HH:mm:ss)是否在sourceTime(HH:mm:ss-HH:mm:ss)之间
	 * 
	 * @param sourceTime
	 * @param curTime
	 * @return
	 */
	public static boolean isInTime(String sourceTime, String curTime) {
		if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}
		if (curTime == null || !curTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
		}
		String[] args = sourceTime.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TO_STRING_TIME_PATTERN);
		try {
			long now = sdf.parse(curTime).getTime();
			long start = sdf.parse(args[0]).getTime();
			long end = sdf.parse(args[1]).getTime();
			if (args[1].equals("00:00:00")) {
				args[1] = "24:00:00";
			}
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			} else {
				if ((now >= start && now < end) || ((now + 1000*24*60*60) >= start && (now + 1000*24*60*60) < end)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}
	}

	/**
	 * 获取半小时时间段
	 * 
	 * @param sourceTime
	 *            现在时间
	 * @param diffMinute
	 *            调整分钟数
	 * @return 所在半小时时间段
	 */
	public static Date[] getHalfHourTimes(Date sourceTime, int diffMinute) {
		Date[] result = new Date[2];

		Date targetDate = diffDate(sourceTime, diffMinute, Calendar.MINUTE);

		GregorianCalendar target = new GregorianCalendar();
        target.setTime(targetDate);
        target.set(Calendar.SECOND, 0);
        target.set(Calendar.MILLISECOND, 0);

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(targetDate);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);

        GregorianCalendar endDate = new GregorianCalendar();
        endDate.setTime(targetDate);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);

		int targetMinute = target.get(Calendar.MINUTE);
		if (targetMinute < 30) {
			startDate.set(Calendar.MINUTE, 0);
			endDate.set(Calendar.MINUTE, 30);
		} else {
			startDate.set(Calendar.MINUTE, 30);
			endDate.set(Calendar.MINUTE, 0);
			endDate.add(Calendar.HOUR, 1);
		}
		result[0] = startDate.getTime();
		result[1] = endDate.getTime();

		return result;
	}

	/**
	 * 日期加減
	 * 
	 * @param sourceTime
	 *            開始時間
	 * @param diff
	 *            加減時間
	 * @param field
	 *            加减类型（Calendar）
	 * @return
	 */
	public static Date diffDate(Date sourceTime, int diff, int field) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(sourceTime);
		calendar.add(field, diff);
		return calendar.getTime();
	}
	
	/**
     * 日期加減
     * 
     * @param sourceTime
     *            開始時間
     * @param diff
     *            加減時間
     * @param field
     *            加减类型（Calendar）
     * @return
	 * @throws ParseException 
     */
    public static String diffDate(String sourceTime, int diff, int field, String pattern) throws ParseException {
        Date source = stringToDate(sourceTime, pattern);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(source);
        calendar.add(field, diff);
        Date result = calendar.getTime();
        
        return DateToString(result, pattern);
        
    }

	/**
	 * 返回日期所在周的星期几
	 * 
	 * @param businessDate
	 * @return
	 * @throws ParseException
	 */
	public static int getDayInWeek(String businessDate) throws ParseException {
		Date busDate = stringToDate(businessDate, DATE_TO_STRING_BUSINESS_PATTERN);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(busDate);
		int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0) {
			dayOfWeek = 0;
		}
		return dayOfWeek;
	}

	/**
	 * 取得所在月份的月未日期
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static String getActualMaxOfMonth(String source) throws ParseException {
		Date date = stringToDate(source, DATE_TO_STRING_BUSINESS_PATTERN);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateToString(calendar.getTime(), DATE_TO_STRING_BUSINESS_PATTERN);
	}

	/**
	 * 日格format
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String stringDateFormat(String source, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_BUSINESS_PATTERN);
		Date date = simpleDateFormat.parse(source);
		simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 日格format,去掉中划线
	 *
	 * @param source
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String stringDateFormatDelLine(String source, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
		Date date = simpleDateFormat.parse(source);
		simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将毫秒转换为时分秒
	 * 
	 * @param millisecond
	 * @return
	 */
	public static String getTimefromMillisecond(long millisecond) {
        String hour = StringUtils.fixZeroBeforeString(String.valueOf(millisecond / 3600000), 2);
        
        millisecond = millisecond % 3600000;
        
        String minute = StringUtils.fixZeroBeforeString(String.valueOf(millisecond / 60000), 2);
        
        millisecond = millisecond % 60000;
        
        String secend = StringUtils.fixZeroBeforeString(String.valueOf(millisecond / 1000), 2);
        
        millisecond = millisecond % 1000;
        
        String result = hour + ":" + minute + ":" + secend + "." + StringUtils.fixZeroBeforeString(String.valueOf(millisecond), 3);
        
        return result;
	}

	public static Map<String, String> getStartAndEndDate(String businessDate) throws ParseException {
		Date busDate = stringToDate(businessDate, DATE_TO_STRING_BUSINESS_PATTERN);
		GregorianCalendar calendar = new GregorianCalendar();
		GregorianCalendar cale = new GregorianCalendar();
		calendar.setTime(busDate);

		int dayIndex = calendar.get(Calendar.DAY_OF_MONTH);
		int i = (dayIndex - 1) / 7;

		// 获取本月的第一天
		cale.setTime(busDate);
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDay = cale.getTime();
		// 获取本月的最后一天
		cale.setTime(busDate);
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		Date lastDay = cale.getTime();
		// 获取开始所在时间的开始时间段
		Date startDate = diffDate(firstDay, i * 7, Calendar.DAY_OF_MONTH);
		Date endDate;
		if (i < 4) {
			endDate = diffDate(startDate, 6, Calendar.DAY_OF_MONTH);
		} else {
			endDate = lastDay;
		}
		Map<String, String> map = new HashMap<>();
		map.put("startDate", DateToString(startDate, DATE_TO_STRING_BUSINESS_PATTERN));
		map.put("endDate", DateToString(endDate, DATE_TO_STRING_BUSINESS_PATTERN));

		return map;
	}

	/**
	 * 获取未来第几天的日期
	 * @param past
	 * @return
	 */
	public static String getFutureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String result = format.format(today);
		return result;
	}
}
