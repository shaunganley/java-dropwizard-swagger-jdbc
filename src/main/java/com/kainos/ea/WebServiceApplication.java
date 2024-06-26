package com.kainos.ea;

import com.kainos.ea.controller.HR;
import com.kainos.ea.dao.EmployeeDao;
import com.kainos.ea.dao.SalesEmployeeDao;
import com.kainos.ea.service.EmployeeService;
import com.kainos.ea.service.SalesEmployeeService;
import com.kainos.ea.util.DatabaseConnector;
import com.kainos.ea.validator.EmployeeValidator;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class WebServiceApplication extends Application<WebServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "WebService";
    }

    @Override
    public void initialize(final Bootstrap<WebServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<WebServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(WebServiceConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final WebServiceConfiguration configuration,
                    final Environment environment) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        environment.jersey().register(new HR(
                        new EmployeeService(new EmployeeDao(), new EmployeeValidator(), databaseConnector),
                        new SalesEmployeeService(new SalesEmployeeDao(), databaseConnector)));
    }

}
