package com.vinca.backboard.common;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

// 컴포넌트 이름 써야됨
@Component("CommonUtil")
public class CommonUtil {
    public String markdown(String content){
        Parser parser = Parser.builder().build();   // 기존 마크다운으로 작성된 글 파싱
        Node document = parser.parse(content);      
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);   // HTML로 렌더링 텍스트 리턴
    }
}
