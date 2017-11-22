package com.zcc.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zcc.netty.server.FirstNettyServer;

/**
 * web容器生命周期监听器
 *  ServletContextListener 接口，它能够监听 ServletContext 对象的生命周期，实际上就是监听 Web 应用的生命周期
 *  当Servlet 容器启动或终止Web 应用时，会触发ServletContextEvent 事件，该事件由ServletContextListener 来处理。
 * @author zcc
 *
 * @date 2017年11月9日
 */

public class WebContextListener implements ServletContextListener {
	
	//使用一个单线程池来管理netty服务线程
	ExecutorService executor = Executors.newSingleThreadExecutor();
	

	/**
	 * 当Servlet容器启动Web应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化， 
	 * 并且对那些在Web应用启动时就需要被初始化的Servlet进行初始化。  
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		final FirstNettyServer nettyServer = new FirstNettyServer();
		
		//此处要开启一个线程去启动netty服务，否则netty服务会一直阻塞，导致项目起不来
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					nettyServer.start();
				} catch (InterruptedException e) {
					System.out.println("error ....");
					e.printStackTrace();
				}
			}
		});
	}

	/** 
	 * 当Servlet容器终止Web应用时调用该方法。在调用该方法之前，容器会先销毁所有的Servlet和Filter过滤器。 
	 */ 
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
