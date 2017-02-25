package org.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class ShiroTest {
	
	@Test
	public void testHelloworld() {
	    //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =
	            new IniSecurityManagerFactory("classpath:shiro.ini");
	    //2、得到SecurityManager实例 并绑定给SecurityUtils
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	    SecurityUtils.setSecurityManager(securityManager);
	    //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
	    Subject subject = SecurityUtils.getSubject();
	    UsernamePasswordToken token = new UsernamePasswordToken("zhang1", "1234");

	    try {
	        //4、登录，即身份验证
	        subject.login(token);
	    } catch (UnknownAccountException e) {
            throw new RuntimeException("账号不存在！", e);
        } catch (DisabledAccountException e) {
            throw new RuntimeException("账号未启用！", e);
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("密码错误！", e);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }

	    Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

	    //6、退出
	    subject.logout();
	}
}
