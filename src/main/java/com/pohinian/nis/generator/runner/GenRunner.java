package com.pohinian.nis.generator.runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.python.google.common.base.CaseFormat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GenRunner implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    private final String defaultPath = "D:/gen";

    public GenRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        List<String> tableNames = Arrays.asList(
            "CLL_AGREE_STORAGE",
            "CLL_AUTO_ACNT",
            "CLL_CAR_MST",
            "CLL_CONT_CAR_DTL",
            "CLL_CONT_LEA_MM",
            "CLL_CONT_LEA_MPAY",
            "CLL_CONT_LEVYEXP",
            "CLL_CONTRACT",
            "CLL_CONTRACT_FILE",
            "CLL_VIRTUAL_ACNT"
        );
        generateXmlFiles(tableNames);
        generateJavaFiles(tableNames);
    }

    private void generateJavaFiles(List<String> tableNames) {
        tableNames.forEach(this::genJavaFile);
    }

    private void genJavaFile(String tableName) {
        List<Map<String, Object>> pkList = getPkCols(tableName);
        List<Map<String, Object>> colList = getColList(tableName);

        String daoStr = GenDao.makeJava(tableName);
        String serviceStr = GenService.makeJava(tableName);
        String serviceImpl = GenServiceImpl.makeJava(tableName);
        String voStr = GenVo.makeJava(tableName, pkList, colList);

        Path javaDir =
            Paths.get(defaultPath + "/java/" + tableName.substring(0, 3).toLowerCase() + "/cmm/"
                + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName).substring(3)
                .toLowerCase());
        String daoFileName =
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "Dao.java";
        String serviceFileName =
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "Service.java";
        String implFileName =
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "ServiceImpl.java";
        String voFileName =
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "VO.java";
        try {
            if (!Files.isDirectory(javaDir)) {
                javaDir = Files.createDirectories(javaDir);
            }
            Path daoFilePath = javaDir.resolve(daoFileName);
            Path serviceFilePath = javaDir.resolve(serviceFileName);
            Path implFilePath = javaDir.resolve(implFileName);
            Path voFilePath = javaDir.resolve(voFileName);
            Files.deleteIfExists(daoFilePath);
            Files.deleteIfExists(serviceFilePath);
            Files.deleteIfExists(implFilePath);
            Files.deleteIfExists(voFilePath);
            Files.createFile(daoFilePath);
            Files.createFile(serviceFilePath);
            Files.createFile(implFilePath);
            Files.createFile(voFilePath);
            Files.write(daoFilePath, daoStr.getBytes());
            Files.write(serviceFilePath, serviceStr.getBytes());
            Files.write(implFilePath, serviceImpl.getBytes());
            Files.write(voFilePath, voStr.getBytes());
        } catch (IOException e) {
            log.error("에러가 발생했습니다.", e);
        }
    }


    private void genXmlFile(String tableName) {
        List<Map<String, Object>> pkList = getPkCols(tableName);
        List<Map<String, Object>> colList = getColList(tableName);
        String xmlStr = GenXml.makeXml(tableName, pkList, colList);
        Path xmlDir = Paths.get(defaultPath + "/xml/" + tableName.substring(0, 3).toLowerCase());
        String fileName =
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName) + ".xml";
        try {
            if (!Files.isDirectory(xmlDir)) {
                xmlDir = Files.createDirectories(xmlDir);
            }
            Path xmlFilePath = xmlDir.resolve(fileName);
            Files.deleteIfExists(xmlFilePath);
            Files.createFile(xmlFilePath);
            Files.write(xmlFilePath, xmlStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateXmlFiles(List<String> tableNames) {
        tableNames.forEach(this::genXmlFile);
    }


    private List<Map<String, Object>> getColList(String tableName) {
        String sql = "SELECT COLUMN_NAME, DATA_TYPE, DATA_PRECISION, DATA_SCALE "
            + "FROM ALL_TAB_COLUMNS "
            + "WHERE TABLE_NAME = '" + tableName + "' "
            + "order by COLUMN_ID ";
        return jdbcTemplate.queryForList(sql);
    }


    private List<Map<String, Object>> getPkCols(String tableName) {
        String sql = "SELECT "
            + "       B.COLUMN_NAME      "
            + "  FROM ALL_CONSTRAINTS  A "
            + "     , ALL_CONS_COLUMNS B "
            + " WHERE A.TABLE_NAME      = '" + tableName + "' "
            + "   AND CONSTRAINT_TYPE = 'P' "
            + "   AND A.OWNER           = B.OWNER "
            + "   AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME "
            + " ORDER BY B.POSITION";
        return jdbcTemplate.queryForList(sql);
    }


}
