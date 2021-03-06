package com.nonobank.platformuser.entity.responseEntity;

import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;

public class ResponseUtil {

	public static ResponseEntity success(Object object) {
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setCode(ResponseCode.SUCCESS.getCode());
		responseEntity.setMsg(ResponseCode.SUCCESS.getMsg());
		responseEntity.setData(object);
		return responseEntity;
	}

	public static ResponseEntity success() {
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setCode(ResponseCode.SUCCESS.getCode());
		responseEntity.setMsg(ResponseCode.SUCCESS.getMsg());
		return responseEntity;
	}

	public static ResponseEntity error(int code, String msg) {
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setCode(code);
		responseEntity.setMsg(msg);
		return responseEntity;
	}
	
	public static ResponseEntity error(int code, String msg, Object object) {
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setCode(code);
		responseEntity.setMsg(msg);
		responseEntity.setData(object);
		return responseEntity;
	}
}
