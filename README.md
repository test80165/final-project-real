# 서비스 시나리오
pay 잔액으로 상품 구매
1. 고객이 구매할 상품을 선택해서 주문한다
2. 고객이 결제 한다(구매 개수와 가격을 확인하여 금액 차감)
3. 주문이 완료되면 해당 사용자의 포인트를 차감하고, 재고 확인 후, 이상없으면 상품을 발송한다.
4. 고객이 구매를 취소 할 수 있다.
5. 구매를 취소하면 상품 발송이 취소되어 상품 개수가 원복되고 결제가 취소되며 결제 금액이 복구된다.
6. 고객이 상품 발송정보를 확인 할 수 있다.

비 기능적 요구사항.

### 클라우드 아키텍처

<img width="388" alt="스크린샷 2024-11-22 오후 1 37 28" src="https://github.com/user-attachments/assets/f1e5aadc-cf1c-4635-9c6b-9557b1a6f1fd">

### 클라우드 아키텍처(inner)

<img width="388" alt="스크린샷 2024-11-22 오후 1 37 28" src="https://github.com/user-attachments/assets/f1e5aadc-cf1c-4635-9c6b-9557b1a6f1fd">

# 분석 설계

### 이벤트 스토밍 결과
www.msaez.io/#/188553391/storming/final-project

<img width="856" alt="스크린샷 2024-11-22 오전 11 05 35" src="https://github.com/user-attachments/assets/6e21477b-e88d-4543-b4b7-33ec1bd28526">





# MSA 개발

## 분산트랜잭션 - saga / 보상처리 - Compensation / 단일 진입점 - GateWay
### 테스트를 위한 기본 데이터 추가
1) 상품 추가
   
=> 가격 1000원짜리 상품 1000개 추가
<img width="912" alt="스크린샷 2024-11-21 오후 1 43 17" src="https://github.com/user-attachments/assets/7cf15f55-0e10-46f2-b180-ffe298bf493f">
  
3) 사용자의 pay잔액 추가
   
=> 사용자 잔액 999999 추가
<img width="916" alt="스크린샷 2024-11-21 오후 1 43 29" src="https://github.com/user-attachments/assets/74ff4fcf-3454-4a69-bc4b-64c3eb640590">

### pub/sub 구조의 기본 주문 과정
1) 상품주문
   
=> 가격 1000원짜리 상품 10개를 주문 - 총 10000원
<img width="906" alt="스크린샷 2024-11-21 오후 1 43 39" src="https://github.com/user-attachments/assets/5308c43f-091b-457c-a2ce-57b3df52e6f8">
   
3) pay잔액 감소 확인
   
=> 1000 * 10 = 10000원 구매하여 잔액이 989999로 감소

<img width="632" alt="스크린샷 2024-11-21 오후 1 44 21" src="https://github.com/user-attachments/assets/6077d916-aea2-49b5-aa9a-16ef5bfb8ea8">
   
5) 상품 개수 감소 확인
   
=> 상품 10개 구매하여 990개로 감소

<img width="556" alt="스크린샷 2024-11-21 오후 1 44 11" src="https://github.com/user-attachments/assets/15b34220-688e-4964-a27a-a13c82dd569a">
   
7) 배송 히스토리 추가 확인
   
=> 주문한 상품 히스토리 추가됨.

<img width="650" alt="스크린샷 2024-11-21 오후 1 43 55" src="https://github.com/user-attachments/assets/f6ea1bd3-9094-4484-963d-c9e5983babe5">


### 취소시 보상처리 
1) 상품주문 취소
   
=> 오더 서비스 쪽에 주문취소 요청

<img width="653" alt="스크린샷 2024-11-21 오후 1 52 56" src="https://github.com/user-attachments/assets/5a9ad852-28a3-45e7-acfd-267f5f19e278">

2) 인벤토리 상품 개수 원복

=> 구매한 상품 10개가 취소되어 다시 1000개로 조회됨

