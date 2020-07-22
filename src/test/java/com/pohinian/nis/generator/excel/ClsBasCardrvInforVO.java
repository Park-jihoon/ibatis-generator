package com.pohinian.nis.generator.excel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClsBasCardrvInforVO {
    private static final long serialVersionUID = 1L;
    /*
     * 회사코드(By TableComments).
     */
    private String compCd;
    /*
     * ,운전자정보번호(By TableComments).
     */
    private String cardrvSeq;
    /*
     * 회사코드(By TableComments).
     */
    private String cardrvType;
    /*
     * 운전자명(By TableComments).
     */
    private String cardrvNm;
    /*
     * 사원번호(By TableComments).
     */
    private String cardrvEmpno;
    /*
     * 주민번호(By TableComments).
     */
    private String cardrvRegno;
    /*
     * 입사일(By TableComments).
     */
    private String cardrvEntdt;
    private String cardrvEntYear;
    private String cardrvEntMonth;
    private String cardrvEntDay;
    /*
     * 소속(By TableComments).
     */
    private String cardrvDptcd;
    /*
     * 직위(By TableComments).
     */
    private String cardrvPstcd;
    /*
     * 면허번호(By TableComments).
     */
    private String cardrvLicno;
    /*
     * 면허종별(By TableComments).
     */
    private String cardrvLictype;
    /*
     * 발행처(By TableComments).
     */
    private String cardrvLicarea;
    /*
     * 상태:운행중: S 준비중 :R (By TableComments).
     */
    private String cardrvSts;
    /*
     *  업무가능유무(By TableComments).
     */
    private String cardrvUseyn;
    /*
     * null(By TableComments).
     */
    private String fileNm;
    /*
     * null(By TableComments).
     */
    private String orgFileNm;
    /*
     * null(By TableComments).
     */
    private byte[] fileBlob;
    /*
     * null(By TableComments).
     */
    private String fileSize;
    /*
     * 비고(By TableComments).
     */
    private String cardrvRmk;
    /*퇴사일*/
    private String retDt;
    /*면허취득일자*/
    private String licAcqDt;
    /*신규정밀검사발급번호*/
    private String scruNo;
    /*신규정밀검사 수정일자*/
    private String scruInspDt;
    /*신규정밀검사 수검사유*/
    private String scruInspRmk;
    /*화물운송종사자 자격번호*/
    private String freiNo;
    /*화물운송종사자 자격 취득일자*/
    private String freiAcqDt;
    /*자격증명 발급협회*/
    private String licAssoCd;
    /*자격증 발급일자*/
    private String licIssDt;
    /*화물협회*/
    private String cargoAssoCd;
    /*운전자격확인증발급일자*/
    private String licCertiDt;
    /*운전자 핸드폰 번호*/
    private String hpNo;
    /*증빙 키*/
    private String edocKey;

    /*차량번호*/
    private String carNo;


    private String driverEducationNm;
    private String cardrvLicno1;
    private String cardrvLicno2;
    private String cardrvLicno3;
    private String cardrvLicno4;
    private String retDtYear;
    private String retDtMonth;
    private String retDtDay;

    /**
     * Sets cardrv entdt.
     *
     * @param cardrvEntdt the cardrv entdt
     */
    public void setCardrvEntdt(String cardrvEntdt) {
        this.cardrvEntdt = cardrvEntdt;
        if (cardrvEntdt != null && cardrvEntdt.trim().length() == 8) {
            this.cardrvEntYear = cardrvEntdt.substring(0, 4);
            this.cardrvEntMonth = cardrvEntdt.substring(4, 6);
            this.cardrvEntDay = cardrvEntdt.substring(6, 8);
        }
    }

    public void setRetDt(String retDt) {
        this.retDt = retDt;
        if (retDt != null && retDt.trim().length() == 8) {
            this.retDtYear = retDt.substring(0, 4);
            this.retDtMonth = retDt.substring(4, 6);
            this.retDtDay = retDt.substring(6, 8);
        }
    }

    public void setCardrvLicno(String cardrvLicno) {
        this.cardrvLicno = cardrvLicno == null ? "" : cardrvLicno.trim().replace("-", "");
        if (this.cardrvLicno.trim().length() == 12) {
            this.cardrvLicno1 = this.cardrvLicno.substring(0, 2);
            this.cardrvLicno2 = this.cardrvLicno.substring(2, 4);
            this.cardrvLicno3 = this.cardrvLicno.substring(4, 10);
            this.cardrvLicno4 = this.cardrvLicno.substring(10, 12);
        }
    }


}

