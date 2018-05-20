package adnyre.maildemo.dto;

import adnyre.maildemo.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private long id;

    private String name;

    private String email;

    private String pass;

    private String smtpHost;

    private String imapHost;

    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .smtpHost(entity.getSmtpHost())
                .imapHost(entity.getImapHost())
                .build();
    }
}