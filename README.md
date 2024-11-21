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

## 클라우드 배포 - Container 운영

## 컨테이너 자동확장
 

## Model
www.msaez.io/#/188553391/storming/final-project
 

## Model
www.msaez.io/#/188553391/storming/final-project

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- order
- inventory
- delivery
- mypage
- pay


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- order
```
 http :8088/orders id="id" productId="productId" quantity="quantity" userId="userId" status="status" price="price" 
```
- inventory
```
 http :8088/inventories id="id" stockName="stockName" stockCount="stockCount" status="status" price="price" 
```
- delivery
```
 http :8088/deliveries id="id" orderId="orderId" userId="userId" address="address" quantity="quantity" status="status" productId="productId" 
```
- mypage
```
```
- pay
```
 http :8088/pays id="id" payAmount="payAmount" userId="userId" status="status" point="point" 
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

