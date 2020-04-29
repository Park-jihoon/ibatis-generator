package com.pohinian.nis.generator.runner;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenService extends AbstractGenJava {
    public String makeJava(String tableName) {
        return genPackageName(tableName)
            + "\n"
            + "import com.jitpower.pms.nis.cll.NisDefaultService;\n"
            + "\n"
            + "public interface " + genClassNamePreFix(tableName) + "Service "
            + " extends NisDefaultService<" + genClassNamePreFix(tableName) + "VO> {\n"
            + "}\n";
    }
}
