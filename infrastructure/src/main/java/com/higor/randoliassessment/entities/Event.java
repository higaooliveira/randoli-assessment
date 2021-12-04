package com.higor.randoliassessment.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name="event")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventId;

    private UUID transId;

    private String transTms;

    private String rcNum;

    private String clientId;

    private Integer eventCnt;

    private String locationCd;

    private String locationId1;

    private String locationId2;

    private String addrNbr;
}
