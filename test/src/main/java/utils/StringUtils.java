package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Description: String 工具类 User: Administrator Date: 2017-11-10 Time: 9:31
 */
public class StringUtils {
	public static String uuid32() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String uuid36() {
		return UUID.randomUUID().toString();
	}

	public static boolean isEmpty(String strVal) {
		return strVal == null || safeTrim(strVal).isEmpty();
	}

	public static boolean isNotEmpty(String strVal) {
		return !isEmpty(strVal);
	}

	public static String safeToString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String safeTrim(Object obj) {
		char[] charArr = safeToString(obj).toCharArray();

		int i;
		for (i = 0; i < charArr.length && (charArr[i] <= ' ' || charArr[i] == 12288); ++i) {
			if (charArr[i] == 12288) {
				charArr[i] = ' ';
			}
		}

		for (i = charArr.length - 1; i >= 0 && (charArr[i] <= ' ' || charArr[i] == 12288); --i) {
			if (charArr[i] == 12288) {
				charArr[i] = ' ';
			}
		}

		return (new String(charArr)).trim();
	}

	public static String safeReplace(String source, String target, String replacement) {
		if (source != null && !source.isEmpty() && target != null && !target.isEmpty() && !target.equals(replacement)) {
			List<Integer> offsets = new ArrayList<Integer>();
			int targetLen = target.length();
			int offset = 0;

			while (true) {
				offset = source.indexOf(target, offset);
				if (offset == -1) {
					String result = source;
					if (!offsets.isEmpty()) {
						int sourceLen = source.length();
						if (replacement == null) {
							replacement = "";
						}

						int replacementLen = replacement.length();
						int offsetsSize = offsets.size();
						int resultLen = sourceLen + (replacementLen - targetLen) * offsetsSize;
						char[] sourceCharArr = source.toCharArray();
						char[] replacementCharArr = replacement.toCharArray();
						char[] destCharArr = new char[resultLen];
						int firstOffset = ((Integer) offsets.get(0)).intValue();
						System.arraycopy(sourceCharArr, 0, destCharArr, 0, firstOffset);
						if (replacementLen > 0) {
							System.arraycopy(replacementCharArr, 0, destCharArr, firstOffset,
									replacementCharArr.length);
						}

						int preOffset = firstOffset;
						int destPos = firstOffset + replacementCharArr.length;

						int lastFragmentLen;
						for (lastFragmentLen = 1; lastFragmentLen < offsetsSize; ++lastFragmentLen) {
							offset = ((Integer) offsets.get(lastFragmentLen)).intValue();
							int fragmentLen = offset - preOffset - targetLen;
							System.arraycopy(sourceCharArr, preOffset + targetLen, destCharArr, destPos, fragmentLen);
							destPos += fragmentLen;
							if (replacementLen > 0) {
								System.arraycopy(replacementCharArr, 0, destCharArr, destPos,
										replacementCharArr.length);
							}

							preOffset = offset;
							destPos += replacementCharArr.length;
						}

						lastFragmentLen = sourceLen - preOffset - targetLen;
						System.arraycopy(sourceCharArr, preOffset + targetLen, destCharArr, destPos, lastFragmentLen);
						result = new String(destCharArr);
					}

					return result;
				}

				offsets.add(offset);
				offset += targetLen;
			}
		} else {
			return source;
		}
	}

	public static String[] split(String source, char splitChar) {
		String[] strArr = null;
		List<String> strList = new LinkedList<String>();
		if (null != source && !source.isEmpty()) {
			char[] charArr = source.toCharArray();
			int start = 0;

			for (int end = 0; end < source.length(); ++end) {
				char c = charArr[end];
				if (c == splitChar) {
					if (start != end) {
						String fragment = source.substring(start, end);
						strList.add(fragment);
					}

					start = end + 1;
				}
			}

			if (start < source.length()) {
				strList.add(source.substring(start));
			}

			strArr = new String[strList.size()];
			strList.toArray(strArr);
		} else {
			strArr = new String[0];
		}

		return strArr;
	}

	public static String[] split(String source, String splitStr) {
		String[] strArr = null;
		if (null != source && !source.isEmpty()) {
			if (splitStr.length() == 1) {
				strArr = split(source, splitStr.charAt(0));
			} else {
				int strLen = source.length();
				int splitStrLen = splitStr.length();
				List<String> strList = new LinkedList<String>();
				int start = 0;

				int end;
				for (; start < strLen; start = end + splitStrLen) {
					end = source.indexOf(splitStr, start);
					String fregment;
					if (end == -1) {
						fregment = source.substring(start);
						strList.add(fregment);
						break;
					}

					if (start != end) {
						fregment = source.substring(start, end);
						strList.add(fregment);
					}
				}

				strArr = new String[strList.size()];
				strList.toArray(strArr);
			}
		} else {
			strArr = new String[0];
		}

		return strArr;
	}

