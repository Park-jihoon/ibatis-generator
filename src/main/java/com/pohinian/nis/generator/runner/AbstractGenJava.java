package com.pohinian.nis.generator.runner;

import org.python.google.common.base.CaseFormat;

public abstract class AbstractGenJava {

    public static String genPackageName(String tableName) {
        String lowName = CaseFormat.UPPER_UNDERSCORE
            .to(CaseFormat.LOWER_CAMEL, tableName)
            .toLowerCase();
        return "package com.jitpower.pms.nis."
            + lowName.substring(0, 3) + ".cmm."
            + lowName.substring(3) + ";\n";
    }

    public static String genClassNamePreFix(String tableName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
    }
}
