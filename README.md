# ibatis generator

- mybatis 를 사용중이라면 이런거 안해도 됩니다. ㅠㅠ
- Oracle Database 기반으로만 작성되었습니다.
- `application.properties` 에 `DataSource` 관련 정보를 입력 후 추가해야합니다.
- 사용할 유저는 권한을 가지고 있어야 합니다.

예 >
```properties
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:orcl
spring.datasource.username=user
spring.datasource.password=password
```

## 작동방법

- `GenRunner.java`의 `run` 내부에 `tableNames` 를 지정합니다.

```java
List<String> tableNames = Arrays.asList(
    "CLL_CONT_LEA_MM",
    "CLL_CONT_LEA_MPAY",
);
```

- `GenRunner.java`의 상단에 `defaultPath`를 선언합니다.

```java
private final String defaultPath = "D:/gen";
```

- 빌드 후 실행합니다.

## 실행 결과

- 지정한 폴더 하부에는 `java`, `xml` 폴더가 생성됩니다.
- xml 폴더 내부에는 sqlMap 이 생성됩니다.
- java 폴더 내부에는 각 테이블 별 Dao, Service, ServiceImpl, VO 가 생성됩니다.

## 소스에 포함되지 못한 필요 Class 정리

`NisDefaultService.java`

```java
import java.util.Map;

public interface NisDefaultService<T> {
    T find(Map<String, Object> param);
    Object insert(T entity);
    Object update(T entity);
    int delete(T entity);
}
```

`NisDefaultVO.java`

```java
@Data
public class NisDefaultVO implements Serializable {
    private static final long serialVersionUID = -3656234710549493794L;
    private String regId;
    private Date regDt;
    private String chgId;
    private Date chgDt;
}
```