

function Home() {
    var username, email, role, loginDt;

    if (localStorage != null) {
        username = localStorage.getItem("username");
        email = localStorage.getItem("email");
        role = localStorage.getItem("role");
        loginDt = localStorage.getItem("loginDt");
    }

    return (
        <div className="container main card" style={{maxWidth: '360px'}}>
            <h4>로그인 정보</h4>
            <div>
                <label className="form-label">
                    {username}
                </label><br />
                <label className="form-label">
                    {email}
                </label><br />
                <label className="form-label">
                    {role}
                </label><br />
                <label className="form-label">
                    {loginDt}
                </label><br />
            </div>
        </div>
    );
}

export default Home;