package com.example.refugeeshelter.dto.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonFilter("reservationsFilter")
public class ReservationResponse {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Object rooms;
    private UserResponse user;

    public ReservationResponse(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
