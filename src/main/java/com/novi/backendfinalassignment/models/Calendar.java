package com.novi.backendfinalassignment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendars")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean availableTime;



    @OneToOne(mappedBy = "calendar")
    private Treatment treatment;

    public boolean isAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(boolean availableTime) {
        this.availableTime = availableTime;
    }


}
/* public static final int MINUTES_IN_ONE_TIME_SLOT = 1;

        public static Calendar getCalendarOf(int slot) {
            Calendar c = Calendar.getInstance();
            int minutes = slot * MINUTES_IN_ONE_TIME_SLOT;
            c.set(Calendar.HOUR_OF_DAY, minutes / 60);
            c.set(Calendar.MINUTE, minutes % 60);
            return c;
        }
*/


