package adnyre.maildemo.service.impl;

import adnyre.maildemo.dao.AddresseeDao;
import adnyre.maildemo.dto.AddresseeDto;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.Keyword;
import adnyre.maildemo.service.AddresseeService;
import adnyre.maildemo.service.KeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Slf4j
@Service
public class AddresseeServiceImpl implements AddresseeService {

    @Autowired
    private AddresseeDao addresseeDao;
    @Autowired
    private KeywordService keywordService;

    @Override
    @Transactional(readOnly = true)
    public Addressee get(long id) {
        return addresseeDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Addressee getByEmail(String email) {
        return addresseeDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Addressee> getByEmails(Set<String> emails) {
        return addresseeDao.findByEmailIn(emails);
    }

    @Override
    @Transactional
    public Addressee save(AddresseeDto dto) {
        Assert.isNull(dto.getId(), "Can save only new entities");
        Addressee entity = new Addressee();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        List<Keyword> keywords = keywordService.convertToKeywords(dto.getKeywords());
        entity.setKeywords(new HashSet<>(keywords));
        addresseeDao.save(entity);
        return entity;
    }

    @Override
    @Transactional
    public Addressee update(AddresseeDto dto) {
        Assert.notNull(dto.getId(), "Addressee id should not be null");
        Addressee entity = addresseeDao.findOne(dto.getId());
        Assert.notNull(entity, "Can't find addressee by id: " + dto.getId());
        Assert.isTrue(dto.getId().equals(entity.getId()), "Dto and entity ids are different");
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        List<Keyword> keywords = keywordService.convertToKeywords(dto.getKeywords());
        entity.setKeywords(new HashSet<>(keywords));
        return entity;
    }

    @Override
    @Transactional
    public void delete(long id) {
        addresseeDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Addressee> selectNewAddresseesForCampaign(long campaignId) {
        return addresseeDao.selectNewAddresseesForCampaign(campaignId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Addressee> selectAllAddresseesForCampaign(long campaignId) {
        return addresseeDao.selectAllAddresseesForCampaign(campaignId);
    }
}