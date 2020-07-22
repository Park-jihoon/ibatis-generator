package com.pohinian.nis.generator.excel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ExcelTest {

    private static void executeGridObjectListDemo(List<?> employees) throws IOException {
        Arrays.asList(
//                "GYEONGGI_IN",
//                "BUSAN_IN",
//                "GYEONGNAM_IN",
//                "CHUNGBUK_IN",
//                "INCHEON_IN",
//                "CHUNGNAM_IN",
//                "JEJU_IN",
//                "DAEGU_IN",
//                "JEONBUK_IN",
//                "DAEJEON_IN",
//                "JEONNAM_IN",
//                "GANGWON_IN",
//                "SEOUL_IN",
//                "GWANGJU_IN",
//                "ULSAN_IN",
//                "GYEONGBUK_IN"
                "퇴사보고서양식"
        ).forEach(s -> {
            try {
                fileTest(employees, s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void fileTest(List<?> employees, String fileName) throws IOException {
        File file = new File("D:\\project\\NIS_PIMS\\docs\\excel\\" + fileName + ".xlsx");
        try (InputStream is = new FileInputStream(file)) {
            try (OutputStream os = new FileOutputStream("D:\\project\\NIS_PIMS\\docs\\excel\\" + fileName + "2.xlsx")) {
                Context context = new Context();
                context.putVar("drivers", employees);

                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
    }

    @Test
    public void text1() throws IOException {
        ClsBasCardrvInforVO vo = new ClsBasCardrvInforVO();
        vo.setCardrvNm("박지훈");
        vo.setCardrvRegno("831226-1******");
        vo.setFreiNo("2384729473");
        vo.setCardrvEntdt("20200401");
        vo.setRetDt("20200801");
        vo.setRetDt("20201231");
        vo.setCarNo("강원80바2214");
        vo.setCardrvLicno("202012345690");
        vo.setDriverEducationNm("고졸");
        vo.setFreiAcqDt("20200604");

        // 14 서울
        List<ClsBasCardrvInforVO> list = new ArrayList<>(Arrays.asList(vo));
//        int listSize = list.size();
//        if (list.size() < 14) {
//            for (int i = 0; i < 14 - listSize; i++) {
//                list.add(new ClsBasCardrvInforVO());
//            }
//        }
        executeGridObjectListDemo(list);

    }

    static class JexlCustomFunction {
        public String getYear(String dt) {
            return dt == null || dt.length() < 4 ? "" : dt.substring(0, 4);
        }
    }

}
