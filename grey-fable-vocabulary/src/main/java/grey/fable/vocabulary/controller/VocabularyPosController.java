package grey.fable.vocabulary.controller;

import grey.fable.base.net.Response;
import grey.fable.vocabulary.service.VocabularyPosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 词汇词性 Controller.
 *
 * @author GreyFable
 * @since 2024/9/18 11:57
 */
@RestController
@RequestMapping("/pos")
public class VocabularyPosController {

    private final VocabularyPosService vocabularyPosService;

    @Autowired
    public VocabularyPosController(VocabularyPosService vocabularyPosService) {
        this.vocabularyPosService = vocabularyPosService;
    }

    @DeleteMapping("/{wordId}/{id}")
    @Transactional
    public Response<Integer> deleteVocabulary(@PathVariable("wordId") List<String> wordIds,
                                              @PathVariable("id") List<String> ids) {
        return Response.success(vocabularyPosService.deleteVocabularyPos(wordIds, ids));
    }
}
