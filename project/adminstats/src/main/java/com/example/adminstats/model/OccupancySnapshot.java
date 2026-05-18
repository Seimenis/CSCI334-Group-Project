package com.example.adminstats.model;

import java.time.Instant;
import java.util.*;

public class OccupancySnapshot {
    private Long id;
    private Instant timestamp; // EMILE: This is an assumption to make the code compile
                               //        If this is wrong please change it.
    private int lotId;
    private int spotsOccupied;
    private int spotsTotal;
}
