package com.example.refugeeshelter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Date startDate;
    private Date endDate;
    private Long roomId;
}
