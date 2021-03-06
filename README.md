# **포드 풀커슨 알고리즘**  
## **네트워크 플로우**
### **용어 정리**
- **용량(capacity)** : 간선의 전송 가능 데이터량  
ㄴ c(u,v) : u->v의 남은 용량
- **유량(flow)** : 간선의 실제 전송 데이터량  
ㄴ f(u,v) : u->v의 사용중인 유량
- **소스(source)** : 유량이 시작되는 정점
- **싱크(sink)** : 유량이 도착되는 정점
### **특징**
- **각 간선의 유량은 항상 용량보다 작거나 같다.**   
**"f(u,v) ≤ c(u,v)"** 
![images](https://user-images.githubusercontent.com/101345032/165453430-8aa35c5f-58ce-4f4d-b598-fcfed4fffac2.jpg)
(예로 S와 T의 용량이 4면, 4를 초과한 유량은 들어올 수 없다.)

- **유량은 대칭적이다.**  
**"f(u,v) = -f(v,u)"**
![images](https://user-images.githubusercontent.com/101345032/165473330-59292c61-2458-4a5f-828a-71b642e70767.jpg)
예로 s에서 t로의 간선이 있다면, t에서 s로의 가상의 간선을 설정하고, f(s,t) = 1 이면, -f(t,s) = 1이다.(이때 c(t,s)의 용량은 0으로 설정하여 논리값에 영향을 주지 않는다.)
- **유량은 항상 보존된다**   
 **"Σf(u,v) = 0"**  
데이터 값의 변화가 생기는 것이 아닌 이상, 정점으로 들어오는 유량과 정점에서 나가는 유량은 동일하다.
- **소스의 유량과 용량은 무한이라고 가정**  
전기가 계속 유입되는 것과 폭포에서 물이 계속 떨어지는 것과 비슷하게 데이터량이 계속해서 들어온다고 했을때를 가정한다.  
![images](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FeyB7il%2FbtqygSV9IiF%2FJtQaMVbYKkmNOzziwE2zSK%2Fimg.jpg)
## **최대 유량문제(MAX FLOW)**
**Ex)**
![images](https://user-images.githubusercontent.com/101345032/165476380-627edac6-8a1a-4cef-954a-a08e424931fa.jpg)
### S에서 A의 용량이 7이고 A에서 T의 용량이 5일때, S에서 T까지의 최대 유량은 몇인가?  
  
  
![images](https://user-images.githubusercontent.com/101345032/165477459-5139dfe6-5854-4ffc-b7ca-4bd73e8b968b.jpg)
S에서 A의 용량이 7이므로 S에서 A의 최대유량은 7이지만, A에서 T의 용량이 5이므로 최대유량은 5로 제한된다.   

따라서 S에서 T의 최대유량은 5이다.  
  
이러한 최대 유량문제를 해결하는 기초적인 알고리즘이 바로  **포드-폴커슨 알고리즘(Ford-Fulkerson algorithm)**이다.

## **포드-폴커슨 알고리즘 동작방식**
- 유량 네트워크의 모든 간선의 유량을 0으로 두고 시작해,  
- 소스에서 싱크로 유량을 더 보낼 수 있는 경로 (증가 경로)를 찾는다   
- 이때 여유 용량이 남아 있어야 한다. "c(a, b) - f(a, b) > 0"  
- 유량을 보내기를 반복한다.  

### 예시) 
![images](https://user-images.githubusercontent.com/101345032/165547738-a98dfb0e-b1cb-4e56-a507-f500e3a9f8ef.jpg)  
다음과 같은 그래프가 있다고 가정할때,  
#### **1) dfs를 통해 그래프를 탐색한다**  
경우의 수   
i) S-A-T와 S-B-T를 먼저 탐색했을 때  
![images](https://user-images.githubusercontent.com/101345032/165547744-3751d782-6d58-42b9-a167-a8b5d331f024.jpg)  
이 경우 S-A-T를 통한 증가 경로의 유량 1과 S-B-T를 통한 증가 경로의 유량 2로 총 유량값은 3을 얻게 된다.  
(S-A-T와 같이 dfs로 Source에서 Sink까지 탐색한 하나의 경로를 증가 경로라 한다.)
  
ii) S-A-B-T를 먼저 탐색했을 때
![images](https://user-images.githubusercontent.com/101345032/165547751-6d5b9d14-7267-48d3-a811-c684a4db0896.jpg)  
이 경우 S-A-B-T를 통한 증가경로의 유량 1밖에 얻을 수 없다.
#### **2)유량의 대칭성을 활용하여 가상의 간선을 통해서도 유량을 전달한다.**
ii의 경우일때 S-A-B-T와 S-B-A-T를 탐색할 수 있다.  

ii-1) S-A-B-T를 통한 증가경로의 유량값 1
![images](https://user-images.githubusercontent.com/101345032/165547759-6c5496ab-2536-41ca-b8b6-227f8b024580.jpg)  
 
ii-2) S-B-A-T를 통한 증가경로의 유량값 2,
![images](https://user-images.githubusercontent.com/101345032/165547768-6847981d-90d7-4b05-ad61-b2ff3a6b23c9.jpg)  
  
 i)와 마찬가지로 총 유량값 3을 얻을 수 있다.  

추가로 f(a,b) + f(b,a) = 1 + -1 = 0이 되어 논리값에 영향을 주지않는다.
![images](https://user-images.githubusercontent.com/101345032/165549846-379d9ac6-30fd-4dcd-9501-c75cf7d62428.jpg)


## **예시의 작동결과**
![images](https://user-images.githubusercontent.com/101345032/165552594-52590a9d-e106-4887-ac13-85a0d8e3a089.PNG)  
~~(정수형 배열의 편의를 위해 각 노드를 알파벳이 아닌 숫자로 지정.)~~

## **포드-풀커슨 알고리즘의 성능분석**  
노드의 수를 V, 간선의 수를 E, 최대 유량값을 F로 한다면,  
하나의 증가 경로에서 최대로 사용되는 노드와 간선은 각각 V,E개이다.   
또한 각 증가 경로의 최소 유량값은 1이다. 따라서 증가경로의 최대 개수는 모든 증가 경로를 더한 F가 된다.  
그러므로 하나의 증가 경로 탐색의 소요 시간인 |V|+|E| 와   
증가 경로의 최대 개수인 F를 곱한 (|V|+|E|) * F 가 시간 복잡도가 된다.  
포드-풀커슨 알고리즘의 시간 복잡도는 **O((|V|+|E|)F)** 이다.  
## **포드-폴커슨 알고리즘의 한계** 
### **예시**
만약 아래의 그래프가 있다고 가정한다면
![images](https://user-images.githubusercontent.com/101345032/165557941-f45675f7-4912-49fd-b30a-3344d16d4760.jpg)  

i) S-A-T 와 S-B-T를 먼저 탐색한 경우
![images](https://user-images.githubusercontent.com/101345032/165557950-8f03a058-91ca-4e49-9ae7-2eeae8a65224.jpg)  
로 2번만에 최대유량값을 구할 수 있지만,  

ii) S-A-B-T 와 S-B-A-T를 먼저 탐색한 경우
![images](https://user-images.githubusercontent.com/101345032/165557945-bc7fec99-e9d8-42b7-92fa-bd9b6e312c04.jpg)
로 총 200000번을 탐색하여 최대유량값을 구해야 한다. 

### **이러한 최악의 경우가 나타나는 이유**  
Source부터 Sink까지 한번에 탐색해야하는 dfs 알고리즘을 사용했기 때문에 위와 같은 최악의 경우가 생긴다.  
이를 보완하기 위해 bfs 알고리즘을 활용한 에드몬드 카프 알고리즘이 있다. 

## **포드 풀커슨 알고리즘과 에드몬드 카프 알고리즘의 차이**  
**포드 풀커슨 알고리즘**의 경우 **dfs 알고리즘**을 사용한 반면,  
**에드몬드 카프 알고리즘**은 **bfs 알고리즘**을 사용했다.  

**일반적인 상황**에서는 **에드몬드 카프 알고리즘**이 더 효율적으로 동작하지만,   

**간선(E)가 많고, 최대 유량(Flow)이 적으면** 오히려 **포드 풀커슨 알고리즘**이 더 효율적일 수 있다.

## **느낀점** 
 중간고사 대체 과제로 처음 접한 네트워크 플로우와 이에 대한 포드풀커슨 알고리즘과 에드몬드 카프 알고리즘에 대해 조사하며 데이터가 얼마나 많이, 효율적으로 전송되기 위해서 어떠한 방식을 사용하는 지 알 수 있는 시간이 되었습니다. 이번 조사를 바탕으로 추후에 배울 데이터통신이나 컴퓨터네트워크, 전자회로를 이해하는 데에 큰 도움이 될 것같습니다.   
 
 조사 과정중 여러 코드를 비교해보며 포드 풀커슨 알고리즘임에도 불구하고 bfs 알고리즘으로 구성된 코드들이 많아 dfs 알고리즘으로 구성된 코드를 찾는 데에 어려움이 있었습니다.  

## **참고 자료 및 사이트**
- https://wooono.tistory.com/401
- https://blog.naver.com/ndb796/221237111220
- https://os94.tistory.com/99
- https://xzio.tistory.com/1173
