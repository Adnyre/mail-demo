package adnyre.maildemo.service.impl;

import adnyre.maildemo.dao.CampaignDao;
import adnyre.maildemo.dao.UserDao;
import adnyre.maildemo.dto.CampaignDto;
import adnyre.maildemo.dto.CampaignStatsView;
import adnyre.maildemo.dto.MessageTemplateDto;
import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.model.Keyword;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;
import adnyre.maildemo.service.CampaignService;
import adnyre.maildemo.service.KeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Slf4j
@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignDao campaignDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private KeywordService keywordService;

    @Override
    @Transactional(readOnly = true)
    public Campaign get(long id) {
        return campaignDao.findOne(id);
    }

    @Override
    @Transactional
    public Campaign save(CampaignDto dto) {
        Assert.isNull(dto.getId(), "Can save only new entities");
        Assert.notNull(dto.getUserId(), "Campaign should be linked to a user");
        Campaign entity = new Campaign();
        entity.setName(dto.getName());
        User user = userDao.findOne(dto.getUserId());
        Assert.notNull(user, "Can't find user by id: " + dto.getUserId());
        entity.setUser(user);
        List<Keyword> keywords = keywordService.convertToKeywords(dto.getKeywords());
        entity.setKeywords(new HashSet<>(keywords));
        return entity;
    }

    @Override
    @Transactional
    public Campaign update(CampaignDto dto) {
        Assert.notNull(dto.getId(), "Campaign id should not be null");
        Campaign entity = campaignDao.findOne(dto.getId());
        Assert.notNull(entity, "Can't find campaign by id: " + dto.getId());
        Assert.isTrue(dto.getId().equals(entity.getId()), "Dto and entity ids are different");
        Assert.notNull(dto.getUserId(), "Campaign should be linked to a user");
        entity.setName(dto.getName());
        if (!dto.getUserId().equals(entity.getUser().getId())) {
            User user = userDao.findOne(dto.getUserId());
            Assert.notNull(user, "Can't find user by id: " + dto.getUserId());
            entity.setUser(user);
        }
        List<Keyword> keywords = keywordService.convertToKeywords(dto.getKeywords());
        entity.setKeywords(new HashSet<>(keywords));
        return entity;
    }

    @Override
    @Transactional
    public void delete(long id) {
        campaignDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MessageTemplate getMessageTemplate(long campaignId) {
        Campaign campaign = campaignDao.findOne(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        return campaign.getMessageTemplate();
    }

    @Override
    @Transactional
    public MessageTemplate saveTemplate(long campaignId, MessageTemplateDto dto) {
        Campaign campaign = campaignDao.findOne(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        MessageTemplate template = campaign.getMessageTemplate() != null
                ? campaign.getMessageTemplate()
                : new MessageTemplate();
        template.setSubject(dto.getSubject());
        template.setTemplate(dto.getTemplate());
        campaign.setMessageTemplate(template);
        return template;
    }

    @Override
    @Transactional
    public void removeTemplate(long campaignId) {
        Campaign campaign = campaignDao.findOne(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        campaign.setMessageTemplate(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignStatsView> getAllCampaignStats() {
        return campaignDao.getCampaignStats();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignStatsView> getCampaignStatsByUserId(long userId) {
        return campaignDao.getCampaignStatsByUserId(userId);
    }

}