package cn.javabook.chapter15.controller;

import cn.javabook.chapter15.annotation.Idempotent;
import cn.javabook.chapter15.common.ServerResponse;
import cn.javabook.chapter15.entity.User;
import cn.javabook.chapter15.service.IdempotentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 并发压力测试Controller
 *
 */
@Api(description = "幂等验证相关Api")
@RestController
@RequestMapping("/idempotent")
public class IdempotentController extends Thread {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IdempotentService idempotentService;

	/**
	 * 先通过接口得到token
	 *
	 * @return ServerResponse
	 */
	@ApiOperation(value = "通过接口得到token")
	@GetMapping("token")
	public ServerResponse token() {
		return idempotentService.createToken();
	}

	/**
	 *  再测试幂等
	 *
	 * @return ServerResponse
	 */
	@ApiOperation(value = "测试幂等")
	@Idempotent
	@PostMapping("again")
	public ServerResponse testIdempotence() {
		return idempotentService.testIdempotence();
	}

	/**
	 * 用户查询
	 *
	 * @return User
	 */
	@GetMapping("user")
	public User getUser() {
		return new User();
	}

//	// 并发压力测试
//	@ApiIdempotent
//	@PostMapping(value = "idempotence")
//	public ServerResponse idempotence() {
//		Runnable runnable = new Runnable() {
//			@Override
//			public synchronized void run() {
//				// 开100个线程
//				int count = 10;
//				ExecutorService executorService = Executors.newFixedThreadPool(count);
//				for (int i = 1; i < count + 1; i++) {
//					executorService.execute(new TestTask(testService));
//					logger.warn("启动第{}个线程", i);
//				}
//			}
//		};
//		runnable.run();
//		return ServerResponse.success();
//	}
//
//	public class TestTask implements Runnable {
//		private IdempotentService idempotentService;
//
//		public TestTask(IdempotentService idempotentService) {
//			this.idempotentService = idempotentService;
//		}
//
//		@SuppressWarnings("static-access")
//		@Override
//		public void run() {
//			for (int j = 0; j < 10; j++) {
//				try {
//					// 创建任务
//					idempotentService.testIdempotence();
//					logger.warn("创建第{}个任务", j);
//					Thread.currentThread().sleep(100);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
