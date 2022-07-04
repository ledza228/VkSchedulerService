package by.ledza.orderlab.vk;

import by.ledza.orderlab.exceptions.VkTokenException;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.ConversationWithMessage;
import com.vk.api.sdk.objects.messages.responses.GetConversationsResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
public class VkUser {

    private String vk_key;
    private UserActor actor;

    VkApiClient vk = new VkApiClient(new HttpTransportClient());

    public VkUser(String vk_key) {
        this.vk_key = vk_key;
        actor = new UserActor(new Random().nextInt(1000),vk_key);
    }


    public List<ConversationWithMessage> getConversations(Integer amount)  {
        try {
            GetConversationsResponse response = vk.messages().getConversations(actor).count(amount).execute();
            return response.getItems();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new VkTokenException("User token error");
        }
    }

    public void sendMessage(Integer conversationId, String message) throws ClientException, ApiException {
        vk.messages().send(actor).chatId(conversationId).message(message).randomId(0).execute();
    }

}
