package grey.fable.vocabulary.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import grey.fable.base.collection.CollectionUtils;
import grey.fable.base.collection.ListUtils;
import grey.fable.base.net.Response;
import grey.fable.base.text.StringUtils;
import grey.fable.base.util.IdUtils;
import grey.fable.vocabulary.pojo.dto.VocabularyDTO;
import grey.fable.vocabulary.pojo.dto.VocabularyPosDTO;
import grey.fable.vocabulary.pojo.entity.VocabularyDO;
import grey.fable.vocabulary.pojo.entity.VocabularyPosDO;
import grey.fable.vocabulary.pojo.query.VocabularyPosQuery;
import grey.fable.vocabulary.pojo.query.VocabularyQuery;
import grey.fable.vocabulary.pojo.vo.VocabularyPosVO;
import grey.fable.vocabulary.pojo.vo.VocabularyVO;
import grey.fable.vocabulary.service.VocabularyPosService;
import grey.fable.vocabulary.service.VocabularyService;
import grey.fable.vocabulary.util.VocabularyMapStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 词汇 Controller.
 *
 * @author GreyFable
 * @since 2024/9/18 11:57
 */
@RestController
@RequestMapping("/vocabularies")
public class VocabularyController {

    /**
     * 词汇服务.
     */
    private final VocabularyService vocabularyService;

    /**
     * 词性服务.
     */
    private final VocabularyPosService vocabularyPosService;

    /**
     * 转换映射.
     */
    private final VocabularyMapStruct mapStruct;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService,
                                VocabularyPosService vocabularyPosService,
                                VocabularyMapStruct mapStruct) {
        this.vocabularyService = vocabularyService;
        this.vocabularyPosService = vocabularyPosService;
        this.mapStruct = mapStruct;
    }

    @GetMapping
    public Response<Page<VocabularyVO>> listVocabularies(VocabularyQuery query) {
        // 如果存在中文查询, 则根据中文查询结果获取对应的单词 ID.
        Set<Long> wordIds = new HashSet<>();
        if (StringUtils.isNotBlank(query.getTranslation())) {
            List<VocabularyPosDO> vocabularyPosDOS = vocabularyPosService.listVocabularyPos(VocabularyPosQuery
                    .builder().translation(query.getTranslation()).build());
            wordIds = vocabularyPosDOS.stream().map(VocabularyPosDO::getWordId).collect(Collectors.toSet());
        }

        // 将查询到的单词 ID 添加到查询条件中, 同时组合查询对应的单词.
        query.getIds().addAll(wordIds);
        Page<VocabularyDO> vocabularyDOPage = vocabularyService.listVocabularies(query);
        List<VocabularyDO> vocabularyDOS = vocabularyDOPage.getRecords();

        // 通过查询到的单词 ID 查询对应的词性翻译.
        List<Long> ids = vocabularyDOS.stream().map(VocabularyDO::getId).toList();
        List<VocabularyPosDO> vocabularyPosDOS = vocabularyPosService.listVocabularyPos(VocabularyPosQuery.builder()
                .wordIds(ids).build());
        // 将查询到的词性翻译按单词 ID 组合到一起.
        Map<Long, List<VocabularyPosVO>> vocabularyPosVOMap = new HashMap<>();
        for (VocabularyPosDO vocabularyPosDO : vocabularyPosDOS) {
            Long wordId = vocabularyPosDO.getWordId();
            VocabularyPosVO vocabularyPosVO = mapStruct.convertVocabularyPos(vocabularyPosDO);

            if (vocabularyPosVOMap.containsKey(wordId)) {
                vocabularyPosVOMap.get(wordId).add(vocabularyPosVO);
            } else {
                List<VocabularyPosVO> vocabularyPosVOS = ListUtils.of(vocabularyPosVO);
                vocabularyPosVOMap.put(wordId, vocabularyPosVOS);
            }
        }

        // 将查询到的单词和对应词性翻译组合到一起.
        List<VocabularyVO> vocabularyVOS = new ArrayList<>();
        for (VocabularyDO vocabularyDO : vocabularyDOS) {
            VocabularyVO vocabularyVO = mapStruct.convertVocabulary(vocabularyDO);
            vocabularyVO.setVocabularyPosVOS(vocabularyPosVOMap.get(vocabularyDO.getId()));
            vocabularyVOS.add(vocabularyVO);
        }

        // 封装结果返回.
        Page<VocabularyVO> vocabularyVOPage = mapStruct.convertVocabulary(vocabularyDOPage);
        vocabularyVOPage.setRecords(vocabularyVOS);
        return Response.success(vocabularyVOPage);
    }

    @PostMapping
    @Transactional
    public Response<Integer> saveVocabulary(@RequestBody VocabularyDTO vocabularyDTO) {
        long wordId = IdUtils.getSnowflakeNextId();
        LocalDateTime now = LocalDateTime.now();

        VocabularyDO vocabularyDO = mapStruct.convertVocabulary(vocabularyDTO);
        vocabularyDO.setId(wordId);
        vocabularyDO.setCreateTime(now);
        vocabularyDO.setUpdateTime(now);
        Integer savedVocabulary = vocabularyService.saveVocabulary(vocabularyDO);

        List<VocabularyPosDTO> vocabularyPosDTOS = vocabularyDTO.getVocabularyPosDTOS();
        if (CollectionUtils.isNotEmpty(vocabularyPosDTOS)) {
            List<VocabularyPosDO> vocabularyPosDOS = mapStruct.convertVocabularyPos(vocabularyPosDTOS);
            for (VocabularyPosDO vocabularyPosDO : vocabularyPosDOS) {
                vocabularyPosDO.setId(IdUtils.getSnowflakeNextId());
                vocabularyPosDO.setWordId(wordId);
                vocabularyPosDO.setCreateTime(now);
                vocabularyPosDO.setUpdateTime(now);
            }
            vocabularyPosService.saveVocabularyPos(vocabularyPosDOS);
        }
        return Response.success(savedVocabulary);
    }

    @PutMapping
    @Transactional
    public Response<Integer> updateVocabulary(@RequestBody VocabularyDTO vocabularyDTO) {
        LocalDateTime now = LocalDateTime.now();

        // 更新词汇.
        VocabularyDO vocabularyDO = mapStruct.convertVocabulary(vocabularyDTO);
        vocabularyDO.setUpdateTime(now);
        Integer updatedVocabulary = vocabularyService.updateVocabulary(vocabularyDO);

        // 更新词汇词性.
        List<VocabularyPosDTO> vocabularyPosDTOS = vocabularyDTO.getVocabularyPosDTOS();
        if (CollectionUtils.isNotEmpty(vocabularyPosDTOS)) {
            List<VocabularyPosDO> vocabularyPosDOS = mapStruct.convertVocabularyPos(vocabularyPosDTOS);
            for (VocabularyPosDO vocabularyPosDO : vocabularyPosDOS) {
                vocabularyPosDO.setUpdateTime(now);
            }
            vocabularyPosService.updateVocabularyPos(vocabularyPosDOS);
        }
        return Response.success(updatedVocabulary);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Response<Integer> deleteVocabulary(@PathVariable("id") List<String> ids) {
        // 删除词汇时删除对应的词汇词性.
        Integer deletedVocabulary = vocabularyService.deleteVocabulary(ids);
        vocabularyPosService.deleteVocabularyPos(ids, null);
        return Response.success(deletedVocabulary);
    }
}
