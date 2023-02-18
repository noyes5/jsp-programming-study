package model1.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect {
    public BoardDAO(ServletContext application) {
        super(application);
    }
    // 검색 조건에 맞는 게시물의 개수를 반환합니다.
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0; // 결과(게시물 수)를 담을 변수
        
        // 게시물 수를 얻어오는 쿼리문 작성
        String query = "SELECT COUNT(*) FROM board";
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%'";
        }
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            totalCount = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("게시물 수를 구하는 중 예외 발생");
            e.printStackTrace();
        }
        
        return totalCount;
    }
    
    // 검색 조건에 맞는 게시물 목록을 반환합니다.
    public List<BoardDTO> selectList(Map<String, Object> map) {
        List<BoardDTO> bbs = new Vector<BoardDTO>();
                                                // 결과(게시물 목록)를 담을 변수
        String query = "SELECT * FROM board";
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }
        query += " ORDER BY num DESC ";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                BoardDTO dto = new BoardDTO();
                
                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));
                
                bbs.add(dto);
            }
        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return bbs;
    }
    
    public int insertWrite(BoardDTO dto) {
        int result = 0;
        
        try {
            // INSERT 쿼리문 작성
            String query = "INSERT INTO board ( "
                    + " num,title,content,id,visitcount) "
                    + " VALUES ( "
                    + " seq_board_num.NEXTVAL, ?, ?, ?, 0)";
            
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getId());
            
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }
        
        return result;
    }
    
    public BoardDTO selectView(String num) {
        BoardDTO dto = new BoardDTO();
        
        // 쿼리문 준비
        String query = "SELECT B.*, M.name "
                        + " FROM member M INNER JOIN board B "
                        + " ON M.id=B.id "
                        + " WHERE num=?";
        
        try { 
            psmt = con.prepareStatement(query);
            psmt.setString(1, num);   // 인파라미터를 일렬번호로 설정
            rs = psmt.executeQuery(); // 쿼리생성 
            
        //결과 처리
            if (rs.next()) {
                dto.setNum(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString(6));
                dto.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }
        
        return dto;
    }
    
    public void updateVisitCount(String num) {
        String query = "UPDATE board SET "
                        + " visitcount=visitcount+1 "
                        + " WHERE num=?";
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, num); // 인파라미터를 일렬번호로 설정
            psmt.executeQuery(); // 쿼리 실행
        } catch (Exception e) {
            System.out.println("게시물 조회수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }
    
    // 지정한 게시물을 수정합니다.
    public int updateEdit(BoardDTO dto) {
        int result = 0;
        
        try {
            // 쿼리문 템플릿
            String query = "UPDATE board SET "
                            + " title=?, content=? "
                            + " WHERE num=?";
            
            //쿼리문 작성
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getNum());
            
            // 쿼리문 실행
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("게시물 수정 중 예외 발생");
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int deletePost(BoardDTO dto) {
        int result = 0;
        
        try {
            // 쿼리문 템플릿
            String query = "DELETE FROM board WHERE num=?";
            
            // 쿼리문 완성
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getNum());
            
            // 쿼리문 실행
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }
        
        return result; // 결과 반환
    }
}