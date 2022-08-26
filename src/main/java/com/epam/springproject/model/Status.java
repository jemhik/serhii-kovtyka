package com.epam.springproject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Status {

    private long id;
    private boolean isBlocked;

}
