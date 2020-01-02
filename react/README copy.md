# react-redux
## react와 react-redux 다른점
- react : React 컴포넌트 자신이 개별적으로 상태관리  
- react-redux : 상태관리하는 전용 장소(store)에서 상태를 관리하고, React 컴포넌트는 그걸 보여주기만 한다.

## store
- 상태는 기본적으로 여기서 관리된다.  
- 커다란 JSON 결정체  
```
{
    // 세션과 관련된 것
    session: {
        loggedIn: true,
        user: {
            id: "114514",
            screenName: "@mpyw",
        },
    },
​
    // 표시중인 타임라인에 관련된 것
    timeline: {
        type: "home",
        statuses: [
            {id: 1, screenName: "@mpyw", text: "hello"},
            {id: 2, screenName: "@mpyw", text: "bye"},
        ],
    },
​
    // 알림과 관련된 것
    notification: [],
}
```

## Reducer
- 이전 상태와 Action을 합쳐, 새로운 state를 만드는 조작  
- store의 문지기  
```
import { ADD_VALUE } from './actions';
​
export default (state = {value: 0}, action) => {
    switch (action.type) {
        case ADD_VALUE:
            return { ...state, value: state.value + action.payload };
        default:
            return state;
    }
}
```
- 초기상태는 Reducer의 디폴트 인수에서 정의  
- 상태가 변할 때 전해진 state는 그 자체의 값으로 대체 된느것이 아니라, 새로운 것이 합성되는 것처럼 쓰여진다.  
대규모 개발에 Reducer를 미세하게 분할하는 경우 Redux에서 제공하는 ```combineReducers``` 함수를 이용하여 아래와 같이 쓴다.
```
import { combineReducers } from 'redux';
​
const sessionReducer = (state = {loggedIn: false, user: null}, payload) => {
    
};
const timelineReducer = (state = {type: "home", statuses: []}, payload) => {
    
};
const notificationReducer = (state = [], payload) => {
    
};
​
export default combineReducers({
    session: sessionReducer,
    timeline: timelineReducer,
    notification: notificationReducer,
})
```

## Action 및 Action Creator
Store 및 Store에 존재하는 State에 접근하려면 Action을 사용해야한다. 이벤트 드리븐과 비슷한 개념  
1. Store에 뭔가 하고 하려고 할때 Action을 발행  
2. Store 문지기가 Action의 발생을 감지하면 State가 갱신된다.  
Action Format Object
```
{
    type: "액션의 종류를 한번에 식별할 수 있는 문자열 혹은 심볼",
    payload: "액션의 실행에 필요한 임의의 데이터",
}
```

## 순수한 Component와 연결된 Component
React의 Component 자체는 Redux의 흐름에 타는 것이 불가능하다. 흐름에 타기 위해선 ReactRedux에서 제공하는 ```connect``` 함수를 이용해야한다.  
Component가 Store로부터 정보를 받는 경우 ```props```를 통해 받는다. ```props```는 immutable하다. 결론적으로 ***상태가 변경될 때마다 새로운 Component가 생성된다.***  
1. Store가 가진 ```state```를 어떻게 ```props```에 엮을 지 정한다. -> 이 동작을 ```mapStateToProps```  
2. Reducer에 action을 알리는 함수 ```dispatch```를 어떻게 ```props```에 엮을지 정한다. -> 이 동작을 ```mapDispatchToProps```  
3. 위에 두 가지가 적용된 ```props```를 받을 Component를 정한다.  
4. Store와 Reducer를 연결시킬 수 있도록 만들어진 Component가 반환값이 된다.
```
connect(mapStateToProps, mapDispatchToProps)(Component)
```
### mapStateToProps
인수로 전달된 state는 ```전체```를 의미한다.  
기본적으로 ```필요한 것```만 설별하여 props로 엮는다.
```
state => ({ value: state.value})
```

### mapDispatchToProps
Action Creator에서 action을 만든다고 해도 아무 일도 일어나지 않는다. Reducer를 향해 통지를 할 수 있게 만들기 위해서는 만든 action을 ```dispatch```라는 함수에 넘겨줘야만 한다.  
Reducer에 switch문으로 분기를 나눠 관계없는 action을 무시하고, 자기에게 주어진 action만을 처리하도록 한다.  
Component 쪽에 하나하나 수동으로 dispatch하는 처리를 하지 않아도 되도록, 여기서 action의 생성부터 dispatch의 실행까지 한번에 이뤄질 수 있도록 함수를 정의하여 props에 넘겨준다.

### bindActionCreators
bindActionCreators를 이용해 간편화할 수 있다.  
사용전)
```
>>>>>>>> 함수판
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { addValue } from './actions';
​
const Counter = ({ value, dispatchAddValue }) => (
    <div>
        Value: {value}
        <a href="#" onClick={e => dispatchAddValue(1)}>+1</a>
        <a href="#" onClick={e => dispatchAddValue(2)}>+2</a>
    </div>
);
​
export default connect(
    state => ({ value: state.value }),
    dispatch => ({ dispatchAddValue: amount => dispatch(addValue(amount)) })
)(Counter)
>>>>>>>>> 클래스판
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { addValue } from './actions';
​
class Counter extends Component {
    render() {
        const { value, dispatchAddValue } = this.props;
        return (
            <div>
                Value: {value}
                <a href="#" onClick={e => dispatchAddValue(1)}>+1</a>
                <a href="#" onClick={e => dispatchAddValue(2)}>+2</a>
            </div>
        );
    }
}
​
export default connect(
    state => ({ value: state.value }),
    dispatch => ({ dispatchAddValue: amount => dispatch(addValue(amount)) })
)(Counter)
```
사용후)
```
import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { addValue } from './actions';
​
​
const Counter = ({ value, addValue }) => (
    <div>
        Value: {value}
        <a href="#" onClick={e => addValue(1)}>+1</a>
        <a href="#" onClick={e => addValue(2)}>+2</a>
    </div>
);
​
export default connect(
    state => ({ value: state.value }),
    dispatch => bindActionCreators({ addValue }, dispatch)
)(Counter)
```
or
```
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { addValue } from './actions';
​
​
const Counter = ({ value, addValue }) => (
    <div>
        Value: {value}
        <a href="#" onClick={e => addValue(1)}>+1</a>
        <a href="#" onClick={e => addValue(2)}>+2</a>
    </div>
);
​
export default connect(
    state => ({ value: state.value }),
    { addValue }
)(Counter)
```

