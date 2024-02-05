package com.fullstackfidelity.campusconnect.entities.item;

import com.fullstackfidelity.campusconnect.entities.item.Item;
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
public class SecondHandSalesItem extends Item {
    private String dealerId;
    private String recipientId;
    private double itemAge;
    private double itemPrice;
    private LocalDate saleTime;
}
