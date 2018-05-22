package adnyre.maildemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignStatsView {
    private long campaignId;
    private String campaignName;
    private String userName;
    private long sentEmails;
    private long potentialAddressees;
}