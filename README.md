# 서비스 시나리오
멤버십 포인트 횟수로 상품 구매
1. 고객이 구매할 상품을 선택해서 주문한다(
2. 고객이 결제 한다(구매 개수와 가격을 확인하여 금액 차감)
3. 주문이 완료되면 해당 사용자의 포인트를 차감하고, 재고 확인 후, 이상없으면 상품을 발송한다.
4. 고객이 구매를 취소 할 수 있다.
5. 구매를 취소하면 상품 발송이 취소되고 결제가 취소되며 결제 금액이 복구된다.
6. 고객이 상품 발송정보를 확인 할 수 있다.

비 기능적 요구사항.


# 분석 설계

## 이벤트 스토밍 결과
www.msaez.io/#/188553391/storming/final-project


## 헥사고날 아키텍처 다이어그램 도출

# MSA 개발
## 분산트랜잭션 - saga
## 보상처리 - Compensation
## 단일 진입점 - GateWay
## 분산 데이터 프로젝션 - CQRS

## gateway를 통한 서비스 호출
### 초기화
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


## CQRS로 주문상태 조회
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

## 컨테이너 자동확장(auto scale out)

### 기본 오더 파드 상태
<img width="497" alt="스크린샷 2024-11-21 오후 4 20 37" src="https://github.com/user-attachments/assets/04be967a-a400-49c3-867a-0b7bcaffb63d">

### hpa로 cpu 측정
<img width="510" alt="스크린샷 2024-11-21 오후 4 21 24" src="https://github.com/user-attachments/assets/6219bf83-3441-4024-8bb5-41cd3d2bd91d">

## siege로 부하주었을때 파드 최대 3개까지 늘어남
<img width="519" alt="스크린샷 2024-11-21 오후 4 22 42" src="https://github.com/user-attachments/assets/45d4bb25-2e75-44ad-84ed-2265f5474e01">

## hpa로 확인
<img width="530" alt="스크린샷 2024-11-21 오후 4 23 26" src="https://github.com/user-attachments/assets/e3346884-e4f5-44c4-ac6d-a811d7bd8b5f">
