package com.searshomeservices.ns.util;

import com.searshomeservices.ns.entity.ServiceOrder;
import com.searshomeservices.ns.entity.ServiceOrderPk;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by jchen9 on 11/18/16.
 */
@Component
public class CustomBackendIdConverter implements BackendIdConverter {
    //TODO: using entityType to determine pk format

    @Override
    public Serializable fromRequestId(String id, Class<?> entityType) {
        if (id == null) return null;
        String[] parts = id.split("_");
        return new ServiceOrderPk(parts[0], parts[1]);
    }

    @Override
    public String toRequestId(Serializable source, Class<?> entityType) {
        ServiceOrderPk id = (ServiceOrderPk) source;
        return String.format("%s_%s", id.getServiceOrderNumber(), id.getServiceUnitNumber());
    }

    @Override
    public boolean supports(Class<?> type) {
        return ServiceOrder.class.equals(type);
    }
}