package com.searshomeservices.ns.repository;

import com.searshomeservices.ns.entity.ServiceOrderSubscriber;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by jchen9 on 11/14/16.
 */
@RepositoryRestResource(collectionResourceRel = "orderSubscriber", path = "orderSubscriber")
public interface ServiceOrderSubscriberRepository extends
        PagingAndSortingRepository<ServiceOrderSubscriber, String> {
    @RestResource(path = "byNotificationIds")
    List<ServiceOrderSubscriber> findByNotificationIdIn(@Param("notificationIds") List<String> notificationIds);
}
