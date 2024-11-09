package com.example.ItemService.dto;

import com.example.ItemService.entity.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private String itemId;
    private String name;
    private String description;
    private String price;
    @NotBlank(message = "UPC cannot be empty")
    private String upc;
    private String imageUrl;
    private int availableUnits;
    private String category;

    public static ItemDto fromEntity(Item item) {
        return ItemDto.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .upc(item.getUpc())
                .imageUrl(item.getImageUrl())
                .availableUnits(item.getAvailableUnits())
                .category(item.getCategory())
                .build();
    }

    public static Item toEntity(ItemDto itemDto) {
        return Item.builder()
                .itemId(itemDto.getItemId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .price(itemDto.getPrice())
                .upc(itemDto.getUpc())
                .imageUrl(itemDto.getImageUrl())
                .availableUnits(itemDto.getAvailableUnits())
                .category(itemDto.getCategory())
                .build();
    }
}
