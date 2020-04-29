package com.pohinian.nis.generator.runner;

import org.python.google.common.base.CaseFormat;

public abstract class AbstractGenJava {

    public static String genPackageName(String tableName) {
        return "package com.jitpower.pms.nis.cll.cmm."
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName).toLowerCase()
            .substring(3) + ";\n";
    }

    public static String genClassNamePreFix(String tableName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
    }


}
