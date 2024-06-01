# Catch V 

## Backend

### Introduction

### Stacks and Dependencies
Framework: Spring,SpringBoot
 
Languages: Java

### Issues

### Contributors

<!-- Copy-paste in your Readme.md file -->

<a href = "https://github.com/Tanu-N-Prabhu/Python/graphs/contributors">
  <img src = "https://contrib.rocks/image?repo=gangfunction/catchvbackend"/>
</a>

Made with [contributors-img](https://contrib.rocks).

---
## CatchV Backend 개선 
### startDate : 2024년 1월 2일
#### 개선과정
- 현재 존재하는 코드에 관한 주석과 기술을 선택했던 이유들을 작성한다.(일자 :20240104)
>   하지만 테스트코드를 다시 원래 코드에 적용하는 과정에서 어려움이 생겼고, 결국 리팩토링의 기반이 DB모델링을 제대로 하지않아서 생겼던 문제였음을 깨달았습니다. 이에 데이터베이스를 H2로 변경후 다시 모델링 하기로 결정했습니다. (20240107)
>> Restful API를 설계하는 것과 어떤서비스를 하기위해서 데이터 모델링을 하는것중 우선인가에 대해서 고민하고있었습니다.
> - (결정) 캐치비 백엔드 리팩토링 프로젝트를 지속함과 동시에,  우선순위를 REST API 먼저로 결정했습니다.
>   - (이유1): 레이어드 아키텍처를 적용했었던 것은 전달될 파라미터들과 그와 관련한 문맥들을 명확히 하기위함이었습니다.
>   - (이유2): RestApi를 채택한것은 백엔드로서 일관성있는 서비스를 제공하기 위함입니다.
>   - (이유3): 서비스상 들어오는 정보와 데이터베이스의 조작이 일관되었다면, 훌륭하지만 그렇지 않을 가능성을 상정하기위함입니다.
- 주석을 통해, 명확히 선택에 관한 이유가 없던 기술들을 제거한다.(일자: )
- 실 운영상황을 상정하여 모니터링 및 서버환경을 구비한다.(일자: )
