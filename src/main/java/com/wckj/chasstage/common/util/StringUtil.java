package com.wckj.chasstage.common.util;

import org.apache.commons.codec.net.URLCodec;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/***
 * 字符串工具类
 * 
 * */
public class StringUtil {

	static URLCodec urlCoder = new URLCodec();

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNullBlank(String str) {
		return str == null || "".equals(str.trim());
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String transFirstChar(boolean isUpperChar, String src) {
		StringBuffer sb = new StringBuffer("");
		if (isUpperChar)
			sb.append(src.substring(0, 1).toUpperCase());
		else
			sb.append(src.substring(0, 1).toLowerCase());
		sb.append(src.substring(1));
		return sb.toString();
	}

	public static String[] splitEx(String str, String spilter) {
		if (str == null)
			return null;
		if (spilter == null || spilter.equals("")
				|| str.length() < spilter.length()) {
			String t[] = { str };
			return t;
		}
		ArrayList al = new ArrayList();
		char cs[] = str.toCharArray();
		char ss[] = spilter.toCharArray();
		int length = spilter.length();
		int lastIndex = 0;
		for (int i = 0; i <= str.length() - length;) {
			boolean notSuit = false;
			int j = 0;
			do {
				if (j >= length)
					break;
				if (cs[i + j] != ss[j]) {
					notSuit = true;
					break;
				}
				j++;
			} while (true);
			if (!notSuit) {
				al.add(str.substring(lastIndex, i));
				i += length;
				lastIndex = i;
			} else {
				i++;
			}
		}

		if (lastIndex <= str.length())
			al.add(str.substring(lastIndex, str.length()));
		String t[] = new String[al.size()];
		for (int i = 0; i < al.size(); i++)
			t[i] = (String) al.get(i);

		return t;
	}

	public static String urlDecode(String str, String charset) {
		try {
			return urlCoder.decode(str, charset);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// public static String getGuid32() {
	// return replaceString(getGuid(), "-", "").toUpperCase();
	// }
	//
	// public static String getGuid() {
	// return UUIDGenerator.getInstance().generateRandomBasedUUID().toString();
	// }

	public static String replaceString(String sourceStr, String oldStr,
			String newStr) {
		return replaceString(sourceStr, oldStr, newStr, false);
	}

	public static String replaceString(String sourceStr, String oldStr,
			String newStr, boolean isIgnoreCase) {
		return replaceString(sourceStr, oldStr, newStr, isIgnoreCase, false);
	}

	public static String replaceString(String sourceStr, String oldStr,
			String newStr, boolean isIgnoreCase, boolean isFirst) {
		if (sourceStr == null || oldStr == null || oldStr.equals(""))
			return null;
		String str_RetStr = sourceStr;
		int pos = str_RetStr.indexOf(oldStr);
		do {
			if (pos == -1)
				break;
			str_RetStr = (new StringBuilder())
					.append(str_RetStr.substring(0, pos)).append(newStr)
					.append(str_RetStr.substring(pos + oldStr.length()))
					.toString();
			if (isFirst)
				break;
			pos = str_RetStr.indexOf(oldStr, pos + newStr.length());
		} while (true);
		return str_RetStr;
	}

	public static boolean getBoolean(String booleanstr) {
		return getBoolean(booleanstr, false);
	}

	public static boolean getBoolean(String booleanstr, boolean defaultValue) {
		if (isNullBlank(booleanstr))
			return defaultValue;
		boolean booleanRetu = defaultValue;
		booleanstr = booleanstr.trim();
		if ("true".equalsIgnoreCase(booleanstr) || equals("1", booleanstr))
			booleanRetu = true;
		return booleanRetu;
	}

	public static boolean equals(String str1, String str2) {
		boolean returnBol = false;
		if (isNullBlank(str1) && isNullBlank(str2))
			returnBol = true;
		if (!isNullBlank(str1) && !isNullBlank(str2) && str1.equals(str2))
			returnBol = true;
		return returnBol;
	}

	public static String clearNull(String obj) {
		if (obj == null)
			return "";
		else
			return obj.trim();
	}

	public static int strlen(String str) {
		if (str == null || str.length() <= 0)
			return 0;
		int len = 0;
		for (int i = str.length() - 1; i >= 0; i--) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A'
					&& c <= 'Z') {
				len++;
				continue;
			}
			int ascii = Character.codePointAt(str, i);
			if (ascii >= 0 && ascii <= 255)
				len++;
			else
				len += 2;
		}

		return len;
	}

	public static boolean haveStrInStringArray(String[] strArray, String str) {
		return contains(strArray, str);
	}

	public static boolean contains(String[] strArray, String str) {
		return contains(strArray, str, true);
	}

	public static boolean contains(String[] strArray, String str,
			boolean equalsIgnoreCase) {
		if ((strArray == null) || (strArray.length == 0) || (isNullBlank(str))) {
			return false;
		}
		for (int i = 0; i < strArray.length; i++) {
			if (equalsIgnoreCase) {
				if (strArray[i].equals(str)) {
					return true;
				}
			} else if (strArray[i].equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	public static String[] splitString(String sourceStr, String splitStr) {
		if ((isNullBlank(sourceStr)) || (isNullBlank(splitStr))) {
			return null;
		}

		int int_ArraySize = subStringCount(sourceStr, splitStr);
		if (int_ArraySize == -1) {
			return null;
		}

		sourceStr = sourceStr + splitStr;
		String[] str_RetArr = new String[int_ArraySize + 1];
		int int_pos = sourceStr.indexOf(splitStr);
		int int_ArrayPos = 0;
		for (; int_pos != -1; int_pos = sourceStr.indexOf(splitStr)) {
			str_RetArr[(int_ArrayPos++)] = sourceStr.substring(0, int_pos);
			sourceStr = sourceStr.substring(int_pos + splitStr.length());
		}
		return str_RetArr;
	}

	public static int subStringCount(String sourceStr, String subStr) {
		if ((isNullBlank(sourceStr)) || (isNullBlank(subStr))) {
			return -1;
		}
		int result = 0;
		for (int int_pos = sourceStr.toUpperCase()
				.indexOf(subStr.toUpperCase()); int_pos != -1; int_pos = sourceStr
				.toUpperCase().indexOf(subStr.toUpperCase(),
						int_pos + subStr.length())) {
			result++;
		}
		return result;
	}

	/** 身份证解析 **/
	/** 中国公民身份证号码最大长度。 */
	public static final int CHINA_ID_LENGTH = 18;

	/**
	 * 根据身份编号获取年龄
	 * 
	 * @param idCard
	 *            身份编号
	 * @return 年龄
	 */
	public static int getAgeByIdCard(String idCard) {
		int iAge = 0;
		Calendar cal = Calendar.getInstance();
		String year = idCard.substring(6, 10);
		int iCurrYear = cal.get(Calendar.YEAR);
		iAge = iCurrYear - Integer.valueOf(year);
		return iAge;
	}

	/**
	 * 根据身份编号获取生日
	 *
	 * @param idCard
	 *            身份编号
	 * @return 生日(yyyyMMdd)
	 */
	public static String getBirthByIdCard(String idCard) {
		return idCard.substring(6, 14);
	}

	/**
	 * 根据身份编号获取生日年
	 *
	 * @param idCard
	 *            身份编号
	 * @return 生日(yyyy)
	 */
	public static Short getYearByIdCard(String idCard) {
		return Short.valueOf(idCard.substring(6, 10));
	}

	/**
	 * 根据身份编号获取生日月
	 *
	 * @param idCard
	 *            身份编号
	 * @return 生日(MM)
	 */
	public static Short getMonthByIdCard(String idCard) {
		return Short.valueOf(idCard.substring(10, 12));
	}

	/**
	 * 根据身份编号获取生日天
	 *
	 * @param idCard
	 *            身份编号
	 * @return 生日(dd)
	 */
	public static Short getDateByIdCard(String idCard) {
		return Short.valueOf(idCard.substring(12, 14));
	}

	/**
	 * 根据身份编号获取性别
	 *
	 * @param idCard
	 *            身份编号
	 * @return 性别(M-男，F-女，N-未知)
	 */
	public static String getGenderByIdCard(String idCard) {
		String sGender = "未知";

		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			sGender = "1";// 男
		} else {
			sGender = "2";// 女
		}
		return sGender;
	}
	/**
	 * 获取File的byte数组类型数据
	 * @param f  File文件
	 * @return 数组
	 */
	public static String getBytesFromFile(File f, HttpServletResponse response) {
		if (f == null) {
			return null;
		} else {
			try {
				FileInputStream e = new FileInputStream(f);
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1000];
				int n;
				while ((n = e.read(b)) != -1) {
					out.write(b, 0, n);
					out.flush();
				}
				e.close();
				out.close();
				return "ok";
			} catch (IOException arg4) {
				return null;
			}
		}
	}
}
