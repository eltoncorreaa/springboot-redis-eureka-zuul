package com.elton.app.zuulapi.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class AccessLogFilter extends ZuulFilter {

	Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		final HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		final HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

		LOGGER.info("REQUEST :: < " + request.getScheme() + " " + request.getLocalAddr() + ":" + request.getLocalPort());
		LOGGER.info("REQUEST :: < " + request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol());
		LOGGER.info("RESPONSE:: > HTTP:" + response.getStatus());

		return null;
	}
}