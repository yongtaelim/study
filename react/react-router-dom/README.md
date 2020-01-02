# react-router-dom
## reference
```https://react-router.vlpt.us/1/02.html```

## router
주소에 따라 다른 View를 보여주는 것

## 정의
react에는 router기능이 내장되어 있지 않다. 그리하여 react-router 라이브러리를 사용한다.  
react-router는 써드파티 라이브러리로서, 클라이언트 사이드에서 이뤄지는 라우팅을 간단하게 해주며, 서버 사이드 렌더링도 도와주는 도구들도 사용가능하다.

## 사용법
- Root.js
```
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import App from 'shared/App';

const Root = () => (
    <BrowserRouter>
        <App/>
    </BrowserRouter>
);

export default Root;
```
- index.js
```
import React from 'react';
import ReactDOM from 'react-dom';
import Root from './client/Root';
import registerServiceWorker from './registerServiceWorker';
import './index.css';

ReactDOM.render(<Root />, document.getElementById('root'));
registerServiceWorker();
```
- App.js
```
import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import { Home, About } from 'pages';


class App extends Component {
    render() {
        return (
            <div>
                <Route exact path="/" component={Home}/>
                <Route path="/about" component={About}/>
            </div>
        );
    }
}

export default App;
```
path 값에 따른 컴포넌트 사용
/ :: Home 컴포넌트
/about :: About 컴포넌트 
## 파라미터
라우트로 설정한 컴포넌트는 3가지의 props를 전달받는다.  
- ```history``` 이이 객체를 통해 ```push```, ```replace```를 통해 다른 경로로 이동하거나 앞 뒤 페이지로 전환 가능  
- ```location``` 이 개겣는 현재 경로에 대한 정보를 지니고 있고 URL 쿼리 정보도 가지고 있다. ( /about?foo=bar )
- ```match``` 이 객체는 어떤 라우트에 매칭이 되었는지 정보가 있고 params 정보도 가지고 있다 ( /about/:name )  
라우트의 경로에 특정 값을 넣는 방법은 ```params```, ```query```로 두 가지가 있다.  
1. params  
```:foo``` 형식으로 설정.
```
import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import { Home, About } from 'pages';

class App extends Component {
    render() {
        return (
            <div>
                <Route exact path="/" component={Home}/>
                <Route path="/about" component={About}/>
                <Route path="/about/:name" component={About}/>
            </div>
        );
    }
}

export default App;
```
```match.params.name``` 파라미터를 받아보자.
```
import React from 'react';

const About = ({match}) => {
    return (
        <div>
            <h2>About {match.params.name}</h2>
        </div>
    );
};

export default About;
```
```about/foo```로 URL를 호출하면 ```foo``` 값을 받을 수 있다.  
2. URL query
리액트 라우터 v3에서는 URL 쿼리를 해석하여 객체로 만들어주는 기능이 있다.  
콘솔로 파라미터를 찍어보자.  
```/about/foo?detail=true```
```
import React from 'react';
import queryString from 'query-string';

const About = ({location, match}) => {
    const query = queryString.parse(location.search);
    console.log(query);

    return (
        <div>
            <h2>About {match.params.name}</h2>
        </div>
    );
};

export default About;
```
## 라우트 이동
1. Link 컴포넌트
>이동할 때 ```<a href>...``` 사용하면 새로고침하기 때문에 사용하지 않음. 새로고침을 방지하기 위해선 react-router에 있는 Link 컴포넌트를 사용한다.  
>라우트 이동 예제로 메뉴 컴포넌트를 사용해보자.
```
import React from 'react';
import { Link } from 'react-router-dom';

const Menu = () => {
    return (
        <div>
            <ul>
                <li><Link to="/">Home</Link></li>
                <li><Link to="/about">About</Link></li>
                <li><Link to="/about/foo">About Foo</Link></li>
            </ul>
            <hr/>
        </div>
    );
};

export default Menu;
```
2. NavLink 컴포넌트
>설정한 URL이 활성화가 되면, 특정 스타일 혹은 클래스를 지정 할 수 있다.
```
import React from 'react';
import { NavLink } from 'react-router-dom';

const Menu = () => {
    const activeStyle = {
        color: 'green',
        fontSize: '2rem'
    };

    return (
        <div>
            <ul>
                <li><NavLink exact to="/" activeStyle={activeStyle}>Home</NavLink></li>
                <li><NavLink exact to="/about" activeStyle={activeStyle}>About</NavLink></li>
                <li><NavLink to="/about/foo" activeStyle={activeStyle}>About Foo</NavLink></li>
            </ul>
            <hr/>
        </div>
    );
};

export default Menu;
```
## 라우트 안의 라우트
props.children을 사용하지 않고 라우트에서 보여주는 컴포넌트 내부에 또 라우트를 사용한다.  
Posts.js 라우트 안에 Post.js 라우트 생성
```Post.js```
```
import React from 'react';

const Post = ({match}) => {
    return (
        <div>
            포스트 {match.params.id}
        </div>
    );
};

export default Post;
```
```Posts.js```
```
import React from 'react';
import { Link, Route } from 'react-router-dom';
import { Post } from 'pages'; 

const Posts = ({match}) => {
    return (
        <div>
           <h2>Post List</h2> 
           <ul>
                <li><Link to={`${match.url}/1`}>Post #1</Link></li>
                <li><Link to={`${match.url}/2`}>Post #2</Link></li>
                <li><Link to={`${match.url}/3`}>Post #3</Link></li>
                <li><Link to={`${match.url}/4`}>Post #4</Link></li>
           </ul>
           <Route exact path={match.url} render={()=>(<h3>Please select any post</h3>)}/>
           <Route path={`${match.url}/:id`} component={Post}/>
        </div>
    );
};

export default Posts;
```
## withRouter
라우트가 아닌 컴포넌트에서 라우터에서 사용 객체 - location, match, history 를 사용하려면 ```withRouter``` 라는 HoC를 사용해야한다.
```
import React from 'react';
import { withRouter } from 'react-router-dom';

const ShowPageInfo = withRouter(({ match, location }) => {
  return <div>현재 위치: {location.pathname}</div>;
});

export default ShowPageInfo;
```
```
import React from 'react';
import { Route } from 'react-router-dom';
import { Home, About, Posts } from 'pages';
import Menu from 'components/Menu';
import ShowPageInfo from 'components/ShowPageInfo';


const App = () => {
  return (
    <div>
      <Menu />
      <Route exact path="/" component={Home} />
      <Route path="/about/:name?" component={About} />
      <Route path="/posts" component={Posts}/>
      <ShowPageInfo />
    </div>
  );
};

export default App;
```