	public static String encrypt(String sSrc, String sKey) throws Exception {
		if (sKey != null && sKey.length() == 16) {
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(1, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} else {
			return null;
		}
	}

	public static String decrypt(String sSrc, String sKey) throws Exception {
		if (sKey != null && sKey.length() == 16) {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(2, skeySpec, iv);
			byte[] encrypted1 = hex2byte(sSrc);

			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception var9) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String join(Object... args) {
		return joinWithSplit((String) null, args);
	}

	public static String joinWithSplit(char split, String... args) {
		return joinWithSplit(split + "", (Object[]) args);
	}

	public static String joinWithSplit(char split, Object... args) {
		return joinWithSplit(split + "", args);
	}

	public static String joinWithSplit(String split, Object... args) {
		if (null != args && args.length != 0) {
			boolean hasSplit = isNotEmpty(split);
			StringBuilder result = new StringBuilder(args[0].toString());

			for (int i = 1; i < args.length; ++i) {
				if (hasSplit) {
					result.append(split);
				}

				result.append(args[i]);
			}

			return result.toString();
		} else {
			return "";
		}
	}

	public static String toLowerStr(String str) {
		return isNotEmpty(str) ? str.toLowerCase() : str;
	}

	public static String toStringWithEmpty(Object inObject) {
		return inObject == null ? "" : inObject.toString();
	}

	public static String subTwentyChar(String text) {
		String resultStr = "";

		for (int i = 0; validateStrByLength(resultStr) != 19 && validateStrByLength(resultStr) != 20
				&& validateStrByLength(resultStr) != 21
				&& validateStrByLength(resultStr) != 22; resultStr = text.substring(0, i)) {
			++i;
		}

		return resultStr;
	}

	public static int validateStrByLength(String strParameter) {
		int temp_int = 0;
		byte[] b = strParameter.getBytes();

		for (int i = 0; i < b.length; ++i) {
			if (b[i] >= 0) {
				++temp_int;
			} else {
				temp_int += 2;
				++i;
			}
		}

		return temp_int;
	}

	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	public static String dateToStr(Date dateDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	public static String dateToStringToStr(String dateDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		Date dateString = null;

		try {
			dateString = sdf.parse(dateDate);
		} catch (ParseException var5) {
			// logger.error("StringUtil.dateToStringToStr", "日期转换失败：" + var5.getMessage(), var5, (Object) null);
		}

		if (null != dateString) {
			return isNotEmpty(format) ? dateToStr(dateString, format) : dateToStr(dateString, "yyyy-MM-dd HH:mm");
		} else {
			return null;
		}
	}

	public static String dateToStringToStr(String dateDate, String format, String srcFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormat, Locale.US);
		Date dateString = null;

		try {
			dateString = sdf.parse(dateDate);
		} catch (ParseException var6) {
			// logger.error("StringUtil.dateToStringToStr", "日期转换失败：" + var6.getMessage(), var6, (Object) null);
		}

		if (null != dateString) {
			return isNotEmpty(format) ? dateToStr(dateString, format) : dateToStr(dateString, "yyyy-MM-dd HH:mm");
		} else {
			return null;
		}
	}

	public static String getHexString(byte[] b) {
		String result = "";

		for (int i = 0; i < b.length; ++i) {
			result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
		}

		return result;
	}

	public static String getPrePackageOfPackageName(String packageName) {
		return "com.yum.boh." + getModelNameFormPackageName(packageName);
	}

	public static String getModelNameFormPackageName(String packageName) {
		packageName = packageName.substring(12);
		packageName = packageName.substring(0, packageName.indexOf("."));
		return packageName;
	}

	public static String getPageNameByUrl(String strUrl) {
		String strResult = null;
		if (!isEmpty(strUrl)) {
			int beginIndex = strUrl.lastIndexOf("/");
			int endIndex = strUrl.lastIndexOf("_");
			int endPointIndex = strUrl.lastIndexOf(".");
			String type = null;
			if (endPointIndex != -1) {
				type = strUrl.substring(endPointIndex + 1);
			}

			if (!isEmpty(type) && type != null && type.equalsIgnoreCase("jsp")) {
				if (beginIndex > 0 && endPointIndex > 0) {
					strResult = strUrl.substring(beginIndex + 1, endPointIndex);
				}
			} else if (beginIndex > 0 && endIndex > 0) {
				strResult = strUrl.substring(beginIndex + 1, endIndex);
			}
		}

		return strResult;
	}

	public static String filterUnderLineForClassName(String value) {
		String returnValue = null;
		if (isNotEmpty(value) && value.indexOf("_") >= 0) {
			returnValue = value.substring(0, value.indexOf("_"));
		} else {
			returnValue = value;
		}

		return returnValue;
	}

	private static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		} else {
			int l = strhex.length();
			if (l % 2 == 1) {
				return null;
			} else {
				byte[] b = new byte[l / 2];

				for (int i = 0; i != l / 2; ++i) {
					b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
				}

				return b;
			}
		}
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 255);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}

		return hs.toUpperCase();
	}

	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		return cs1 == null ? cs2 == null : cs1.equals(cs2);
	}

	public static String getStringValue(Object o) {
		return o == null ? null : o.toString();
	}
	
	public static String fixZeroBeforeString(String str, int length) {
	    StringBuffer result = new StringBuffer();
	    int strLength = 0;
	    if (StringUtils.isNotEmpty(str)) {
	        strLength = str.length();
	    } else {
	        str = "";
	    }
	    if (strLength < length) {
	        for (int i = 0; i < length - strLength; i++) {
	            result.append("0");
	        }
	    }
	    result.append(str);
	    return result.toString();
	}

}
