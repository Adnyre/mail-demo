package adnyre.maildemo.service.impl;

import adnyre.maildemo.dao.KeywordDao;
import adnyre.maildemo.model.Keyword;
import adnyre.maildemo.service.KeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordDao keywordDao;

    @Override
    public List<Keyword> convertToKeywords(List<String> keywordNames) {
        List<Keyword> keywords = new ArrayList<>();
        Set<String> keywordNameSet = new HashSet<>(keywordNames);
        Map<String, Keyword> keywordMap =
                keywordDao.findByNameIn(new HashSet<>(keywordNames)).stream()
                        .collect(Collectors.toMap(
                                Keyword::getName,
                                Function.identity()
                        ));
        keywordNameSet.forEach(x -> {
            if (keywordMap.containsKey(x)) {
                keywords.add(keywordMap.get(x));
            } else {
                Keyword keyword = keywordDao.save(new Keyword(x));
                keywords.add(keyword);

                // TODO add to parent entity
            }
        });
        return keywords;
    }
}