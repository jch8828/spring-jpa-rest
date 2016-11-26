package com.searshomeservices.ns.entity;

import org.springframework.data.rest.core.config.Projection;

/**
 * Created by jchen9 on 11/21/16.
 */
@Projection(name = "orderDetail" , types = ServiceOrderSubscriber.class)
public interface ServiceOrderSubscriberOrderDetailProjection {
    String getNotificationId();
    ServiceOrder getOrder();
}