package br.com.fiap.tripcanvas.activity;

import lombok.Data;

@Data
public class Activity {

    private String title;
    private String description;
    private int durationInMinutes;
    private ActivityType type;

}
