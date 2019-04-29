package by.tut.mdcatalog.week4app.service.converter.impl;

import by.tut.mdcatalog.week4app.repository.model.ItemStatusEnum;
import by.tut.mdcatalog.week4app.repository.model.Item;
import by.tut.mdcatalog.week4app.service.converter.ItemConverter;
import by.tut.mdcatalog.week4app.service.model.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public Item fromItemDTO(ItemDTO itemDTO) {
        return new Item(
                itemDTO.getId(),
                itemDTO.getName(),
                ItemStatusEnum.valueOf(itemDTO.getStatus()),
                itemDTO.getDeleted()
        );
    }

    @Override
    public ItemDTO fromItem(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getItemStatusEnum().name(),
                item.isDeleted()
        );
    }
}
