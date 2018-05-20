package adnyre.maildemo.service;

import adnyre.maildemo.model.Keyword;

import java.util.List;

public interface KeywordService {
    List<Keyword> convertToKeywords(List<String> keywordNames);
}