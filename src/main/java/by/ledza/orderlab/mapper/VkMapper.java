package by.ledza.orderlab.mapper;

import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserFull;
import org.mapstruct.Mapper;

@Mapper
public interface VkMapper {

    public User fullUserToCommonUser(UserFull full);

}
