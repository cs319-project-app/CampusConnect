package com.fullstackfidelity.campusconnect.entities.item;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonationItem extends Item {
    private String donatorId;
    private String recipientId;
    private LocalDate takenDate;
}
