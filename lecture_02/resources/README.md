# Application servers, WAR and EAR archives, servlets.

## Application servers
- software suite that implements part (Web Profile) or whole(Full profile) EE specification
- provides container runtime environment, services and resources (not JVM)
- Glassfish, WebSphere, Wildfly/JBoss AS, (TomEE)

## Packaging
packed into units for deployment on any java EE-platform compliant
unit contains component/s and optional descriptor

## Packaging applications
- packed as JAR or WAR/EAR (all standard JAR with extra extension)

## Packaging Web Archives - WAR
- smallest deployable and usable unit of web resource
- contains web components and static content (css,js,…)
- structure:
  - top level directory → **document root**
    - contains XHTML,static files…
    - WEB-INF folder
      - contains deployment descriptor (web.xml and impl. specific deployment descriptor)
      - lib folder with libraries
      - classes folder with .class files

![](https://d2mxuefqeaa7sj.cloudfront.net/s_310B8538313DC59F20D0BF501BBA1B1122FE1B1BFCB5A45F5340EB29A02F2262_1472724642991_file.png)


## Packaging EJBs - within JAR
- can be used as module 

![](https://d2mxuefqeaa7sj.cloudfront.net/s_310B8538313DC59F20D0BF501BBA1B1122FE1B1BFCB5A45F5340EB29A02F2262_1472670301054_file.png)

## Packaging EJBs - within WAR
- beans deployed within WAR file, have JNDI name prefixed by application namespace
- beans packed in WAR are in WEB-INF/classes as all compiled source or within the WEB-INF/lib folder
- the ejb-jar.xml (DD) is not required in this case
- If beans are packed as jar with ejb-jar.xml , the DD is not considered

## Packaging Resource Adapter Archives
- resource adapter archive
- uses extension .rar
- can be deployed as part of EAR or standalone
- the content of RAR is accessible for all server components (even if deployed as part of EAR)


## EAR
  - contains EE modules + optional deployment descriptor with .xml extension
  - deployment descriptors Java EE + runtime (implementation specific) dep. descriptor in META-INF package
  - Java EE modules
    - .jar with optional EJB deal. descriptor
    - .war (web archive) contains web modules
    - .rar (resource adapter archive), interfaces + native libs + optional depl. descriptor

![](https://d2mxuefqeaa7sj.cloudfront.net/s_310B8538313DC59F20D0BF501BBA1B1122FE1B1BFCB5A45F5340EB29A02F2262_1472669655708_file.png)

## Servlets
- web component generating dynamic content
- request/response model
- live in web container

## WebServlet
- extends `javax.servlet.http.HttpServlet`
- defined by `@WebServlet` annotation or web.xml descriptor
- deployment descriptor overrides annotation
- servlet name by default is full class name (package/myservlet)
- possible dynamic registration via `ServletContextListener`, `ServletContainerInitializer`

```java
    @WebServlet("myservlet")
    public class MyServlet extends HttpServlet {
    }
```

- Override method means supported that HTTP method
- To support HTTP GET method you override `doGet`, to support HTTP POST method you override `doPost`
   
```java
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }
```
## HttpServletRequest
- contains data from request
- request headers, request parameters, request body

## HttpServletResponse
- handles the response for recieved request

## Servlet lifecycle
- class loaded by application startup
- servlet is initialized
- call `init()` of servlet
- call `destroy()` before it is destroyed by context
- could be modified by overriding lifecycle methods

## Servlet filters
- defined by annotation @WebFilter or in web.xml descriptor
- implements javax.servlet.Filter
- used for logging, security, ...
- url pattern defined where the filter is applied
- possible dynamic registration via `ServletContextListener`,`ServletContainerInitializer`

```java
    @WebFilter("/*")
    public class LoggingFilter implements javax.servlet.Filter {
        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        } 
    }
```

## Event Listeners
- defined by annotation @WebFilter or in web.xml descriptor
- used to handle servlet lifecycle callbacks
- possible dynamic registration via `ServletContextListener`,`ServletContainerInitializer`


## Asynchronous Support
- since Servlet 3.0 API
- saves server resources
- usefull for long running processes
- servlet must support async (asyncSupported = true)
- the completness of request processing must be explicitly confirmed by Async Context#complete

```java
    @WebServlet(urlPatterns = "/async", asyncSupported = true)
```

## Asynchronous servlet - example
```java
    @WebServlet(urlPatterns = "/async/", asyncSupported = true)
    public class AsyncServlet extends HttpServlet {
    
    private ExecutorService executor;

    @Override
    public void init() throws ServletException {
        this.executor = Executors.newSingleThreadExecutor();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
            //Represents the current execution context
            final AsyncContext ac = request.startAsync();
    
            //Listener handles the async context events
            ac.addListener(new AsyncListener() {
                @Override
                public void onComplete(AsyncEvent asyncEvent) throws IOException {
                    log.info("Completed");
                }
                //...
            });
    
             //
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //... long running process
                    ac.complete();
                }
            });
        }
    }
```

## Servlet 3.1 - Nonblocking I/O
- EE 7 feature
- ServletInputStream and ServletOutputStream provides setXXXListener
- Invoking setXXXListener methods indicates that nonblocking I/O is used instead of traditional.

```java
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
            final AsyncContext ac = request.startAsync();
            ServletOutputStream os = response.getOutputStream();
    
            os.setWriteListener(new WriteListener() {
                @Override
                public void onWritePossible() throws IOException {
                    os.write("Write if possible";.getBytes());
                    ac.complete();
                }
    
                @Override
                public void onError(Throwable throwable) {
                    log.error("Error occured &#34", throwable);
                }
            });
        }
```

## Using ServletContainerInitializer interface
- class must implement `ServletContainerInitializer`
- the service provider is defined in META-INF/services/ folder
- class implementing the interface must be part of separate JAR in WEB-INF/lib folder

