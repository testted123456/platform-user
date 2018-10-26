package com.nonobank.platformuser.remotecontroller;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nonobank.platformuser.component.result.Result;

@FeignClient(value="PLATFORM-ZUUL")
public interface RemoteZuul {

	@PostMapping(value="zuul/initURLMap", consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result initUrlMap(@RequestBody Map<String, Map<String, String>> urlMap);
}