<img width="622" alt="스크린샷 2024-11-21 오후 1 53 33" src="https://github.com/user-attachments/assets/9d2e8e15-66d1-44cb-8194-b0d9776bf497">

3) pay잔액 복구 확인.

=> 사용한 10000원 잔액이 복구되어 999999로 조회됨

<img width="582" alt="스크린샷 2024-11-21 오후 1 53 11" src="https://github.com/user-attachments/assets/fad30bcf-7f20-4824-8529-380edb6433a3">

## 분산 데이터 프로젝션 - CQRS

### CQRS를 사용하여 주문 상태 조회 구성.

1) 상품 주문

=> 상품 두번 주문

<img width="909" alt="스크린샷 2024-11-21 오후 2 14 08" src="https://github.com/user-attachments/assets/8af2b81b-c844-4101-a4ff-4981752b5027">

<img width="911" alt="스크린샷 2024-11-21 오후 2 16 02" src="https://github.com/user-attachments/assets/7982565c-60f3-40ba-9bf5-21c7b4bf53b6">

2) 마이페이지에서 주문 조회

 => gateway를 통해 마이페이지에서 두개의 주문 이력확인.

<img width="578" alt="스크린샷 2024-11-21 오후 2 15 35" src="https://github.com/user-attachments/assets/f4e40678-ea1c-4c6f-9fee-32871d0a7bcf">

3) order 서비스 종료

 => 주문 서비스 종료후, 호출 안되는 것 확인.

<img width="906" alt="스크린샷 2024-11-21 오후 2 16 26" src="https://github.com/user-attachments/assets/1af97609-1c89-4d47-8b19-d1280e5bfb4c">
   
4) 종료후에도 마이페이지에서 주문 이력 조회되는 것 확인
 
=> gateway통해서 호출했을때, 마이페이지에서는 정상 조회

<img width="910" alt="스크린샷 2024-11-21 오후 2 16 59" src="https://github.com/user-attachments/assets/5f4daa68-056c-43a1-a8fb-b1d3f566ef00">

## 클라우드 배포 - Container 운영
* AZURE DEVOPS CI/CD를 사용하여 진행하엿습니다.
### CI 설정
<img width="1109" alt="스크린샷 2024-11-21 오후 5 22 27" src="https://github.com/user-attachments/assets/c0623187-b009-4c1e-9812-7a9d70090ae1">

### CD 설정
<img width="643" alt="스크린샷 2024-11-21 오후 5 23 54" src="https://github.com/user-attachments/assets/c4a6f431-7cb3-47db-adb2-ea3d7a960749">

### 기존 파드가 종료되고 새로운 파드가 실행됨.
<img width="536" alt="스크린샷 2024-11-21 오후 5 24 13" src="https://github.com/user-attachments/assets/d95aaa4a-6885-429b-b3f2-9fb0c0442a7c">

##CI/CD 트리거 동작확인 - 리드미 수정.
<img width="1136" alt="스크린샷 2024-11-21 오후 5 26 07" src="https://github.com/user-attachments/assets/89370753-5941-4475-8e16-b64c6e08dec6">

## 컨테이너 자동확장(auto scale out)

### 기본 오더 파드 상태
<img width="497" alt="스크린샷 2024-11-21 오후 4 20 37" src="https://github.com/user-attachments/assets/04be967a-a400-49c3-867a-0b7bcaffb63d">

### hpa로 cpu 측정
<img width="510" alt="스크린샷 2024-11-21 오후 4 21 24" src="https://github.com/user-attachments/assets/6219bf83-3441-4024-8bb5-41cd3d2bd91d">

### siege로 부하주었을때 파드 최대 3개까지 늘어남
<img width="519" alt="스크린샷 2024-11-21 오후 4 22 42" src="https://github.com/user-attachments/assets/45d4bb25-2e75-44ad-84ed-2265f5474e01">

### hpa로 확인
<img width="530" alt="스크린샷 2024-11-21 오후 4 23 26" src="https://github.com/user-attachments/assets/e3346884-e4f5-44c4-ac6d-a811d7bd8b5f">

