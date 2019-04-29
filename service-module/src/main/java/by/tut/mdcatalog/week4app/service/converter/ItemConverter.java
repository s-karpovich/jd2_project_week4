package by.tut.mdcatalog.week4app.service.converter;

import by.tut.mdcatalog.week4app.repository.model.Item;
import by.tut.mdcatalog.week4app.service.model.ItemDTO;

public interface ItemConverter {
    Item fromItemDTO(ItemDTO itemDTO);

    ItemDTO fromItem(Item item);
}
