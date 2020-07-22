package com.pohinian.nis.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.XML;
import org.junit.jupiter.api.Test;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
public class DrmTest {

    @Test
    public void toXmlTest() throws JsonProcessingException {

        Map<String, Object> head = new HashMap<>();
        head.put("MODULEID", "INFRA");
        head.put("DOCTYPE", "LGL_INFRA_STATEMENT");

        Map<String, Object> signer = new HashMap<>();
        signer.put("EMPLOYEE_NUM", "");
        signer.put("DEPT_CD", "");
        signer.put("SIGN_TYPE", "");

        head.put("PROCESS_SIGNER", signer);

        Map<String, Object> dbapproval = new HashMap<>();
        dbapproval.put("HEADER", head);


        XmlMapper mapper = new XmlMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        log.info("\n{}", mapper.writer().withRootName("DBAPPROVAL").writeValueAsString(dbapproval));

    }

    @Test
    public void readFile() throws IOException, TransformerException {
        List<String> xmlLines = Files.readAllLines(Paths.get("C:\\Users\\Pohinian\\Downloads\\전자결재관련자료\\INFRA요약회계전표(통합결재)_20200619.xml"));
        String xmlStr = xmlLines.stream().collect(Collectors.joining("\n"));
        TypeReference<TreeMap<String, Object>> type = new TypeReference<TreeMap<String, Object>>() {
        };

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> map = om.readValue(XML.toJSONObject(xmlStr).toString(), type);

        log.info(mapToXml(map));

        log.info("@@@{}", map);

    }

    public String mapToXml(Map<String, Object> map) throws TransformerException {
        XmlGenerator generator = new XmlGenerator();

        generator.setPropertyOmit("yes");
        generator.setPropertyIndent("yes");

        String rootName = "DBAPPROVAL";

        if (map.size() == 1) {
            rootName = (String) map.keySet().toArray()[0];
        }
        generator.addRootElement(rootName);
        for (Map.Entry<String, Object> o : map.entrySet()) {
            genValue(rootName, (Map<String, Object>) o.getValue(), generator);
        }

        return generator.getXmlData();
    }

    private void genValue(String parentName, Map<String, Object> map, XmlGenerator generator) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof List) {
                List<?> list = (List<?>) entry.getValue();
                list.forEach(l -> {
                    log.info("LIST : {}", entry.getKey());
                    generator.addElement(parentName, entry.getKey(), "");
                    genValueList(entry.getKey(), l, generator);
                });

            } else if (entry.getValue() instanceof Map) {
                log.info("MAP : {}", entry.getKey());
                generator.addElement(parentName, entry.getKey(), "");
                genValue(entry.getKey(), (Map<String, Object>) entry.getValue(), generator);
            } else {
                generator.addElementCDATASection(parentName, entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    private void genValueList(String parentName, Object o, XmlGenerator generator) {
        if (o instanceof Map) {
            genValue(parentName, (Map<String, Object>) o, generator);
        } else if (o instanceof List) {
            genValueList(parentName, o, generator);
        }
    }


}
