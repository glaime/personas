package ar.com.demo.reba.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDTO {

    private Integer codigo;
    private String mensaje;

}
