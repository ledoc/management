package fr.treeptik.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import fr.treeptik.model.Mesure;
import fr.treeptik.model.TrameDW;

@Aspect
@Component
public class LoginAuditAspect {

	private Logger logger = Logger.getLogger(LoginAuditAspect.class);

	@AfterReturning(pointcut="execution(* fr.treeptik.dao.*.save(..))", returning="result")
	public void logAfterSave(JoinPoint joinPoint, Object result){
		
		System.out.println(joinPoint.getArgs()[0].getClass());
		
		if (! (joinPoint.getArgs()[0] instanceof TrameDW) || 
				! (joinPoint.getArgs()[0] instanceof Mesure) ){
			
			SecurityContext context = SecurityContextHolder.getContext();
			String loginName = context.getAuthentication().getName();
			
			logger.info("- " + loginName + 
					" - Save / Update - " + result.getClass().getSimpleName() + " - " + result );
			
		}
		
	}
	
	@AfterReturning(pointcut="execution(* fr.treeptik.service.*.remove(..))", returning="result")
	public void logAfterDelete(JoinPoint joinPoint){
		
		SecurityContext context = SecurityContextHolder.getContext();
		String loginName = context.getAuthentication().getName();
		
		Object id = joinPoint.getArgs()[0];
		String declaringTypeName = joinPoint.getSignature().toShortString();
		
		logger.info("- " + loginName + 
				" - Delete - " + declaringTypeName +" - id " + id );
		
	}
	
	
}