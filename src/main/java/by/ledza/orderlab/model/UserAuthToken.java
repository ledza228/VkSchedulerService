package by.ledza.orderlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserAuthToken {
    @Id
    private String id;

    @Column(length=1024)
    private String authToken;

}
