package adnyre.maildemo.dto;

import adnyre.maildemo.model.MessageTemplate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageTemplateDto {

    private String subject;

    private String template;

    public MessageTemplateDto fromEntity(MessageTemplate entity) {
        return MessageTemplateDto.builder()
                .subject(entity.getSubject())
                .template(entity.getTemplate())
                .build();

    }
}