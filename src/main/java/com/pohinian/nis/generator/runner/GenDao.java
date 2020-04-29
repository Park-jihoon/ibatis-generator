package com.pohinian.nis.generator.runner;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenDao extends AbstractGenJava{
    public String makeJava(String tableName) {
        return genPackageName(tableName)
            + "\n"
            + "import egovframework.rte.psl.dataaccess.EgovAbstractDAO;\n"
            + "import org.springframework.stereotype.Repository;\n"
            + "\n"
            + "@Repository\n"
            + "public class " + genClassNamePreFix(tableName) + "Dao extends EgovAbstractDAO {\n"
            + "}"
            ;
    }
}
