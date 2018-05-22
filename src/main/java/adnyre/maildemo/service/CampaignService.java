package adnyre.maildemo.service;

import adnyre.maildemo.dto.CampaignDto;
import adnyre.maildemo.dto.CampaignStatsView;
import adnyre.maildemo.dto.MessageTemplateDto;
import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.model.MessageTemplate;

import java.util.List;

public interface CampaignService {

    Campaign get(long id);

    Campaign save(CampaignDto dto);

    Campaign update(CampaignDto dto);

    void delete(long id);

    MessageTemplate getMessageTemplate(long campaignId);

    MessageTemplate saveTemplate(long campaignId, MessageTemplateDto dto);

    void removeTemplate(long campaignId);

    List<CampaignStatsView> getAllCampaignStats();

    List<CampaignStatsView> getCampaignStatsByUserId(long userId);
}