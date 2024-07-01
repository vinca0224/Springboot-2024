import logo from './logo.svg';
import './App.css';
import CustomButton from './component/CustomButton'
import { useState } from 'react';
import IncButton from './component/IncButton';

// 데이터 생성 시 보통 const 사용
const ironman = {
  name: 'Tony Stark',
  heroName: 'Ironman',
  imgUrl: 'https://img.danawa.com/prod_img/500000/207/533/img/18533207_1.jpg?_v=20221226163359',
  imgSize: 100,
}

const weapons = [
  {title:'Repulsor Beam', idx:1},
  {title:'Unibeam Blaster Beam', idx:2},
  {title:'Smart missile', idx:3},
];

const listWeapons = weapons.map(weapon =>
  <li key={weapon.idx}>
    {weapon.title}
  </li>
);

function App() {
  const [count, setCount] = useState(0);

  function handleClick() {
    setCount(count + 1);
  }
  return (
    <div className="App">
      <header className="App-header">
        <h1>{ironman.heroName}</h1>
        <img className='profile'
             src={ironman.imgUrl}
             alt={ironman.name + '피규어'}
             style={{
                width: ironman.imgSize,
                // borderRadius: '50%'
             }}
        ></img>
        <IncButton count={count} onClick={handleClick}></IncButton>
        <IncButton count={count} onClick={handleClick}></IncButton>
        {/* <h1>Hello, React.js</h1> */}
        {/* <ul>{listWeapons}</ul> */}
        {/* <CustomButton data={ironman}></CustomButton> */}
      </header>
    </div>
  );
}

export default App;
