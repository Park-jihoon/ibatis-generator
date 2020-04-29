package com.pohinian.nis.generator.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.python.google.common.base.CaseFormat;

/**
 * The type Gen xml.
 */
@UtilityClass
public class GenXml {

    private final String COLUMN_NAME = "COLUMN_NAME";

    /**
     * Make xml string.
     *
     * @param tableName the table name
     * @param pkList    the pk list
     * @param colList   the col list
     * @return the string
     */
    public String makeXml(String tableName, List<Map<String, Object>> pkList,
                          List<Map<String, Object>> colList) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            +
            "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n"
            + "<sqlMap namespace=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "\">\n"
            +
            "<typeAlias alias=\"egovMap\" type=\"egovframework.rte.psl.dataaccess.util.EgovMap\"/>\n"
            + "<typeAlias alias=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName) + "VO"
            + "\" type=\"com.jitpower.pms.nis.cll.cmm."
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName).substring(3)
            .toLowerCase()
            + "." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "VO\"/>\n"
            + makeSelect(tableName, pkList, colList) + "\n"
            + makeInsert(tableName, colList) + "\n"
            + makeUpdate(tableName, pkList, colList) + "\n"
            + makeDelete(tableName, pkList) + "\n"
            + "</sqlMap>\n";
    }

    private String makeDelete(String tableName, List<Map<String, Object>> pkList) {
        return "<delete id=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)
            + "DAO.delete\" parameterClass=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName) + "VO\">\n"
            + "DELETE FROM " + tableName + " \n"
            + makeWhere(pkList) + " \n"
            + "</delete>";
    }

    private String makeInsert(String tableName, List<Map<String, Object>> colList) {
        return "<insert id=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)
            + "DAO.insert\" parameterClass=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName) + "VO\">\n"
            + "INSERT INTO " + tableName + " (\n"
            + makeSelectCol(colList)
            + ") VALUES ( \n"
            + makeInsertValue(colList)
            + ")\n"
            + "</insert>";
    }

    private String makeInsertValue(List<Map<String, Object>> colList) {
        return colList.stream()
            .map(m -> {
                String c = (String) m.get(COLUMN_NAME);
                if ("CHG_ID".equalsIgnoreCase(c)) {
                    c = "regId";
                } else if ("CHG_DT".equalsIgnoreCase(c)) {
                    c = "regDt";
                } else {
                    c = CaseFormat.UPPER_UNDERSCORE
                        .to(CaseFormat.LOWER_CAMEL, c);
                }
                return "#" + c + "#";
            }).collect(Collectors.joining(",\n"));
    }

    private String makeSelect(String tableName, List<Map<String, Object>> pkList,
                              List<Map<String, Object>> colList) {
        return "<select id=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)
            + "DAO.find\" resultClass=\"egovMap\" parameterClass=\"java.util.Map\"> \n"
            + "SELECT "
            + makeSelectCol(colList) + "\n"
            + " FROM " + tableName + "\n"
            + " WHERE 1 = 1 \n"
            + makeWhere(pkList) + " \n"
            + "</select>";
    }

    private String makeSelectCol(List<Map<String, Object>> colList) {
        return colList.stream()
            .map(m -> (String) m.get(COLUMN_NAME)).collect(Collectors.joining(",\n"));
    }

    private String makeWhere(List<Map<String, Object>> pkList) {
        return pkList.stream()
            .map(m -> {
                String c = (String) m.get(COLUMN_NAME);
                String cc = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, c);
                return " AND " + c + " = #" + cc + "# ";
            }).collect(Collectors.joining("\n"));
    }

    private String makeUpdateCol(List<Map<String, Object>> colList) {
        return colList.stream()
            .filter(m -> !"REG_ID".equalsIgnoreCase((String) m.get(COLUMN_NAME))
                && !"REG_DT".equalsIgnoreCase((String) m.get(COLUMN_NAME)))
            .map(m -> {
                String c = (String) m.get(COLUMN_NAME);
                String cc = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, c);
                return " " + c + " = #" + cc + "#";
            }).collect(Collectors.joining(",\n"));
    }

    private String makeUpdate(String tableName, List<Map<String, Object>> pkList,
                              List<Map<String, Object>> colList) {
        return "<update id=\"" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)
            + "DAO.update\" parameterClass=\""
            + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName) + "VO\">\n"
            + "UPDATE " + tableName + "\n"
            + "SET " + makeUpdateCol(colList) + "\n"
            + "WHERE 1 = 1 \n"
            + makeWhere(pkList) + " \n"
            + "</update>";

    }


}
