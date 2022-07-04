package by.ledza.orderlab.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserCreds{

    @Id
    private String id;
    
    private String first_name;

    private String last_name;

    private String photo_max;

    @OneToOne(cascade= CascadeType.ALL)
    private UserAuthToken vk_token;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Order> orders;
}
