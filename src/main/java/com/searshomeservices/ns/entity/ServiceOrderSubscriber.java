package com.searshomeservices.ns.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jchen9 on 11/14/16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity(name = "orderSubscriber")
@Table(name = "service_order_subscriber")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceOrderSubscriber implements Serializable{
    @Id
    @Column(name = "subscriber_id")
    private String id;

    private String serviceOrderNumber;

    private String serviceUnitNumber;

    private String label;

    private String notificationType;

    private String notificationId;

    private String confirmationNumber;

    private Date created;

    private Date modified;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name="serviceOrderNumber",insertable = false, updatable = false),
            @JoinColumn(name="serviceUnitNumber",insertable = false, updatable = false)
    })
    private ServiceOrder order;
}
