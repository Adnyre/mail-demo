package adnyre.maildemo.dao;

import adnyre.maildemo.dto.CampaignStatsView;

import java.util.List;

public interface CustomCampaignDao {
    List<CampaignStatsView> getCampaignStats();
    List<CampaignStatsView> getCampaignStatsByUserId(long userId);
}