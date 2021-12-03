package com.higor.randoliassessment.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="event")

public class Event {
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
