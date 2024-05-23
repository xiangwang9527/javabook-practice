package cn.javabook.chapter15.service;

import cn.javabook.chapter15.common.ServerResponse;
import cn.javabook.chapter15.exception.ServiceException;
import cn.javabook.chapter15.utils.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * IdempotentService实现类
 * 
 */
@Service
public class IdempotentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final Integer EXPIRE_TIME_DAY = 60 * 60 * 24;

	@Autowired
	private JedisUtils jedisUtil;

	public ServerResponse createToken() {
		String str = UUID.randomUUID().toString().replaceAll("-", "");
		StringBuilder token = new StringBuilder();
		token.append(str);
		jedisUtil.set(token.toString(), token.toString(), EXPIRE_TIME_DAY);
		return ServerResponse.success(token.toString());
	}

	public boolean checkToken(HttpServletRequest request) {
		// 获取Authorization头部值
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.startsWith("Bearer")) {
			// 分割Authorization头部值
			String[] parts = authorization.trim().split(" ");
			if (parts.length == 2) {
				// 获取Bearer令牌
				String token = parts[1];
				if (!jedisUtil.exists(token)) {
					throw new ServiceException("请勿重复操作");
				}
				Long del = jedisUtil.del(token);
				logger.warn("token: {} 被删除", token);
				if (del <= 0) {
					throw new ServiceException("请勿重复操作");
				}
			} else {
				throw new ServiceException("参数不合法");
			}
		} else {
			throw new ServiceException("参数不合法");
		}
		return true;
	}

	public ServerResponse testIdempotence() {
		return ServerResponse.success("幂等验证: 成功");
	}
}
