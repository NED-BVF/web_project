package com.jsc.fanCM.service;

import com.jsc.fanCM.dao.BoardRepository;
import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.dto.board.BoardSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // DB 저장/수정/삭제시 필요
    @Transactional
    public void save(BoardSaveForm boardSaveForm){
        Board board = Board.createBoard(
                boardSaveForm.getName(),
                boardSaveForm.getDetail()
        );
        boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
