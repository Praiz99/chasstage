package com.wckj.chasstage.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 * 
 * */
public class DateTimeUtils {
		/***
		 * 预制 时间处理
		 * 使用前查看本类
		 * **/
	    public static String getDateStr(Date date, int formatType){
	    	if(date == null) return "";
	        if(formatType < 1 || formatType > 20)
	        	//时间格式化类型不是合法的值
	            throw new IllegalArgumentException("\u65F6\u95F4\u683C\u5F0F\u5316\u7C7B\u578B\u4E0D\u662F\u5408\u6CD5\u7684\u503C\u3002");
	        String formatStr = null;
	        switch(formatType)
	        {
	        case 1: 
	            formatStr = "yyyy";
	            break;
	        case 2: 
	            formatStr = "MM";
	            break;
	        case 3: 
	            formatStr = "dd";
	            break;
	        case 4:
	        	 formatStr = "yyyy.MM.dd";
	            break;
	        case 5:
	        	 formatStr = "yyyy.MM.dd HH:mm";
	            break;
	        case 6:
	        	 formatStr = "yyyy.MM.dd HH:mm:ss";
	            break;
	        case 7:
	        	 formatStr = "yyyy.MM.dd";
	            break;
	        case 8:
	        	 formatStr = "yyyy.MM.dd HH:mm";
	            break;
	        case 9:
	        	 formatStr = "yyyy.MM.dd HH:mm:ss";
	            break;
	        case 10:
	            formatStr = "yyyy'\u5E74'MM'\u6708'dd'\u65E5'";
	            break;
	        case 11:
	        	formatStr = "yyyy'\u5E74'MM'\u6708'dd HH:mm";
	            break;
	        case 12:
	        	formatStr = "yyyy'\u5E74'MM'\u6708'dd HH:mm:ss";
	            break;
	        case 13: 
	        	formatStr = "yyyy-MM-dd";
	            break;
	        case 14: 
	            formatStr = "yyyy-MM-dd HH:mm";
	            break;
	        case 15: 
	            formatStr = "yyyy-MM-dd HH:mm:ss";
	            break;
	        case 16: 
	            formatStr = "yyyyMMddHHmmss";
	            break;
	        case 17: 
	            formatStr = "yyyyMMdd";
	            break;
	        case 18: 
	            formatStr = "yyyy.MM";
	            break;
	        case 19: 
	            formatStr =  "yyyy-MM";
	            break;
				case 20:
					formatStr = "yyyy年MM月dd日 HH时mm分";
					break;
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
	        return sdf.format(date);
	    }
	    /**预制   获取当前时间的格式化*/
	    public static String getDateString(int formatType) {
	    	return getDateStr(new Date(),formatType);
	    }
		/**
		 * 获取自定义的时间字段
		 * @return
		 */
		public static String getDateString(String format) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String s_date = sdf.format(new Date());
			return s_date;
		}
		
		public static String doFormatDate(String pattern, Date date)
	    {
	        SimpleDateFormat df = new SimpleDateFormat(pattern);
	        String result = "";
	        try
	        {
	            result = df.format(date).toString();
	        }
	        catch(Exception ex)
	        {
	            ex.printStackTrace();
	            return "";
	        }
	        return result;
	    }
		
		public static String getCurrDateStr(int formatType)
	    {
	        return getDateStr(new Date(), formatType);
	    }
		
		public static String getDateFormat(String datestr,String inFmt,String outFmt) throws ParseException{
			if((datestr==null)||("".equals(datestr.trim())))
				return datestr;
			if((inFmt==null)||("".equals(inFmt.trim())))
				return inFmt;
			if((outFmt==null)||("".equals(outFmt.trim())))
				return "yyyyMMdd";
			Date inDate=getDate(datestr,inFmt);
			if(inDate==null){
				return datestr;
			}
			String retu=getDateFormat(inDate,outFmt);
			inDate=null;
			return retu;
		}
		
		public static Date getDate(String datestr,String inFmt) throws ParseException{
			if((datestr==null)||("".equals(datestr.trim())))
				return null;
			if((inFmt==null)||("".equals(inFmt.trim())))
				inFmt= "yyyyMMdd";
			Date inDate=null;
			SimpleDateFormat inDateFormat=new SimpleDateFormat(inFmt);
			inDateFormat.setLenient(true);
			inDate=inDateFormat.parse(datestr);
			inDateFormat=null;
			return inDate;
		}
		
		public static String getDateFormat(Date date, String outFmt)
	    {
	        if(date == null)
	            return null;
	        if(outFmt == null || "".equals(outFmt.trim()))
	            outFmt = "yyyyMMdd";
	        String retu = null;
	        SimpleDateFormat dateFormat = null;
	        try
	        {
	            dateFormat = new SimpleDateFormat(outFmt);
	        }
	        catch(IllegalArgumentException iaex)
	        {
	            dateFormat = new SimpleDateFormat("yyyyMMdd");
	        }
	        retu = dateFormat.format(date);
	        dateFormat = null;
	        return retu;
	    }
		
		
		/**
		 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
		 */
		public static String formatDate(Date date, Object... pattern) {
			String formatDate = null;
			if (pattern != null && pattern.length > 0) {
				formatDate = DateFormatUtils.format(date, pattern[0].toString());
			} else {
				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
			}
			return formatDate;
		}
		
		
		public static String getPreTodDay(int days) {
			String year = getYear();
			String mth = getMonth();
			int day = Integer.parseInt(getDay());
			if (day > days) {
				String now = String.valueOf(day - days);
				return year + "-" + mth + "-" + now;
			} else {
				Calendar cal = Calendar.getInstance();
				int month = Integer.parseInt(DateTimeUtils.getMonth());
				cal.set(Calendar.MONTH, month - 1);
				int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
				int now = dateOfMonth + day - days;
				int m = month - 1;
				return year + "-" + m + "-" + now;
			}
		}
		
