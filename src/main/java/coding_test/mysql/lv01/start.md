## 코테 연습용 mysql
### 2023-12-18

```mysql

```
docker 로 mysql2 컨테이너 생성하고 비밀번호 설정하고

db에 root 계정으로 접속하고 생성할 때 입력한 비밀번호 입력하고

```터미널
docker run --name [컨테이너이름] -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=[루트 비밀번호] -p 3307:3306 -d mysql

docker exec -it mysql2 mysql -uroot -p


```

이렇게 해서 내가 사용할 user 생성 권한부여 끝났고
```mysql
CREATE USER '[유저이름]'@'%' IDENTIFIED BY '[비밀번호]';

GRANT ALL PRIVILEGES ON *.* TO 'study1'@'%';
FLUSH PRIVILEGES;

```

#### 조건에 부합하는 중고거래 댓글 조회하기
##### 문제
조건에 부합하는 중고거래 댓글 조회하기  

`USED_GOODS_BOARD`와 `USED_GOODS_REPLY` 테이블에서
2022년 10월에 작성된 게시글 제목, 게시글 ID, 댓글 ID, 댓글 작성자 ID, 댓글 내용, 댓글 작성일을 조회하는 SQL문을 작성해주세요. 
결과는 댓글 작성일을 기준으로 오름차순 정렬해주시고,  
댓글 작성일이 같다면 게시글 제목을 기준으로 오름차순 정렬해주세요.


```mysql
-- 코드를 입력하세요
SELECT b.TITLE
     , b.BOARD_ID
     , r.REPLY_ID
     , r.WRITER_ID
     , r.CONTENTS
     , DATE_FORMAT(r.CREATED_DATE, '%Y-%m-%d') AS CREATED_DATE
FROM USED_GOODS_BOARD b
         JOIN USED_GOODS_REPLY r
              ON b.BOARD_ID = r.BOARD_ID
WHERE YEAR(b.CREATED_DATE) = 2022
  AND MONTH(b.CREATED_DATE) = 10
ORDER BY r.created_date, b.TITLE;

```

###### 문제
특정 옵션이 포함된 자동차 리스트 구하기  


###### 답
```mysql
SELECT CAR_ID, CAR_TYPE, DAILY_FEE, OPTIONS
FROM CAR_RENTAL_COMPANY_CAR
WHERE OPTIONS LIKE '%네비게이션%'
ORDER BY CAR_ID DESC;
```

###### 문제
자동차 대여 기록에서 장기/단기 대여 구분하기  
`CAR_RENTAL_COMPANY_RENTAL_HISTORY` 테이블에서  
대여 시작일이 2022년 9월에 속하는 대여 기록에 대해서  
대여 기간이 29일 이상이면 '장기 대여'  
그렇지 않으면 '단기 대여' 로 표시하는 컬럼(컬럼명: RENT_TYPE)을 추가하여  
대여기록을 출력하는 SQL문을 작성해주세요.  
결과는 대여 기록 ID를 기준으로 내림차순 정렬해주세요.

```mysql
-- 코드를 입력하세요
SELECT HISTORY_ID
     , CAR_ID
     , DATE_FORMAT(START_DATE, '%Y-%m-%d') AS START_DATE
     , DATE_FORMAT(END_DATE, '%Y-%m-%d')   AS END_DATE
     , CASE
           WHEN ABS(DATEDIFF(START_DATE, END_DATE)) >= 29 THEN '장기 대여'
           ELSE '단기 대여'
    END
                                           AS RENT_TYPE
FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY
WHERE DATE_FORMAT(START_DATE, '%Y-%m') = '2022-09'
ORDER BY HISTORY_ID DESC;
```

##### 문제
###### 최댓값 구하기
가장 최근에 들어온 동물은 언제 들어왔는지 조회하는 SQL 문을 작성해주세요.

###### 답
```mysql
SELECT MAX(DATETIME) AS 시간
FROM ANIMAL_INS;
```



### 오늘 배운 내용 정리
- `YEAR(DATE)` = 2022 AND `MONTH(DATE)` = 10
  - 날짜가 해당 년 월인 로우를 조회할 때
- CASE WHEN '조건' THEN '결과1' ELSE '결과2'
  - 컬럼에 조건문 넣을 때

```mysql
   SELECT
       CASE
           WHEN score >= 90 THEN 'A'
           WHEN score >= 80 THEN 'B'
           WHEN score >= 70 THEN 'C'
           WHEN score >= 60 THEN 'D'
           ELSE 'F'
           END AS grade
   FROM students;
```
##### 문제
중복 제거하기
동물 보호소에 들어온 동물의 이름은 몇 개인지 조회하는 SQL 문을 작성해주세요  
이때 이름이 NULL인 경우는 집계하지 않으며  
중복되는 이름은 하나로 칩니다.

###### 답
```mysql
SELECT COUNT(DISTINCT NAME)
FROM  ANIMAL_INS;
```


- ABS(DATEDIFF(DATE1, DATE2))
  - 날짜 계산할 때 사용
  - 절대값 : `ABS(num)`, `DATEDIFF(DATE1, DATE2)` 차이 계산
- `DATE_FORMAT(DATE, '%Y-%m-%d')` 
  - 날짜 형식 지정
  - '%y'는 23년, '%Y'는 2023년
- 집계함수
  - MAX, MIN, SUM, AVG
  - 일반적으로 GROUP BY 와 사용됨
  - 집계 함수는 일반적으로 NULL 값을 무시
- DISTINCT
  - 중보 제거할 때
  - COUNT(DISTINCT NAME) 이런 식으로 사용