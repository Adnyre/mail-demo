package adnyre.maildemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignStatsView {
    private long campaignId;
    private String campaignName;
    private String userName;
    private long sentEmails;
    private long potentialAddressees;
}