		/**
		 * 获取i天(月)前日期
		 * @param i 差值
		 * @param type 天、月等  ---例：Calendar.DATE、Calendar.MONTH
		 * @param formate 时间样式
		 * @return
		 */
		public static String getDateBeginToday(int i,int type,String formate){
			SimpleDateFormat format = new SimpleDateFormat(formate);
			Calendar c = Calendar.getInstance();
	        //过去七天
	        c.setTime(new Date());
	        c.add(type, -i);
	        Date d = c.getTime();
	        String time = format.format(d);
	        return time;
		}
		
		public static long getDateDiff(Date begin,Date end){
			long nh = 1000 * 60 * 60;
			long diff = end.getTime() - begin.getTime();
			long hour = diff / nh;
	        return hour;
		}
		
		/**
		 * 得到当前年份字符串 格式（yyyy）
		 */
		public static String getYear() {
			return formatDate(new Date(), "yyyy");
		}

		/**
		 * 得到日期年份
		 * 
		 * @param date
		 * @return
		 */
		public static int getYear(Date date) {
			return Integer.parseInt(formatDate(date, "yyyy"));
		}

		/**
		 * 得到日期月份（如以0开始，则去掉0）
		 * 
		 * @param date
		 * @return
		 */
		public static int getMonth(Date date) {
			String month = formatDate(date, "MM");
			boolean mat = month.matches("^0.*");
			if (mat) {
				return Integer.parseInt(month.substring(1));
			}
			return Integer.parseInt(month);
		}

		/**
		 * 得到当前月份字符串 格式（MM）
		 */
		public static String getMonth() {
			return formatDate(new Date(), "MM");
		}

		/**
		 * 得到当天字符串 格式（dd）
		 */
		public static String getDay() {
			return formatDate(new Date(), "dd");
		}

		/**
		 * 得到当前星期字符串 格式（E）星期几
		 */
		public static String getWeek() {
			return formatDate(new Date(), "E");
		}
		/**
		 * 出生日期获得年龄
		 */
		public static  int getAge(Date birthDay){
            Calendar cal = Calendar.getInstance();  
            if (cal.before(birthDay)) {  
                throw new IllegalArgumentException(  
                        "The birthDay is before Now.It's unbelievable!");  
            }  
            int yearNow = cal.get(Calendar.YEAR);  
            int monthNow = cal.get(Calendar.MONTH);  
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
            cal.setTime(birthDay);   
      
            int yearBirth = cal.get(Calendar.YEAR);  
            int monthBirth = cal.get(Calendar.MONTH);  
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);   
      
            int age = yearNow - yearBirth;  
      
            if (monthNow <= monthBirth) {  
                if (monthNow == monthBirth) {  
                    if (dayOfMonthNow < dayOfMonthBirth) age--;  
                }else{  
                    age--;  
                }  
            }  
            return age;  
        } 
		public static Date getBeforeDate() {
			/*Calendar lastDate = Calendar.getInstance();
			lastDate.roll(Calendar.DATE, -7);// 日期回滚7天
			Date date = lastDate.getTime();*/
			Calendar c = Calendar.getInstance();
	        //过去七天
	        c.setTime(new Date());
	        c.add(Calendar.DATE, -7);
	        Date d = c.getTime();
			return d;
		}

		public static String getComplementTime(String time){
			if(StringUtils.isEmpty(time)){
				return "";
			}
			if(time.split(" ").length > 1){
				String date = time.split(" ")[1];
				if(date.length() == 2){
					time += ":00:00";
				}
				if(date.length() == 5){
					time += ":00";
				}
			}
			return time;
		}


	public static int getAgeByBirth(Date birthDay){
		int age = 0;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			//出生日期晚于当前时间，无法计算
			return age;
		}
		//当前年份
		int yearNow = cal.get(Calendar.YEAR);
		//当前月份
		int monthNow = cal.get(Calendar.MONTH);
		//当前日期
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		//计算整岁数
		age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				/**当前日期在生日之前，年龄减一*/
				if (dayOfMonthNow < dayOfMonthBirth){
					age--;
				}
			} else {
				//当前月份在生日之前，年龄减一
				age--;
			}
		}
		return age;
	}

    public static Date parseDateTime(String str, String format) {
        if (com.wckj.framework.core.utils.StringUtils.isNullBlank(str))
            return null;
        try {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

	//比较两个时间差
	public static Duration BetweenDateTime(Date startDate, Date endDate){
		if(startDate == null || endDate == null){
			return Duration.ZERO;
		}
		Instant startInstant = startDate.toInstant();
		ZoneId startZoneId = ZoneId.systemDefault();
		LocalDateTime start = startInstant.atZone(startZoneId).toLocalDateTime();

		Instant endInstant = endDate.toInstant();
		ZoneId endZoneId = ZoneId.systemDefault();
		LocalDateTime end = endInstant.atZone(endZoneId).toLocalDateTime();

		return Duration.between(start,end);
	}
	/**
	 * 判断时间格式是否正确
	 * @param str 时间字符串
	 * @param formatStr 时间转换格式
	 * @return true or false
	 */

	public static boolean isValidDate(String str, String formatStr) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
			e.printStackTrace();
		}
		return convertSuccess;
	}
}
