package com.fullstackfidelity.campusconnect.DTO;

import com.fullstackfidelity.campusconnect.entities.item.LostAndFoundItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LostAndFoundItemDTO {
    private LocalDate postDate;
    private LocalDate retrieveDate;
    private long itemId;
    private String itemName;
    private String imagePath;
    private String description;
    private String status;
    private String location;
    private String foundInLocation;
    private String typeOfLost;
    private String idLoserName;
    private String idLoserDepartment;
    private long userId;
    private String username;
    public LostAndFoundItemDTO(LostAndFoundItem item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.imagePath = item.getImagePath();
        this.description = item.getDescription();
        this.status = item.getStatus();
        this.location = item.getLocation();
        this.foundInLocation = item.getFoundInLocation();
//        this.typeOfLost = item.getTypeOfLost();
//        this.idLoserName = item.getLoserId();
//        this.idLoserDepartment = item.getIdLoserDepartment();
        this.postDate = item.getPostDate();
        this.retrieveDate = item.getRetrieveDate();
        if (item.getUser() != null) {
            this.userId = item.getUser().getUser_id();
            this.username = item.getUser().getUsername();
        }
    }
}
