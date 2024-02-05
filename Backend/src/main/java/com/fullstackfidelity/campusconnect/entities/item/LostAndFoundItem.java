package com.fullstackfidelity.campusconnect.entities.item;

import com.fullstackfidelity.campusconnect.entities.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LostAndFoundItem extends Item {
    private String founderId;
    private String location = "G Binası";
    private String foundInLocation;
    private String foundTime;

}
