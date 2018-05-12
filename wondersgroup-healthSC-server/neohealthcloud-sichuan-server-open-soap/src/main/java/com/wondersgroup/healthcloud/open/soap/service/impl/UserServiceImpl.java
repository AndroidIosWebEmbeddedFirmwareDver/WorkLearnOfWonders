package com.wondersgroup.healthcloud.open.soap.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.wondersgroup.healthcloud.open.soap.entity.User;
import com.wondersgroup.healthcloud.open.soap.service.UserService;
/**
 * @ClassName:UserServiceImpl
 * @Description:测试服务接口实现类
 */
@WebService(serviceName="UserService",//对外发布的服务名
			targetNamespace="http://service.soap.open.healthcloud.wondersgroup.com",//指定你想要的名称空间，通常使用使用包名反转
			endpointInterface="com.wondersgroup.healthcloud.open.soap.service.UserService")//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
@Component
public class UserServiceImpl implements UserService{

	private Map<String, User> userMap = new HashMap<String, User>();
	public UserServiceImpl() {
	    System.out.println("向实体类插入数据");
	    User user = new User();
	    user.setUserId(UUID.randomUUID().toString().replace("-", ""));
		user.setUserName("test1");
		user.setEmail("maplefix@163.xom");
	    userMap.put(user.getUserId(), user);
	    
	    user = new User();
	    user.setUserId(UUID.randomUUID().toString().replace("-", ""));
		user.setUserName("test2");
		user.setEmail("maplefix@163.xom");
	    userMap.put(user.getUserId(), user);
	    
	    user = new User();
	    user.setUserId(UUID.randomUUID().toString().replace("-", ""));
		user.setUserName("test3");
		user.setEmail("maplefix@163.xom");
	    userMap.put(user.getUserId(), user);
	}
	@Override
	public String getUserName(String userId) {
	    return "test" + userId;
	}
	@Override
	public User getUser(String userId) {
	    System.out.println("userMap是:"+userMap);
	    return userMap.get(userId);
	}

}
