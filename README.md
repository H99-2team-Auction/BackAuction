# 0. BackAuction

## Member

- 강창식
- 이기재
- 안성재

## Dependencies
- Lombok
- MySQL Driver
- H2 Database
- Spring Web
- Spring Security
- Validation
- Spring Data JPA
- Spring Boot Dev Tools

<br/>

---

<br/>

## 1. 리팩터링

<br/>

![image](https://user-images.githubusercontent.com/64416833/198209243-76c6325f-21ea-48f2-9f6d-abb08a181fa7.png)

![image](https://user-images.githubusercontent.com/64416833/198209288-014d0011-97e0-455d-bda5-8f1a425f3a1c.png)

![image](https://user-images.githubusercontent.com/64416833/198209326-abdcbdbe-67bf-4b36-86bc-c8bd05c1d7db.png)

<br/>

---

<br/>

## 2. 트러블 슈팅

### 2-1) JPA Query Creation
메인페이지에 상품 목록들을 보여줄 때 낙찰 받은 상품은 제외하고 보여주는 것이 요구사항이었는데 지금까지는 아이디로 찾거나 네임으로 찾거나 정렬하는 조건으로만 처리해주었는데 이번에는 상품이 낙찰 됐는지에 대한 여부를 알아야 했고, 이것과 관련한 건 상품(Product)의 낙찰 여부(boolean isSold) 필드였다.
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsSoldFalseOrderByModifiedAtDesc();
}
```

이것과 관련된 포스팅은 다음과 같다.
https://stackoverflow.com/questions/17600790/query-by-boolean-properties-in-spring-data-jpa-without-using-method-parameters

---

### 2-2) Error creating bean with name 'commentXXX' defined in file

```java
org.springframework.beans.factory.UnsatisfiedDependencyException:
Error creating bean with name 'commentController' defined in file
Error creating bean with name 'commentService' defined in file
Error creating bean with name 'commentRepository' defined in
...
Caused by: java.lang.IllegalArgumentException: Unable to locate Attribute with the the given name [product] on this ManagedType
```

Bean이 등록되지 않는 문제가 있어서 각 계층에 적절한 Component 어노테이션들이 붙어있는지도 확인해보고 기본 생성자나 Getter가 있는 지도 확인해봤지만 문제없이 다 있었지만 계속해서 빈을 생성할 수 없었는데 변수명이 대문자라 발생한 문제였다.

```java
@Entity
@Getter
@Builder
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product Product;
    ...
}
```

---

### 2-3) MySQL Error Code: 1452. Cannot add or update a child row
지난 주차에 사용한 RDS DB를 그대로 사용하다보니 겹치는 테이블이 발생해서 발생한 에러였는데 처음엔 단순히 에러 코드만 보고 @Table(name = "members")로 지정해봤지만 계속해서 참조무결성 에러가 발생해서 기존에 있던 테이블들을 DROP TABLE 하고 접근하니 에러가 해결됐다.

참고 URL
https://live-everyday.tistory.com/188

---

### 2-4) com.fasterxml.jackson.databind.exc.InvalidDefinitionException

처음보는 에러에 당황해서 하루종일 해결을 못했던것 같습니다. 해결하고 나니 Access_Token 언더바 syntax error 였습니다. 
처음보는 에러나 오랫동안 해결이 안되는 에러는 생각보다 간단한 에러일수도 있다는 생각을 했고, 다음부터는 기본적인 에러부터 찾아봐야겠다고 생각했습니다. 

![InvalidDefinition](https://user-images.githubusercontent.com/66250121/198219375-914f22e6-f109-4655-b064-53b14f0c9590.png)



---

### 2-5) application.yml 정보 노출
application.yml 파일이 gitignore에 등록되지 않은 상태라 jwt secret key와 RDS의 마스터 계정과 비밀번호가 Github에 노출되는 문제가 있었다.
https://velog.io/@gillog/Git-.gitignore-재적용

---

### 2-6) java.sql.SQLException: Field 'id' doesn't have a default value

"경우에 따라 모델이나 ORM은 데이터베이스의 변경사항이 정확하게 반영되지 않을 수 있다. "Auto Increment" 항목을 체크해주고 다시 실행해보니 잘 실행이 됩니다."라는 부분을 보고 MySQL의 테이블을 보니 잘 해결이 되었다.
https://cocoon1787.tistory.com/823

---



## 3. [Git] 커밋 메시지 컨벤션 Commit Message Convention


01. Feat	새로운 기능을 추가
02. Fix	버그 수정
03. Design	CSS 등 사용자 UI 디자인 변경
04. !BREAKING CHANGE	커다란 API 변경의 경우
05. !HOTFIX	급하게 치명적인 버그를 고쳐야하는 경우
06. Style	코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우
07. Refactor	프로덕션 코드 리팩토링
08. Comment	필요한 주석 추가 및 변경
09. Docs	문서 수정
10. Test	테스트 코드, 리펙토링 테스트 코드 추가, Production 11. Code(실제로 사용하는 코드) 변경 없음
12. Chore	빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음
13. Rename	파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우
14. Remove	파일을 삭제하는 작업만 수행한 경우


### Example
- Feat: 회원 가입 기능 구현
- SMS, 이메일 중복확인 API 개발

<br/>

#### [Reference]

[[Git] Commit Message Convention](https://velog.io/@archivvonjang/Git-Commit-Message-Convention)
