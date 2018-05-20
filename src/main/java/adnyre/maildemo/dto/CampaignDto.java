package adnyre.maildemo.dto;

import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.model.Keyword;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CampaignDto {
    private Long id;
    private String name;
    private Long userId;
    private List<String> keywords;

    public static CampaignDto fromEntity(Campaign entity) {
        return CampaignDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .userId(entity.getUser().getId())
                .keywords(entity.getKeywords().stream()
                        .map(Keyword::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}