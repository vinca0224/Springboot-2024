function CustomButton(props){

    let heroName = props.data.heroName;
    let isLoggedIn = true;  // 로그인 여부
    let content;

    console.log(heroName);

    function handleClick(name) {
        if (isLoggedIn) {
            alert(name + " have logged out")
        } else {
            alert(name + " have logged in")
        }
    }

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
                    <button onClick={() => handleClick(heroName)}>Log Out</button>
                ) : (
                    <button onClick={() => handleClick(heroName)}>Log In</button>
                )
            }
        </>
    );
}

export default CustomButton;    // 외부에서 사용하려면 필수