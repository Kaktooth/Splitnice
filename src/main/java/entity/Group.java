package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class Group extends BusinessEntity implements Identifiable {

    @NonNull
    private final Integer id;

    @Setter
    @NonNull
    private String title;

    @Setter
    @NonNull
    private Integer creatorId;

    @NonNull
    BigDecimal moneyAmount;
}
