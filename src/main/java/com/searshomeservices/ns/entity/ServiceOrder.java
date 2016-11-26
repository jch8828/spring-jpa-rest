package com.searshomeservices.ns.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jchen9 on 11/17/16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity(name = "order")
@Table(name = "service_order")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceOrder implements Serializable{
    @EmbeddedId
    private ServiceOrderPk id;

    private String serviceEta;

    private Date scheduleDate;

    private String timeWindow;

    @Column(name = "is_canceled")
    private boolean canceled;

    @Column(name = "is_closed")
    private boolean closed;

    @Column(name = "is_tech_en_route")
    private boolean techEnRoute;

    @Column(name = "is_tech_arrived")
    private boolean techArrived;

    private String closedCallCode;

    private String orderPostalCode;

    private String timeZonePostalCode;

    private String locationDeviceId;

    private Date created;

    private Date modified;
}
