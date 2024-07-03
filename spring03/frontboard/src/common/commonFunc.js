
export function formatDate(date) {
    var result = date.replace('T', ' ');    // T를 공백으로 변경
    var index = result.lastIndexOf(' ')    // 마지막 공백은 'YYYY-mm-dd'만 남김

    result = result.substr(0, index);
    
    return result;
}

