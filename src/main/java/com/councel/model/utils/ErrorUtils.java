package com.councel.model.utils;

import java.util.HashMap;
import java.util.Map;

public class ErrorUtils {

	public static Map<String,Integer> errorIds = new HashMap<String,Integer>();
	
	static{
		errorIds.put(ErrorCode.USER_NOT_FOUND_WITH_ID, 1001);
		errorIds.put(ErrorCode.USER_NOT_REGISTERED, 1002);
		errorIds.put(ErrorCode.USER_PASSWORD_INCORRECT, 1003);
	}
}
