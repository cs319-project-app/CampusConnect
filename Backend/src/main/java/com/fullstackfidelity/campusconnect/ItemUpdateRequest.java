package com.fullstackfidelity.campusconnect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateRequest {
    private Optional<String> itemName;
    private Optional<String> imagePath;
    private Optional<String> description;
    private Optional<Double> itemAge;
    private Optional<Double> itemPrice;
}
