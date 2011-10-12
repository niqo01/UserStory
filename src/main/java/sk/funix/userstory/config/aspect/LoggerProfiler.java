package sk.funix.userstory.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log every call for every service or dao methods.
 * @author Nicolas Milliard
 *
 */
public class LoggerProfiler {

    /** Log some informations about the execution of a method.
     * @param pjp JoinPoint
     * @return Result
     * @throws Throwable Any Exception
     */
    public final Object profile(final ProceedingJoinPoint pjp)
    	throws Throwable {

    	final Logger logger = LoggerFactory.getLogger(pjp.getTarget()
    			.getClass());
    	if (logger.isDebugEnabled()) {
    		logger.debug("Class: {}, Start method: {}",
    				pjp.getTarget().getClass().getCanonicalName(),
    				pjp.getSignature().getName());
    	}
        Object output = pjp.proceed();
    	if (logger.isDebugEnabled()) {
    		logger.debug("Class: {}, End method: {}",
    				pjp.getTarget().getClass().getCanonicalName(),
    				pjp.getSignature().getName());
    	}
        return output;
    }
}
