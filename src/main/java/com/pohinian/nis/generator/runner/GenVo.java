package com.pohinian.nis.generator.runner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.python.google.common.base.CaseFormat;

@UtilityClass
public class GenVo extends AbstractGenJava {

    public String makeJava(String tableName, List<Map<String, Object>> pkList,
                           List<Map<String, Object>> colList) {
        String cllAgreeStorage = genClassNamePreFix(tableName);
        return genPackageName(tableName)
            + "import com.jitpower.pms.nis.cll.NisDefaultVO;\n"
            + "import javax.validation.constraints.NotBlank;\n"
            + "import java.util.Date;\n"
            + "import lombok.Data;\n"
            + "import lombok.EqualsAndHashCode;\n"
            + "import lombok.NoArgsConstructor;\n"
            + "\n"
            + "@Data\n"
            + "@NoArgsConstructor\n"
            + "@EqualsAndHashCode(callSuper = true)\n"
            + "public class " + cllAgreeStorage + "VO extends NisDefaultVO {\n"
            + "    private static final long serialVersionUID = 1L;\n"
            + makeVoField(pkList, colList) + ";\n"
            + "}";
    }

    private String makeVoField(List<Map<String, Object>> pkList,
                               List<Map<String, Object>> colList) {
        List<String> defaultField = Arrays.asList("REG_ID", "REG_DT", "CHG_ID", "CHG_DT");
        return colList.stream()
            .filter(m -> !defaultField.contains(m.get("COLUMN_NAME")))
            .map(m -> {
            String c = (String) m.get("COLUMN_NAME");
            String n = (String) m.get("NULLABLE");
            String finalC = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, c);
            String dataType = genDataType(m);
            String reStr = "    private " + dataType + " " + finalC;
            reStr = getNotNull(pkList, n, c, reStr);
            return reStr;
        }).collect(Collectors.joining(";\n"));
    }

    private String getNotNull(List<Map<String, Object>> pkList, String n, String c,
                              String reStr) {
        if (pkList.stream().anyMatch(
            pm -> c.equalsIgnoreCase((String) pm.get("COLUMN_NAME")))
            || "N".equalsIgnoreCase(n)) {
            reStr = "    @NotBlank\n" + reStr;
        }
        return reStr;
    }

    private String genDataType(Map<String, Object> m) {
        String dataType = (String) m.get("DATA_TYPE");
        int dataPrecision = m.get("DATA_PRECISION") == null ? 0 : ((BigDecimal) m.get("DATA_PRECISION")).intValue();
        int dataScale = m.get("DATA_SCALE") == null ? 0 : ((BigDecimal) m.get("DATA_SCALE")).intValue();
        if ("VARCHAR2".equalsIgnoreCase(dataType) || "VARCHAR".equalsIgnoreCase(dataType)) {
            return "String";
        } else if ("DATE".equalsIgnoreCase(dataType)) {
            return "Date";
        } else if ("NUMBER".equalsIgnoreCase(dataType)
            && dataPrecision > 8 && dataScale == 0) {
            return "Long";
        } else if ("NUMBER".equalsIgnoreCase(dataType)
            && dataPrecision <= 8 && dataScale == 0) {
            return "Integer";
        } else {
            return "Double";
        }


    }
}
