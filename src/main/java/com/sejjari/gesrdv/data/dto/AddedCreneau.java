package com.sejjari.gesrdv.data.dto;

import com.sejjari.gesrdv.data.entity.Creneau;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddedCreneau {

    private Date startDate;
    private Date endDate;
    private Long center_id;
    private boolean defaulte=true;
    private ArrayList<Creneau> creneaus=new ArrayList<Creneau>();


}
