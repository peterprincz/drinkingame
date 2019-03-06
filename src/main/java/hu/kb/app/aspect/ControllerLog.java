package hu.kb.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Configuration
public class ControllerLog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("execution(* hu.kb.app.controller.*.*(..))")
    public void beforeRequestProcess(JoinPoint joinPoint) {
        logger.info("Following endpoint was called: " + joinPoint.getSignature());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(joinPoint.getArgs()).forEach(sb::append);
        logger.info("with the following parameters: " +sb.toString());
    }
}
