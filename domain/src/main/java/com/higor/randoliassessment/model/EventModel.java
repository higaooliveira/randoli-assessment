package com.higor.randoliassessment.model;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventModel {
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
