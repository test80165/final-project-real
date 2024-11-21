# 서비스 시나리오
멤버십 포인트 횟수로 상품 구매
1. 고객이 구매할 상품을 선택해서 주문한다(가격중 일부는 포인트 횟수, 나머지는 실제 돈)
2. 고객이 결제 한다.(실제 돈 결제 후 포인트 횟수 차감)
3. 주문이 완료되면 해당 사용자의 포인트를 차감하고, 재고 확인 후, 이상없으면 상품을 발송한다.
4. 고객이 구매를 취소 할 수 있다.
5. 구매를 취소하면 상품 발송이 취소되고 결제가 취소되며 차감된 횟수가 복구된다.
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

