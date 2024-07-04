import React, {useState} from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';

function Login() {
    
    // 변수
    const navigate = useNavigate();
    const [user, setUser] = useState({
        username: '',
        password: ''
    });

    // 함수
    const handleChange = (e) => {
        const {name, value} = e.target; // username, password 중 하나
        setUser({...user, [name]: value});
    }

    const handleSubmit = async (e) => {
        e.preventDefault(); // submit 동안 다른 이벤트가 발생하지 않도록 중지시키는 것
        
        try {
            const formData = new FormData();
            formData.append('username', user.username);
            formData.append('password', user.password);

            console.log(formData.get('username') + "/" + formData.get('password'));

            // axios 백엔드 호출
            const resp = await axios({
                url: 'http://localhost:8080/api/member/login',  // REST API 호출
                method: 'POST', // GET, POST, DELETE, PUT
                data: formData,
                withCredentials: true
            });

            console.log(resp);
            if(resp.data.resultCode == "OK"){
                const {email, mid, role, username} = resp.data.data;
                const transactionTime = resp.data.transactionTime;
                // localStorage에 저장
                localStorage.setItem("username", username);
                localStorage.setItem("email", email);
                localStorage.setItem("mid", mid);
                localStorage.setItem("role", role);
                localStorage.setItem("loginDt", transactionTime)
                console.log(localStorage);
                
                alert('로그인 성공');
                // navigate('/home', {data:{userData: resp.data.data}});   // 다른 페이지로 데이터 전달
                navigate('/home');
            } else {
                alert('로그인 실패');
            }
            
        } catch (error) {
            console.log('로그인 에러: ' + error);
            alert('로그인 실패');
        }
    }

    return (
        <div className="container card shadow-sm" style={{maxWidth: '400px', padding: '1rem'}}>
            <div>
                <div className="my-3 border-bottom">
                    <h4 className="text-start">로그인</h4>
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="text-start mb-3">
                        <label htmlFor="username" className="form-label">사용자 이름</label>
                        <input type="text" name="username" placeholder="사용자 이름"
                               className="form-control" required
                               value={user.username} onChange={handleChange}></input>
                    </div>
                    <div className="text-start mb-3">
                        <label htmlFor="password" className="form-label">비밀번호</label>
                        <input type="password" name="password" placeholder="비밀번호"
                               className="form-control" required
                               value={user.password} onChange={handleChange}></input>
                    </div>
                    <button type="submit" className="btn btn-primary me-2">로그인</button>
                    <Link to={'./home'} className="btn btn-secondary">취소</Link>
                </form>
            </div>
        </div>
    );
}

export default Login;