package ru.letmerent.core.dtos;


import com.sun.jdi.IntegerType;

import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class InstrumentDto {
//1. Такая штука, что для общей таблицы (список всех инструментов) нам, по идее, достаточно из информации о
// владельце только какое-то одно его поле (например, userName). И еще для общей таблицы не нужно описание,
//не нужны все картинки и все заказы. Т.е. для общей таблицы надо намного меньше инфо, чем, для
// страницы с подробной инфо об инструменте.
//Может, сделаем потом две отдельные Dto-шки - краткую для общей таблицы и обширную для страницы инструмента.
//2. Также для легковесности, может, вместо UserDto owner сюда организуем отдельную стопку полей, которые мы
//используем на фронте из UserDtо : String ownerUserName=owner.getUserName, String ownerFirstName=owner.getFirstname  и т.д.
    private Long id;
    private String title;
    private String brand;
    private String producerName;
    private String description;
    private BigDecimal price;
    private BigDecimal fee;
    private UserDto owner;
//    private List<Picture> pictures;
//    private Picture avatarPicture;
    private List<String> picturesUrls; //пока вижу, что оптимально на фронт кидать url картинки
    private String avatarPictureUrl;
    private String categoryName;
    private List<OrderItemDto> orderItems;  // отсюда на страницу инструмента будет выцепляться инфо для календаря занятости


    public InstrumentDto (Instrument instrument) {
        this.id = instrument.getId();
        this.title = instrument.getTitle();
        this.brand = instrument.getBrand();
        this.producerName = instrument.getProducer().getName();
        this.description = instrument.getDescription();
        this.price = instrument.getPrice();
        this.fee = instrument.getFee();
        this.owner = new UserDto(instrument.getOwner());
        this.avatarPictureUrl = instrument.getPictures().get(0).getUrl(); //Todo: предусмотреть, что делать, если нет картинок, или сделать заполнение картинок обязательным (я бы сделала заполнение картинок обязательным)
        this.picturesUrls = instrument.getPictures().stream().map(u -> u.getUrl).collect(Collectors.toList());
        this.categoryName = instrument.getCategory().getName();
        this.orderItems = instrument.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
