package cl.demo.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPhoneDto {
    @Size(min=8)
    private Integer number;
    @Size(min=1)
    private Integer cityCode;
    @Size(min=1)
    private Integer countryCode;
}
