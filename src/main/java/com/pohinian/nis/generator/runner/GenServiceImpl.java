package com.pohinian.nis.generator.runner;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenServiceImpl extends AbstractGenJava {
    public String makeJava(String tableName) {
        String cllAgreeStorage = genClassNamePreFix(tableName);
        return genPackageName(tableName) + "\n"
            + "import com.fasterxml.jackson.databind.ObjectMapper;\n"
            + "import java.util.Map;\n"
            + "import javax.validation.Valid;\n"
            + "import lombok.RequiredArgsConstructor;\n"
            + "import org.springframework.stereotype.Service;\n"
            + "\n"
            + "@Service\n"
            + "@RequiredArgsConstructor\n"
            + "public class " + cllAgreeStorage + "ServiceImpl "
            + " implements " + cllAgreeStorage + "Service {\n"
            + "\n"
            + "    private final " + cllAgreeStorage + "Dao dao;\n"
            + "    private static final String QUERY_ID = \"" + cllAgreeStorage + "DAO\";\n"
            + "\n"
            + "    @Override\n"
            + "    public " + cllAgreeStorage + "VO find(Map<String, Object> param) {\n"
            + "        ObjectMapper mapper = new ObjectMapper();\n"
            + "        return mapper.convertValue(dao.select(QUERY_ID + \".find\", param), "
            + cllAgreeStorage + "VO.class);\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    public Object insert(@Valid " + cllAgreeStorage + "VO entity) {\n"
            + "        return dao.insert(QUERY_ID + \".insert\", entity);\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    public Object update(@Valid " + cllAgreeStorage + "VO entity) {\n"
            + "        return dao.update(QUERY_ID + \".update\", entity);\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    public int delete(@Valid " + cllAgreeStorage + "VO entity) {\n"
            + "        return dao.delete(QUERY_ID + \".delete\", entity);\n"
            + "    }\n"
            + "}";

    }
}
