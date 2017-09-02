package cz.edhouse.javaee;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = "async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncServlet.class);

    private ExecutorService executor;

    @Override
    public void init() throws ServletException {
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext ac = req.startAsync();
        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                LOG.info("Async request completed");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                LOG.info("Async request timeout event");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                LOG.info("Async request error event");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                LOG.info("Async request start event");
            }
        });
        executor.execute(() -> {
            LOG.info("Doing something heavy");
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                LOG.error("Unable to complete heavy computation", ex);
            }
            
            LOG.info("I am done");
            
            ac.complete();
        });

    }

}
