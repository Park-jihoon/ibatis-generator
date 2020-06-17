package com.pohinian.nis.generator.runner;

import lombok.experimental.UtilityClass;
import org.python.google.common.base.CaseFormat;
import org.springframework.validation.annotation.Validated;

@Validated
@UtilityClass
public class GenServiceImpl extends AbstractGenJava {
    public String makeJava(String tableName) {
        String upperCamelName = genClassNamePreFix(tableName);
        String lowerCamelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
        return genPackageName(tableName) + "\n"
            + "import com.fasterxml.jackson.databind.ObjectMapper;\n"
            + "import org.springframework.cache.annotation.CacheEvict;\n"
            + "import org.springframework.cache.annotation.Cacheable;\n"
            + "import javax.validation.Valid;\n"
            + "import lombok.RequiredArgsConstructor;\n"
            + "import org.springframework.stereotype.Service;\n"
            + "import org.springframework.validation.annotation.Validated;\n"
            + "\n"
            + "@Service\n"
            + "@Validated\n"
            + "@RequiredArgsConstructor\n"
            + "public class " + upperCamelName + "ServiceImpl "
            + " implements " + upperCamelName + "Service {\n"
            + "\n"
            + "    private final " + upperCamelName + "Dao dao;\n"
            + "    private static final String QUERY_ID = \"" + upperCamelName + "DAO\";\n"
            + "\n"
            + "    @Override\n"
            + "    @Cacheable(value = \"" + lowerCamelName
            + "\", key = \"#entity?.genCacheKey()\", \n"
            + "        unless = \"#result == null\")\n"
            + "    public " + upperCamelName + "VO find(" + upperCamelName + "VO entity) {\n"
            + "        ObjectMapper mapper = new ObjectMapper();\n"
            + "        return mapper.convertValue(dao.select(QUERY_ID + \".find\", entity), "
            + upperCamelName + "VO.class);\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    @CacheEvict(value = \"" + lowerCamelName
            + "\", key = \"#entity?.genCacheKey()\")\n"
            + "    public Object insert(@Valid " + upperCamelName + "VO entity) {\n"
            + "        return dao.insert(QUERY_ID + \".insert\", entity);\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    @CacheEvict(value = \"" + lowerCamelName
            + "\", key = \"#entity?.genCacheKey()\")\n"
            + "    public int update(@Valid " + upperCamelName + "VO entity) {\n"
            + "        return dao.update(QUERY_ID + \".update\", entity);\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    @CacheEvict(value = \"" + lowerCamelName
            + "\", key = \"#entity?.genCacheKey()\")\n"
            + "    public int delete(@Valid " + upperCamelName + "VO entity) {\n"
            + "        return dao.delete(QUERY_ID + \".delete\", entity);\n"
            + "    }\n"
            + "}";

    }
}
