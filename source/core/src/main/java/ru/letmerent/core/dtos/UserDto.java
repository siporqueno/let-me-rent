package ru.letmerent.core.dtos;

@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String userName;
    private List<Order> orders;

    public UserDto (User user){
        this.id=user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.orders = user.getOrders();
    }
}
