package com.searshomeservices.ns.util;

import com.searshomeservices.ns.entity.ServiceOrder;
import com.searshomeservices.ns.entity.ServiceOrderSubscriber;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * Created by jchen9 on 11/18/16.
 */
@Component
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        config.exposeIdsFor(ServiceOrder.class, ServiceOrderSubscriber.class);
    }
}