package ag.orca.living.core.entity.org;

import ag.orca.living.core.entity.BaseEntity;
import ag.orca.living.util.JsonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgExemptWord extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 免审词名称
     */
    private String name;

    /**
     * 免审词列表
     */
    private List<String> words;


    public String getWords() {
        return JsonUtil.beanToJson(words);
    }

    public List<String> getWordList() {
        return words;
    }

    @SuppressWarnings("all")
    public void setWords(String words) {
        this.words = JsonUtil.jsonToBean(words, List.class);
    }


}
