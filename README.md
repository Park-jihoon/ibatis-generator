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