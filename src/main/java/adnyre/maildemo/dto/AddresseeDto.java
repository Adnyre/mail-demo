package adnyre.maildemo.dto;

import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.Keyword;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AddresseeDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> keywords;

    public static AddresseeDto fromEntity(Addressee entity) {
        return AddresseeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .keywords(entity.getKeywords().stream()
                        .map(Keyword::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}