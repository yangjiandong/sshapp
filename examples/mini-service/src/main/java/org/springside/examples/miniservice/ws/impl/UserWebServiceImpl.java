package org.springside.examples.miniservice.ws.impl;

import java.util.List;

import javax.jws.WebService;

import org.dozer.DozerBeanMapper;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.service.account.AccountManager;
import org.springside.examples.miniservice.ws.UserWebService;
import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.AuthUserResult;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetAllUserResult;
import org.springside.examples.miniservice.ws.result.GetUserResult;
import org.springside.examples.miniservice.ws.result.WSResult;

import com.google.common.collect.Lists;

/**
 * WebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "org.springside.examples.miniservice.ws.UserWebService", targetNamespace = WsConstants.NS)
public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = LoggerFactory.getLogger(UserWebServiceImpl.class);

	private AccountManager accountManager;

	private DozerBeanMapper dozer;

	/**
	 * @see UserWebService#getAllUser()
	 */
	public GetAllUserResult getAllUser() {

		GetAllUserResult result = new GetAllUserResult();

		//获取User列表并转换为UserDTO列表.
		try {
			List<User> userEntityList = accountManager.getAllInitedUser();
			List<UserDTO> userDTOList = Lists.newArrayList();

			for (User userEntity : userEntityList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}
			result.setUserList(userDTOList);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#getUser()
	 */
	public GetUserResult getUser(Long id) {
		GetUserResult result = new GetUserResult();

		//校验请求参数
		try {
			Assert.notNull(id, "id参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//获取用户
		try {
			User entity = accountManager.getInitedUser(id);
			UserDTO dto = dozer.map(entity, UserDTO.class);

			result.setUser(dto);
			return result;
		} catch (ObjectNotFoundException e) {
			String message = "用户不存在(id:" + id + ")";
			logger.error(message, e);
			return result.buildResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#createUser(UserDTO)
	 */
	public CreateUserResult createUser(UserDTO user) {
		CreateUserResult result = new CreateUserResult();

		//校验请求参数
		try {
			Assert.notNull(user, "用户参数为空");
			Assert.hasText(user.getLoginName(), "新建用户登录名参数为空");
			Assert.isNull(user.getId(), "新建用户ID参数必须为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//保存用户
		try {
			User userEntity = dozer.map(user, User.class);
			accountManager.saveUser(userEntity);
			result.setUserId(userEntity.getId());
			return result;
		} catch (ConstraintViolationException e) {
			String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
			logger.error(message, e);
			return result.buildResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#authUser(String, String)
	 */
	public AuthUserResult authUser(String loginName, String password) {
		AuthUserResult result = new AuthUserResult();

		//校验请求参数
		try {
			Assert.hasText(loginName, "登录名参数为空");
			Assert.hasText(password, "密码参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//认证
		try {
			if (accountManager.authenticate(loginName, password)) {
				result.setValid(true);
			} else {
				result.setValid(false);
			}
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setDozer(DozerBeanMapper dozer) {
		this.dozer = dozer;
	}
}
