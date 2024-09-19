package grey.fable.vocabulary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 词汇服务启动类.
 *
 * @author GreyFable
 * @since 2024/9/18 11:50
 */
@SpringBootApplication
@MapperScan("grey.fable.**.dao")
public class VocabularyApplication {
    public static void main(String[] args) {
        SpringApplication.run(VocabularyApplication.class, args);
    }
}
