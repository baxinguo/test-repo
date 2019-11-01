package com.tedu.sp11;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.tedu.web.util.JsonResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class ItemServiceFallback implements FallbackProvider{

	@Override
	public String getRoute() {
	    /**
	     * 路由规则，返回一个service-id
	     * 当前降级类，只对指定的微服务有效
	     */
		return "item-service";
		//return null  对所有服务有效
		//return ""
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		//降级响应的数据封装成一个Respose对象
		return response();
	}

	private ClientHttpResponse response() {
		// TODO Auto-generated method stub
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers=new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				String json=JsonResult.err("后台服务不可用").toString();
				return new ByteArrayInputStream(json.getBytes());
				
			}
			
			@Override
			public String getStatusText() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK.getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.OK.value();
			}
			
			@Override
			public void close() {
				
				
			}
		};
	}

}
