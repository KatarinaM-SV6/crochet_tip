package com.ftn.sbnz.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {
    private Integer daily;
    private Integer weekly;
    private Integer monthly;
    private Integer done;
    private Integer n;
}
