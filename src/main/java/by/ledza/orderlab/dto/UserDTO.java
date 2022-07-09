package by.ledza.orderlab.dto;


import lombok.Data;

import java.util.Map;

@Data
public class UserDTO {

    private String id;

    private String photo_max;

    private String first_name;

    private String last_name;

    private boolean can_access_closed;

    private boolean is_closed;
}
