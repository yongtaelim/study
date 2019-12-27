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
```
package manage
  - npm, yarn
bundler
  - webpack, Browserify
compiler
  - babel
```
```
npm init
npm install --save react react-dom
```