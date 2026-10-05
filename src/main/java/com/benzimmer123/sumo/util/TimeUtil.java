package com.benzimmer123.sumo.util;

public final class TimeUtil {

	public static long getTimeDifference(long millis) {
		return System.currentTimeMillis() - millis;
	}
	
	public static String getTime(int time) {
		int displayhours = (int) (time / 3600);
		int remainder = time % 3600;
		int displayminutes = (int) (remainder / 60);
		int displayseconds = (int) (remainder % 60);
		String displaydisHour = (displayhours < 10 ? "0" : "") + displayhours;
		String displaydisMinu = (displayminutes < 10 ? "0" : "") + displayminutes;
		String displaydisSec = (displayseconds < 10 ? "0" : "") + displayseconds;
		String displayformattedTime;

		if (displayhours > 0)
			displayformattedTime = displaydisHour + ":" + displaydisMinu + ":" + displaydisSec;
		else
			displayformattedTime = displaydisMinu + ":" + displaydisSec;

		return displayformattedTime;
	}
	
}
