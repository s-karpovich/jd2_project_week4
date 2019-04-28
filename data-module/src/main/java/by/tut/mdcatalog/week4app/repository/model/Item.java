package by.tut.mdcatalog.week4app.repository.model;

public class Item {
    private Long id;
    private String name;
    private ItemStatusEnum itemStatusEnum;
    private Boolean isDeleted;

    public Item(Long id, String name, ItemStatusEnum itemStatusEnum, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.itemStatusEnum = itemStatusEnum;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStatusEnum getItemStatusEnum() {
        return itemStatusEnum;
    }

    public void setItemStatusEnum(ItemStatusEnum itemStatusEnum) {
        this.itemStatusEnum = itemStatusEnum;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
