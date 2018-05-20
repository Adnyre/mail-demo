package adnyre.maildemo.controller;

import adnyre.maildemo.dto.CampaignDto;
import adnyre.maildemo.dto.MessageTemplateDto;
import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/{id}")
    @ResponseBody
    public CampaignDto getAddressee(@PathVariable long id) {
        Campaign addressee = campaignService.get(id);
        log.debug("Found campaign: {}", addressee);
        return CampaignDto.fromEntity(addressee);
    }

    @PostMapping
    @ResponseBody
    public CampaignDto saveAddressee(@RequestBody CampaignDto dto) {
        Campaign addressee = campaignService.save(dto);
        log.debug("Saved campaign successfully");
        return CampaignDto.fromEntity(addressee);
    }

    @PutMapping
    @ResponseBody
    public CampaignDto updateAddressee(@RequestBody CampaignDto dto) {
        Campaign addressee = campaignService.update(dto);
        log.debug("Updated campaign successfully");
        return CampaignDto.fromEntity(addressee);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressee(@PathVariable long id) {
        campaignService.delete(id);
        log.debug("Deleted campaign with id: {}", id);
    }

    @GetMapping("/{id}/message-template")
    public MessageTemplateDto getMessageTemplate(@PathVariable long id) {
        MessageTemplate template = campaignService.getMessageTemplate(id);
        log.debug("Got message template for campaign#{}", id);
        return MessageTemplateDto.fromEntity(template);
    }

    @PostMapping("/{id}/message-template")
    @ResponseBody
    public MessageTemplateDto saveMessageTemplate(
            @RequestBody MessageTemplateDto dto,
            @PathVariable long id) {
        MessageTemplate template = campaignService.saveTemplate(id, dto);
        log.debug("Saved message template for campaign#{} successfully", id);
        return MessageTemplateDto.fromEntity(template);
    }

    @DeleteMapping("/{id}/message-template")
    public void deleteMessageTemplate(@PathVariable long id) {
        campaignService.removeTemplate(id);
        log.debug("Deleted message template for campaign#{} successfully", id);
    }
}