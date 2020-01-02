# react
## react project 생성
1. Create React App은 최신 javascript 기능을 사용할 수 있게 개발 환경을 세팅한다.
```
npm install -g create-react-app	
```
2. Create React App을 이용하여 프로젝트를 생성한다.
```
create-react-app $PROJECT_NAME
```
3. project build
```
cd $PROJECT_NAME
npm start
```
## react project configuration
1. debugging
debugger for chrome 설치  
launch.json 수정 후 실행
```
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "type": "chrome",
            "request": "launch",
            "name": "Launch Chrome against localhost",
            "url": "http://localhost:3000",
            "webRoot": "${workspaceFolder}/src"
        }
    ]
}
```
2. 권장 설치

>bundler
>>- webpack : 코드들을 의존하는 순서대로 합쳐서 하나 또는 N개의 결과물로 만들어 낸다.  
>>- Browserify
>Compiler
>>- Babel : 최신 사양의 자바스크립트 코드를 IE나 구형 브라우저에서도 동작하는 ES5 이하의 코드로 변환할 수 있다.
>>- ES6 : 개발을 쉽게 만드는 모던 자바스크립트 기능의 집합
>>- JSX : React Javascript 언어 확장

## Method
- constructor(props)  
  - component가 생성될 때 호출  
- componentDidMount()  
  - 외부 라이브러리 연동  
  - 컴포넌트에서 필요한 데이터 요청  
  - DOM관련 작업  
- componentDidCatch(error, info)  
  - error가 난 부모 component에 구현  
- static getDerivedStateFromProps(nextProps, prevState)  
  - component가 변경될 떄 호출  
- componentDidUpdate(prevProps, prevState)  
  - component가 실제로 변경된 후 호출  
- componentWillUnmount()  
  - component가 제거된 후 호출  
- shouldComponentUpdate(nextProps, nextState)  
  - 성능을 위해 업데이트를 막아주는 메소드  