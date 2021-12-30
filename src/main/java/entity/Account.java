package entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;


@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)

public class Account extends BusinessEntity implements Identifiable {

    @NonNull
    Integer id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String phone;

    @NonNull
    BigDecimal moneyAmount;
}
