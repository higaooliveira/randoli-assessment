package model;

import lombok.Data;

import java.util.UUID;

@Data
public class EventModel {

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
