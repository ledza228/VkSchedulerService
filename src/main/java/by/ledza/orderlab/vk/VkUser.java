package by.ledza.orderlab.vk;

import by.ledza.orderlab.dto.ConversationDTO;
import by.ledza.orderlab.exceptions.VkTokenException;
import by.ledza.orderlab.mapper.VkMapper;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.messages.ConversationPeer;
import com.vk.api.sdk.objects.messages.ConversationPeerType;
import com.vk.api.sdk.objects.messages.responses.GetConversationsResponse;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserFull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
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


    public List<ConversationDTO> getConversations(Integer amount, Integer offset)  {
        try {
            GetConversationsResponse response = vk.messages().getConversations(actor)
                    .count(amount)
                    .offset(offset)
                    .extended(true)
                    .execute();

            return fillFullConversation(response);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new VkTokenException("User token error");
        }
    }

    @SneakyThrows
    public String getConversationById(Integer id){
        try {
            if (id < 1000) {
                id += 2000000000;
                return vk.messages().getConversationsById(actor, id).execute().getItems().get(0).getChatSettings().getTitle();
            }
            Integer finalId = id;
            UserFull user = vk.messages().getConversationsByIdExtended(actor, id).execute().getProfiles().stream()
                    .filter(u -> u.getId().equals(finalId)).findAny().orElse(null);

            return user.getFirstName() + " " +user.getLastName();
        } catch (Exception e){
            e.printStackTrace();
            return id.toString();
        }
    }

    public void sendMessage(Integer conversationId, String message) throws ClientException, ApiException {
        if (conversationId < 100000000)
            vk.messages().send(actor).chatId(conversationId).message(message).randomId(0).execute();
        else
            vk.messages().send(actor).userId(conversationId).message(message).randomId(0).execute();
    }

    private User getUserDetails(GetConversationsResponse resp, Integer userId){
        UserFull full = resp.getProfiles().stream().filter(u -> u.getId().equals(userId)).findAny().orElse(null);
        VkMapper mapper = Mappers.getMapper(VkMapper.class);
        return mapper.fullUserToCommonUser(full);
    }

    private GroupFull getGroupDetails(GetConversationsResponse resp, Integer groupId){
        return resp.getGroups().stream().filter(g -> g.getId().equals(groupId)).findAny().orElse(null);
    }

    private List<ConversationDTO> fillFullConversation(GetConversationsResponse response){
        List<ConversationDTO> conversations = new ArrayList<>();
        response.getItems().forEach(c -> {
            ConversationDTO conv;
            ConversationPeer peer = c.getConversation().getPeer();

            if (peer.getType().equals(ConversationPeerType.USER))
                conv = new ConversationDTO(peer, getUserDetails(response, peer.getId()));
            else if (peer.getType().equals(ConversationPeerType.GROUP))
                conv = new ConversationDTO(peer, getGroupDetails(response, peer.getId()));
            else
                conv = new ConversationDTO(peer, c.getConversation().getChatSettings());

            conversations.add(conv);
        });
        return conversations;
    }

}
