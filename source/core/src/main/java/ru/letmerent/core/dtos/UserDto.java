package ru.letmerent.core.dtos;

import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String userName;
    private List<OrderDto> orders;

    public UserDto (User user){
        this.id=user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.orders = user.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
