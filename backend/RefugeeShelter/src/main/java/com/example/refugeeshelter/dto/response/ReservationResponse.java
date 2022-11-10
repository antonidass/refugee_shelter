package com.example.refugeeshelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonFilter("reservationsFilter")
public class ReservationResponse {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Object rooms;
    private UserDTO user;

    public ReservationResponse(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
