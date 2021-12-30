package entity;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Balance implements Identifiable {

    @NonNull
    Integer id;

    @NonNull
    BigDecimal amount;

    @NonNull
    Integer balanceCurrencyId;

    @NonNull
    Integer ownerId;
}
