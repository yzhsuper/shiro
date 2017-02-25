package org.shiro.test;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class Tutorial {

	private static final transient Logger log = Logger.getLogger(Tutorial.class);

	public static void main(String[] args) {
		log.info("My First Apache Shiro Application");

		// 1.
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

		// 2.
		SecurityManager securityManager = factory.getInstance();

		// 3.
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject currentUser = SecurityUtils.getSubject();
		
		Session session = currentUser.getSession();
		session.setAttribute( "someKey", "aValue" );
		
		if ( !currentUser.isAuthenticated() ) {
		    //collect user principals and credentials in a gui specific manner
		    //such as username/password html form, X509 certificate, OpenID, etc.
		    //We'll use the username/password example here since it is the most common.
		    UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa1");

		    //this is all you have to do to support 'remember me' (no config - built in!):
		    token.setRememberMe(true);

		    try {
		        currentUser.login( token );
		    } catch ( UnknownAccountException uae ) {
		    	log.error("账户不存在");
		    } catch ( IncorrectCredentialsException ice ) {
		    	log.error("密码错误");
		    } catch ( LockedAccountException lae ) {
		    	log.error("帐号被锁定");
		    } catch ( AuthenticationException ae ) {
		    	log.error("没有权限");
		    }
		}
		System.exit(0);
	}

}