package com.rest.api.boonyarisRestApi.config;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class ClientDatasourceConfig implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		if (bean instanceof DataSource) {
			ProxyFactory factory = new ProxyFactory(bean);
			factory.setProxyTargetClass(true);
			factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
			return factory.getProxy();
		}
		return bean;
	}

	private static class ProxyDataSourceInterceptor implements MethodInterceptor {
		private final DataSource dataSource;

		public ProxyDataSourceInterceptor(final DataSource dataSource) {
			super();
			this.dataSource = ProxyDataSourceBuilder.create(dataSource).listener(new QueryLoggingListener()).build();
		}

		@Override
		public Object invoke(final MethodInvocation invocation) throws Throwable {
			Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(), invocation.getMethod().getName());
			if (proxyMethod != null) {
				return proxyMethod.invoke(dataSource, invocation.getArguments());
			}
			return invocation.proceed();
		}
	}
}

class QueryLoggingListener implements QueryExecutionListener {
	private static final Logger logger = LogManager.getLogger(QueryLoggingListener.class);
	private static final String MODULE_SUB = "moduleSub";
	private static final String DURATION = "duration";

	@Override
	public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		MDC.put(MODULE_SUB, "db");
		for (QueryInfo queryInfo : queryInfoList) {
			logger.info("-->(db)    : {}", queryInfo.getQuery());
		}
	}

	@Override
	public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
		MDC.put(DURATION, "" + execInfo.getElapsedTime());
		try {
			if (!execInfo.isSuccess()) {
				logger.error("<--(db)    : ", execInfo.getThrowable());
			}
			for (QueryInfo queryInfo : queryInfoList) {
				logger.info("<--(db)    : {}", queryInfo.getQuery());
			}
		} finally {
			MDC.remove(DURATION);
			MDC.remove(MODULE_SUB);
		}

	}

}
