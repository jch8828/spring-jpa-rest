package com.searshomeservices.ns.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by jchen9 on 11/18/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ServiceOrderPk implements Serializable{
    private String serviceOrderNumber;
    private String serviceUnitNumber;
}