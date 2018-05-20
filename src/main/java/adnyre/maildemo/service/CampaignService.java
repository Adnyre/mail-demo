package adnyre.maildemo.service;

import adnyre.maildemo.dto.CampaignDto;
import adnyre.maildemo.dto.MessageTemplateDto;
import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.model.MessageTemplate;

public interface CampaignService {

    Campaign get(long id);

    Campaign save(CampaignDto dto);

    Campaign update(CampaignDto dto);

    void delete(long id);

    MessageTemplate saveTemplate(long campaignId, MessageTemplateDto dto);

    void removeTemplate(long campaignId);
}