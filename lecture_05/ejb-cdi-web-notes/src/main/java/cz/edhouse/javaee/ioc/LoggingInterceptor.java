package cz.edhouse.javaee.ioc;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Frantisek Spacek
 */
@LogAround
@Interceptor
public class LoggingInterceptor {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logAndExecute(InvocationContext context) throws Exception {
        LOG.log(Level.INFO, String.format("Execution of method %s started", context.getMethod().getName()));
        final long start = System.currentTimeMillis();
        final Object result = context.proceed();
        LOG.log(Level.INFO, String.format("Execution of method %s finished ", context.getMethod().getName()));
        LOG.log(Level.INFO, String.format("Execution of method %s took %sms", context.getMethod().getName(),
                System.currentTimeMillis() - start));

        return result;
    }
}
