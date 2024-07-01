function CustomButton(){

    let isLoggedIn = true;  // 로그인 여부
    let content;

    // if (isLoggedIn) {
    //     content = <button>Log Out</button>
    // }else {
    //     content = <button>Log In</button>
    // }


    return (
        <>
            {/* {content} */}
            {
                isLoggedIn ? (
                    <button>Log Out</button>
                ) : (
                    <button>Log In</button>
                )
            }
        </>
    );
}

export default CustomButton;    // 외부에서 사용하려면 필수