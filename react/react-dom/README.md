# react-dom
## 개요
>react-dom package는 앱의 최상위 레벨에서 사용할 수 있는 DOM에 특화된 메서드와 필요한 경우 React 모델 외부로 나갈 수 있는 해결책을 제공한다.
>>render()
>>hydrate()
>>unmountComponentAtNode()
>>findDOMNode()
>>createPortal()
## 참조
### render()
```
ReactDOM.render(element, container[, callback])
```
React element를 container DOM에 렌더링하고 컴포넌트에 대한 참조를 반환. ( 무상태 컴포넌트는 null 반환 )  
React element가 이전에 container 내부에 렌더링 되었다면 해당 element는 업데이트하고 최신의 React element를 반영하는데 필요한 DOM만 변경
### hydrate()
```
ReactDOM.hydrate(element, container[, callback])
```
render()와 동일하지만 HTML 콘텐츠가 ReactDOMServer로 렌더링 된 컨테이너에 이벤트를 보충하기 위해 사용.
React는 기존 마크업에 이벤트 리스너를 연결
### unmountComponentAtNode()
```
ReactDOM.unmountComponentAtNode(container)
```
마운트된 React 컴포넌트를 DOM에서 제거하고 컴포넌트의 이벤트 핸들러와 state를 정리
### findDOMNode()
```
ReactDOM.findDOMNode(component)
```
권장하지 않음..
### createPortal()
```
ReactDOM.createPortal(child, container)
```
portal을 생성. Portal을 DOM 컴포넌트 구조의 외부에 존재하는 DOM 노드에 자식을 렌더링하는 방법을 제공