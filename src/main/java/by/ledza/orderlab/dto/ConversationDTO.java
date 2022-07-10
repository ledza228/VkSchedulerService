package by.ledza.orderlab.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vk.api.sdk.objects.messages.ConversationWithMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ConversationDTO {

    private Object peer;

    private Object chatSettings;
}