## 셀프힐링/무정지 배포 - Liveness/readiness
* readiness로 진행
### readiness 설정

<img width="258" alt="스크린샷 2024-11-22 오전 12 27 52" src="https://github.com/user-attachments/assets/b20706cc-8b32-40b2-b15c-d907569e1c1d">

### siege로 60초간 서비스 호출 및 가용성 100프로 확인

<img width="445" alt="스크린샷 2024-11-22 오전 12 26 47" src="https://github.com/user-attachments/assets/9d69b585-c3c0-4e8b-8b80-858bfcce093b">

### siege 호출동안 두번의 배포 진행

<img width="723" alt="스크린샷 2024-11-22 오전 12 27 38" src="https://github.com/user-attachments/assets/a6c6e265-8a95-4480-aadc-1266a025863d">


## 환경분리 - configMap/secret

### configMap yaml
<img width="290" alt="스크린샷 2024-11-22 오전 1 12 21" src="https://github.com/user-attachments/assets/8dcbf5ec-c224-4ef7-a33e-139e91f278d4">

### 파일 공유 방식을 사용해서 여러 레플리카가 동일한 configMap 파일을 공유하도록 함.
=> 해당 파일을 통해 설정 값 사용 가능
<img width="1130" alt="스크린샷 2024-11-22 오전 1 13 08" src="https://github.com/user-attachments/assets/f9e09fc0-77cf-4cac-9d08-052c17c48649">

## PVC

### NFS 생성
=> NFS 방식으로 생성한 후, 두 레플리카가 서로 파일을 공유하는지 확인함.
<img width="1229" alt="스크린샷 2024-11-22 오전 1 41 01" src="https://github.com/user-attachments/assets/0bc4b841-50cb-47e0-b2cf-8338f34c8a1b">

### order 컨테이너에 마운트를 위한 설정파일

<img width="261" alt="스크린샷 2024-11-22 오전 1 42 52" src="https://github.com/user-attachments/assets/79b98047-bcb8-47bc-8f34-99d46abd48ca">

### 1번 레플리카에 파일 생성

<img width="864" alt="스크린샷 2024-11-22 오전 1 48 07" src="https://github.com/user-attachments/assets/ba22c075-e05a-4127-a48c-2f9802cfe695">

### 2번 레플리카에 파일생성확인

<img width="734" alt="스크린샷 2024-11-22 오전 1 48 30" src="https://github.com/user-attachments/assets/7ab59639-5341-411e-a91d-bb607f6f5a26">

## 서비스 매쉬

## 모니터링

### loki stack 설치

<img width="579" alt="스크린샷 2024-11-22 오전 1 56 48" src="https://github.com/user-attachments/assets/8f278154-2b11-46fd-a7b8-9251e33458e8">

<img width="632" alt="스크린샷 2024-11-22 오전 1 57 21" src="https://github.com/user-attachments/assets/60eec3e7-f3b3-46b7-96ae-7d68bc926f56">

### loki 

<img width="1622" alt="스크린샷 2024-11-22 오전 2 15 57" src="https://github.com/user-attachments/assets/3ee31366-dad3-4a1e-af71-89003f0da483">

### grafana 기본 템플릿을 이용한 부하 모니터링 

<img width="1907" alt="스크린샷 2024-11-22 오전 2 28 12" src="https://github.com/user-attachments/assets/8b7734af-97ba-4c9a-94e1-29d20e343277">


### 시즈로 부하주기

<img width="580" alt="스크린샷 2024-11-22 오전 10 17 58" src="https://github.com/user-attachments/assets/2b49bd63-ef12-4a2f-a649-034d8b082099">

### 그라파나에서 리소스 확인

<img width="1406" alt="스크린샷 2024-11-22 오전 10 18 30" src="https://github.com/user-attachments/assets/cc5245b1-38f6-4923-bdba-e29014b5579a">
