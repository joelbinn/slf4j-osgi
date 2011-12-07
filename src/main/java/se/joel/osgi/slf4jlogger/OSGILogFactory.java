package se.joel.osgi.slf4jlogger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Implementation of {@link ILoggerFactory} over {@link LogService}.
 * <p/>
 * {@inheritDoc}
 */
public class OSGILogFactory implements ILoggerFactory {
    static private OSGiLogger logger = new OSGiLogger();
    private static ServiceReference serviceref = null;
    private static ServiceTracker logServiceTracker;
    private static BundleContext context;

    public static void initOSGI(BundleContext context) {
        initOSGI(context, null);
    }

    public static void initOSGI(BundleContext context, ServiceReference servref) {
        OSGILogFactory.context = context;
        serviceref = servref;
        if (context != null) {
            logServiceTracker = new ServiceTracker(context, LogService.class.getName(), null);
            logServiceTracker.open();
        }
    }


    static LogService getLogService() {
        if (logServiceTracker != null) {
            return (LogService) logServiceTracker.getService();
        }
        return null;
    }

    static ServiceReference getServiceReference() {
        return serviceref;
    }

    static BundleContext getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    public Logger getLogger(String name) {
        return logger;
    }
}
