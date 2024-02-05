package com.fullstackfidelity.campusconnect.entities.item;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowedItem extends Item {

    private String borrowerId;
    private String lenderId;
    private LocalDate takenDate=LocalDate.now();
    private LocalDate returnDate;
    private LocalDate expectedReturnDate=takenDate.plusMonths(1); //1-month period to return the item;

    private String holderId;
    private boolean hold;
    final private long EXTRA_TIME=7; //if no one holds, extra 7 days could be added
}
