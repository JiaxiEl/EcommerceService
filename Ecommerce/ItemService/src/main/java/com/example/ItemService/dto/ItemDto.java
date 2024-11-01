package com.example.ItemService.dto;
import com.example.ItemService.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String itemId;
    private String name;
    private String description;
    private String price;
    private String upc;
    private String imageUrl;
    private int availableUnits;
    private String category;
    public static ItemDto fromEntity(Item item) {
        return new ItemDto(
                item.getItemId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getUpc(),
                item.getImageUrl(),
                item.getAvailableUnits(),
                item.getCategory()
        );
    }

    public static Item toEntity(ItemDto itemDto) {
        return Item.builder()
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
