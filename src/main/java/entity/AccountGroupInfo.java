package entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
@Builder(toBuilder = true)
public class AccountGroupInfo implements Identifiable {

    @NonNull
    private final Integer id;

    @Setter
    @NonNull
    private Integer roleId;

    @NonNull
    private final Integer addedById;

    @NonNull
    private final Integer groupId;

    @NonNull
    private final Integer accountId;
}
