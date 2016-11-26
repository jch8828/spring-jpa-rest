package com.searshomeservices.ns.repository;

import com.searshomeservices.ns.entity.ServiceOrder;
import com.searshomeservices.ns.entity.ServiceOrderPk;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jchen9 on 11/17/16.
 */
@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface ServiceOrderRepository extends
        PagingAndSortingRepository<ServiceOrder, ServiceOrderPk> {
}