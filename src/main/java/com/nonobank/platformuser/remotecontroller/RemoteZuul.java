package com.nonobank.platformuser.remotecontroller;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nonobank.platformuser.component.result.Result;

@FeignClient(value="PLATFORM-ZUUL")
public interface RemoteZuul {

	@GetMapping(value="zuul/initURLMap")
	@ResponseBody
	public Result initUrlMap();
}
