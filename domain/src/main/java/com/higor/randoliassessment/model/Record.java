package com.higor.randoliassessment.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record {
    private UUID transId;
    private String transTms;
    private String rcNum;
    private String clientId;
    private List<EventModel> event;
}
