package ru.letmerent.core.dtos;


import com.sun.jdi.IntegerType;

@NoArgsConstructor
@Data
public class InstrumentDto {
// Такая штука, что для общей таблицы (список всех инструментов) нам, по идее, достаточно из информации о
    // владельце только какое-то одно его поле (например, userName). И еще для общей таблицы не нужно описание,
    //не нужны все картинки и все заказы. Т.е. для общей таблицы надо намного меньше инфо, чем, для
    // страницы с подробной инфо об инструменте.
    //Может, есть смысл сделать две отдыельные Dto-шки - краткую для общей таблицы и обширную для страницы инструмента?
    //Насколько это корректно вообще? Или нет в этом никакой нигде экономии, а только плодим классы?
    private Long id;
    private String title;
    private String brand;
    private String producerName;
    private String description;
    private BigDecimal price;
    private BigDecimal fee;
    private User owner;
    private List<Picture> pictures;
    private Picture avatarPicture;
    private String categoryName;
    private List<OrderItem> orderItems;  // отсюда на страницу инструмента будет выцепляться инфо для календаря занятости


    public InstrumentDto (Instrument instrument) {
        this.id = instrument.getId();
        this.title = instrument.getTitle();
        this.brand = instrument.getBrand();
        this.producerName = instrument.getProducer().getName();
        this.description = instrument.getDescription();
        this.price = instrument.getPrice();
        this.fee = instrument.getFee();
        this.owner = instrument.getOwner();
        this.avatarPicture = instrument.getPictures().get(0); //Todo: предусмотреть, что делать, если нет картинок, или сделать заполнение картинок обязательным
        this.pictures = instrument.getPictures();
        this.categoryName = instrument.getCategory().getName();
        this.orderItems = instrument.getOrderItems();
    }
}
