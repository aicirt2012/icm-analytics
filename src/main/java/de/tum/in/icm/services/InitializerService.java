package de.tum.in.icm.services;

@WebListener
public class InitializerService implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        // Do stuff during webapp's startup.
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Do stuff during webapp's shutdown.
    }

}
